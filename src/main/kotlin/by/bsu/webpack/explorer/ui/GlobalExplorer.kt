package by.bsu.webpack.explorer.ui

import by.bsu.webpack.crudable.DataProviderImpl
import by.bsu.webpack.crudable.dataProvider
import by.bsu.webpack.crudable.find
import by.bsu.webpack.crudable.findAll
import by.bsu.webpack.data.GlobalWebPackProject
import by.bsu.webpack.data.WebPackProject
import by.bsu.webpack.data.WebPackProjectConfig
import by.bsu.webpack.explorer.ExplorerUnit
import by.bsu.webpack.utils.rwLocked
import com.intellij.notification.NotificationBuilder
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.Disposable
import com.intellij.openapi.progress.ProcessCanceledException
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import java.util.concurrent.locks.ReentrantReadWriteLock
import java.util.stream.Collectors

const val EXPLORER_NOTIFICATION_GROUP_ID = "by.bsu.webpack.explorer.ExplorerNotificationGroup"

class GlobalExplorer : Explorer {

  val lock = ReentrantReadWriteLock()

  val disposable = Disposer.newDisposable()

  private fun WebPackProjectConfig.toGlobalWPP(parentDisposable: Disposable): GlobalWebPackProject {
    return GlobalWebPackProject(
      uuid = uuid,
      globalExplorer = this@GlobalExplorer,
      webPackProjectConfigProvider = { dataProvider.find<WebPackProjectConfig> { wp -> wp.uuid == it }[0] },
      parentDisposable = parentDisposable
    )
  }

  override val units: MutableSet<GlobalWebPackProject> by rwLocked(
    value = dataProvider.findAll<WebPackProjectConfig>().map { it.toGlobalWPP(disposable) }.stream().collect(Collectors.toSet()),
    lock = lock
  )

  override fun disposeUnit(unit: ExplorerUnit) {
    TODO("Not yet implemented")
  }

  override fun isUnitPresented(unit: ExplorerUnit): Boolean {
    return unit is GlobalWebPackProject && units.contains(unit)
  }

  override fun reportThrowable(t: Throwable, project: Project?) {
    if (t is ProcessCanceledException) {
      return
    }
    NotificationBuilder(
      EXPLORER_NOTIFICATION_GROUP_ID,
      "Error in plugin For Mainframe",
      t.message ?: t.toString(),
      NotificationType.ERROR
    ).build().let {
      Notifications.Bus.notify(it, project)
    }
  }

  override fun reportThrowable(t: Throwable, unit: ExplorerUnit, project: Project?) {
    reportThrowable(t, project)
  }

  override fun showNotification(title: String, content: String, type: NotificationType, project: Project?) {
    NotificationBuilder(
      EXPLORER_NOTIFICATION_GROUP_ID,
      title,
      content,
      type
    ).build().let {
      Notifications.Bus.notify(it, project)
    }
  }
}