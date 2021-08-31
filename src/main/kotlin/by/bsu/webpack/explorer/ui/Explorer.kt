package by.bsu.webpack.explorer.ui

import by.bsu.webpack.explorer.units.ExplorerUnit
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project

val globalExplorer
  get() = Explorer.instance

interface Explorer {

  companion object {
    @JvmStatic
    val instance: Explorer
      get() = ApplicationManager.getApplication().getService(Explorer::class.java)
  }

  val units: Collection<ExplorerUnit>

  fun disposeUnit(unit: ExplorerUnit)

  fun isUnitPresented(unit: ExplorerUnit): Boolean

  fun reportThrowable(t: Throwable, project: Project?)

  fun showNotification(
    title: String,
    content: String,
    type: NotificationType = NotificationType.INFORMATION,
    project: Project?
  )

  fun reportThrowable(t: Throwable, unit: ExplorerUnit, project: Project?)

}