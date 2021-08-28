package by.bsu.webpack.explorer.ui.nodes

import by.bsu.webpack.data.MessageUnit
import by.bsu.webpack.data.WebPackProject
import by.bsu.webpack.explorer.ui.ExplorerTreeStructureBase
import by.bsu.webpack.explorer.ui.ExplorerUnitTreeNodeBase
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.ui.SimpleTextAttributes

class MessageTreeNode(
  value: MessageUnit,
  project: Project,
  parent: ExplorerTreeNode<*>,
  webPackProject: WebPackProject,
  treeStructure: ExplorerTreeStructureBase
) : ExplorerUnitTreeNodeBase<MessageUnit, WebPackProject>(value, project, parent, webPackProject, treeStructure) {
  override fun update(presentation: PresentationData) {
    presentation.addText(value.name, SimpleTextAttributes.REGULAR_ATTRIBUTES)
  }

  override fun getChildren(): MutableCollection<out AbstractTreeNode<*>> {
    return mutableListOf()
  }
}