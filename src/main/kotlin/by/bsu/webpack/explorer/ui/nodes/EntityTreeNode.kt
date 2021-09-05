package by.bsu.webpack.explorer.ui.nodes

import by.bsu.webpack.explorer.ui.ExplorerTreeStructureBase
import by.bsu.webpack.explorer.ui.ExplorerUnitTreeNodeBase
import by.bsu.webpack.explorer.units.MessageUnit
import by.bsu.webpack.explorer.units.WebPackProject
import by.bsu.webpack.explorer.units.entities.EntityConfig
import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.SimpleTextAttributes
import icons.WebPackIcons
import java.util.*
import javax.swing.Icon

private val entityIcon = AllIcons.Nodes.DataTables
private val columnIcon = AllIcons.Nodes.DataColumn
private val idIcon = WebPackIcons.IdIcon

class EntityTreeNode(
  value: EntityConfig,
  project: Project,
  parent: ExplorerTreeNode<*>,
  webPackProject: WebPackProject,
  treeStructure: ExplorerTreeStructureBase
) : ExplorerUnitTreeNodeBase<EntityConfig, WebPackProject>(value, project, parent, webPackProject, treeStructure) {

  private fun makeMessageTreeNode(value: String, subValue: String, icon: Icon? = null): MessageTreeNode {
    return MessageTreeNode(
      MessageUnit(value, subValue,icon, explorer, UUID.randomUUID().toString()),
      notNullProject,
      this,
      unit,
      treeStructure
    )
  }

  override fun update(presentation: PresentationData) {
    presentation.setIcon(entityIcon)
    presentation.addText(value.entityClass.className.substringAfterLast(".") + " ", SimpleTextAttributes.REGULAR_ATTRIBUTES)
    presentation.addText(value.entityClass.tableAnnotationInfo.name, SimpleTextAttributes.GRAYED_ATTRIBUTES)
  }

  override fun getChildren(): MutableCollection<out AbstractTreeNode<*>> {
    return value.entityClass.membersInfo.map{ memb ->
      val value = memb.name
      val subValueBase = memb.type.simpleName
      var resultSubValue = subValueBase
      var icon: Icon? = null
      memb.annotationsInfo.findLast { it.annotationName == "Column" }?.let {
        resultSubValue = "$subValueBase (${it.parameters["name"] as String})"
        icon = columnIcon
      }
      memb.annotationsInfo.findLast { it.annotationName == "Id" }?.let {
        resultSubValue = "$subValueBase (id)"
        icon = idIcon
      }
      makeMessageTreeNode(value, resultSubValue, icon)
    }.toMutableList()
  }
}