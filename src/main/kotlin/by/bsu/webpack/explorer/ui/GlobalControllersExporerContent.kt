package by.bsu.webpack.explorer.ui

import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.ui.layout.panel
import javax.swing.JComponent

class GlobalControllersExporerContent : ExplorerContent(null, "Controllers Explorer") {
  override fun <DisposableComponent : Disposable> buildContent(
    parentDisposable: DisposableComponent,
    project: Project
  ): JComponent where DisposableComponent : JComponent {
    return GlobalControllersExplorerVeiw(project, parentDisposable)
  }

  override val displayName = "Controllers"
  override val isLockable = true
}