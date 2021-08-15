package by.bsu.webpack.explorer.ui

import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.util.Disposer
import javax.swing.JComponent

interface ComponentFactory {
  fun buildComponent(parentDisposable: Disposable, project: Project): JComponent
}

abstract class ContentFactory : ComponentFactory {
  abstract fun <DisposableComponent> buildContent(parentDisposable: DisposableComponent, project: Project): JComponent
      where DisposableComponent : Disposable,
            DisposableComponent : JComponent

  override fun buildComponent(parentDisposable: Disposable, project: Project): JComponent {
    return object : SimpleToolWindowPanel(true, true), Disposable {

      private var builtContent: JComponent? = null

      init {
        Disposer.register(parentDisposable, this)
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
}