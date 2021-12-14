package by.bsu.webpack.explorer.ui.nodes

import by.bsu.webpack.explorer.ui.ExplorerTreeStructureBase
import by.bsu.webpack.explorer.ui.ExplorerUnitTreeNodeBase
import by.bsu.webpack.explorer.units.WebPackProject
import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.ui.SimpleTextAttributes
import com.kvk.config.javassist.EntityClass
import com.kvk.config.javassist.MemberInfo
import icons.WebPackIcons
import javax.swing.Icon

private val entityIcon = AllIcons.Nodes.DataTables
private val columnIcon = AllIcons.Nodes.DataColumn
private val idIcon = WebPackIcons.IdIcon

class MembersTreeNode(
  value: MemberInfo,
  project: Project,
  parent: ExplorerTreeNode<*>,
  webPackProject: WebPackProject,
  treeStructure: ExplorerTreeStructureBase
) : ExplorerUnitTreeNodeBase<MemberInfo, WebPackProject>(value, project, parent, webPackProject, treeStructure)  {
  override val nodeType = NodeType.MEMBER

  override fun update(presentation: PresentationData) {
    val subValueBase = value.type.simpleName
    var resultSubValue = subValueBase
    var icon: Icon? = null
    value.annotationsInfo.findLast { it.annotationName == "Column" }?.let {
      resultSubValue = "$subValueBase (${it.parameters["name"] as String})"
      icon = columnIcon
    }
    value.annotationsInfo.findLast { it.annotationName == "Id" }?.let {
      resultSubValue = "$subValueBase (id)"
      icon = idIcon
    }
    presentation.addText(value.name + " ", SimpleTextAttributes.REGULAR_ATTRIBUTES)
    presentation.addText(resultSubValue, SimpleTextAttributes.GRAYED_ATTRIBUTES)
    presentation.setIcon(icon)
  }

  override fun getChildren(): MutableCollection<out AbstractTreeNode<*>> {
    return value.annotationsInfo.map {
      ClassifierTreeNode(it, notNullProject, this, unit, treeStructure)
    }.toMutableList()
  }
}