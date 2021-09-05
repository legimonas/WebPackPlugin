package by.bsu.webpack.explorer.ui.nodes

import by.bsu.webpack.explorer.units.MessageUnit
import by.bsu.webpack.explorer.units.WebPackProject
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
  unit: WebPackProject,
  treeStructure: ExplorerTreeStructureBase
) : ExplorerUnitTreeNodeBase<MessageUnit, WebPackProject>(value, project, parent, unit, treeStructure) {
  override fun update(presentation: PresentationData) {
    presentation.setIcon(value.icon)
    if (value.subValue == null || value.subValue == "") {
      presentation.addText(value.name, SimpleTextAttributes.REGULAR_ATTRIBUTES)
    } else {
      presentation.addText(value.name + " ", SimpleTextAttributes.REGULAR_ATTRIBUTES)
      presentation.addText(value.subValue, SimpleTextAttributes.GRAYED_ATTRIBUTES)
    }
  }

  override fun getChildren(): MutableCollection<out AbstractTreeNode<*>> {
    return mutableListOf()
  }
}