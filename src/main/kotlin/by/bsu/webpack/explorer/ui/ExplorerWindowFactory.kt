package by.bsu.webpack.explorer.ui

import com.intellij.openapi.components.service
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory

class ExplorerWindowFactory : ToolWindowFactory, DumbAware {
  override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
    val contentManager = toolWindow.contentManager
    val explorerContent = service<GlobalControllersExporerContent>()
    val content = contentManager.factory.createContent(
      explorerContent.buildComponent(toolWindow.disposable, project),
      explorerContent.displayName,
      explorerContent.isLockable
    )

    toolWindow.contentManager.addContent(content)
  }
}