package by.bsu.webpack.explorer.ui.nodes

import by.bsu.webpack.explorer.ui.ExplorerTreeStructureBase
import by.bsu.webpack.explorer.ui.ExplorerUnitTreeNodeBase
import by.bsu.webpack.explorer.units.FolderWithItems
import by.bsu.webpack.explorer.units.MessageUnit
import by.bsu.webpack.explorer.units.WebPackProject
import by.bsu.webpack.explorer.units.entities.ControllerConfig
import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.ui.SimpleTextAttributes
import java.util.*
import javax.swing.Icon

val controllerIcon = AllIcons.FileTypes.Diagram

class ControllerTreeNode(
  value: ControllerConfig,
  project: Project,
  parent: ExplorerTreeNode<*>,
  webPackProject: WebPackProject,
  treeStructure: ExplorerTreeStructureBase
) : ExplorerUnitTreeNodeBase<ControllerConfig, WebPackProject>(value, project, parent, webPackProject, treeStructure) {

  override fun update(presentation: PresentationData) {
    presentation.setIcon(controllerIcon)
    presentation.addText(value.name, SimpleTextAttributes.REGULAR_ATTRIBUTES)
  }

  fun makeMessageTreeNode(value: String, icon: Icon? = null): MessageTreeNode {
    return MessageTreeNode(
      MessageUnit(value, icon, explorer, UUID.randomUUID().toString()),
      notNullProject,
      this,
      unit,
      treeStructure
    )
  }

  override fun getChildren(): MutableCollection<out AbstractTreeNode<*>> {
    return mutableListOf(
      makeMessageTreeNode(value.controllerType.toString()),
      makeMessageTreeNode(value.httpMethod.toString().toUpperCase()),
      makeMessageTreeNode(value.url)
    )
  }
}