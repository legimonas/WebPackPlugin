package by.bsu.webpack.explorer.ui.nodes

import by.bsu.webpack.explorer.units.entities.FolderConfig
import by.bsu.webpack.explorer.units.MessageUnit
import by.bsu.webpack.explorer.units.WebPackProject
import by.bsu.webpack.explorer.ui.ExplorerTreeStructureBase
import by.bsu.webpack.explorer.ui.ExplorerUnitTreeNodeBase
import by.bsu.webpack.explorer.units.entities.ControllersConfig
import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.ui.SimpleTextAttributes

private val wppIcon = AllIcons.Nodes.Project

class WebPackProjectNode(
  webPackProject: WebPackProject,
  project: Project,
  parent: ExplorerTreeNode<*>,
  treeStructure: ExplorerTreeStructureBase
): ExplorerUnitTreeNodeBase<WebPackProject, WebPackProject>(
  webPackProject, project, parent, webPackProject, treeStructure
) {
  override fun isAlwaysExpand(): Boolean = true

  override fun update(presentation: PresentationData) {
    presentation.addText(value.name, SimpleTextAttributes.REGULAR_ATTRIBUTES)
    presentation.setIcon(wppIcon)
  }


  override fun getChildren(): MutableCollection<out AbstractTreeNode<*>> {
    val projectControllers = unit.controllers
    val controllersNode = if (projectControllers != null ) FolderTreeNode(
      projectControllers,
      { controller, folder -> ControllerTreeNode(controller, notNullProject, folder, unit, treeStructure) },
      notNullProject,
      this,
      unit,
      treeStructure
    ) else null

    return mutableListOf(controllersNode).filterNotNull().toMutableList()
  }
}