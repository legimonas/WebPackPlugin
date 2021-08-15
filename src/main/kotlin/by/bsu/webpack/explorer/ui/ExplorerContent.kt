package by.bsu.webpack.explorer.ui

import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.util.Disposer
import com.intellij.ui.components.JBScrollPane
import javax.swing.JComponent

abstract class ExplorerContent(
  actionGroup: ActionGroup?,
  place: String
) : ComponentFactory {

  private val actionToolbar = actionGroup?.let { ActionManager.getInstance().createActionToolbar(place, it, true) }

  abstract fun <DisposableComponent> buildContent(parentDisposable: DisposableComponent, project: Project): JComponent
      where DisposableComponent : Disposable,
            DisposableComponent : JComponent

  override fun buildComponent(parentDisposable: Disposable, project: Project): JComponent {
    return object : SimpleToolWindowPanel(true, true), Disposable {

      private var builtContent: JComponent? = null

      init {
        Disposer.register(parentDisposable, this)
        actionToolbar?.let {
          it.setTargetComponent(this)
          toolbar = it.component
        }
        setContent(buildContent(this, project).also { builtContent = it })
      }

      override fun dispose() {
        if (builtContent is Disposable) {
          (builtContent as Disposable).dispose()
        }
        builtContent = null
      }
    }
  }

  abstract val displayName: String

  abstract val isLockable: Boolean
}