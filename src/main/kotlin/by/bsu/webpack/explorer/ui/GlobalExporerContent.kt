package by.bsu.webpack.explorer.ui

import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

class GlobalExporerContent : ExplorerContent(null, "Controllers Explorer") {
  override fun <DisposableComponent : Disposable> buildContent(
    parentDisposable: DisposableComponent,
    project: Project
  ): JComponent where DisposableComponent : JComponent {
    return GlobalExplorerView(globalExplorer, project, parentDisposable)
  }

  override val displayName = "Web Explorer"
  override val isLockable = true
}