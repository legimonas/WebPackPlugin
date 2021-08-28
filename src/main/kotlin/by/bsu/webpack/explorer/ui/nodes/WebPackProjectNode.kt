package by.bsu.webpack.explorer.ui.nodes

import by.bsu.webpack.data.FolderConfig
import by.bsu.webpack.data.MessageUnit
import by.bsu.webpack.data.WebPackProject
import by.bsu.webpack.explorer.ui.ExplorerTreeStructureBase
import by.bsu.webpack.explorer.ui.ExplorerUnitTreeNodeBase
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
    val controllersNode = FolderTreeNode(
      FolderConfig<MessageUnit>("controllers", explorer, emptyList()),
      { mu, f -> MessageTreeNode(mu, notNullProject, f, unit, treeStructure) },
      notNullProject,
      this,
      unit,
      treeStructure
    )
    return mutableListOf(controllersNode)
  }
}