package by.bsu.webpack.explorer.ui.nodes

import by.bsu.webpack.explorer.ui.ExplorerTreeStructureBase
import by.bsu.webpack.explorer.ui.ExplorerUnitTreeNodeBase
import by.bsu.webpack.explorer.units.WebPackProject
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import com.intellij.ui.SimpleTextAttributes
import com.kvk.config.javassist.AnnotationInfo
import com.kvk.config.javassist.EntityClass

class ClassifierTreeNode(
  value: AnnotationInfo,
  project: Project,
  parent: ExplorerTreeNode<*>,
  webPackProject: WebPackProject,
  treeStructure: ExplorerTreeStructureBase
) : ExplorerUnitTreeNodeBase<AnnotationInfo, WebPackProject>(value, project, parent, webPackProject, treeStructure) {
  override val nodeType: NodeType
    get() = NodeType.CLASSIFIER

  override fun update(presentation: PresentationData) {
    presentation.addText(value.annotationName, SimpleTextAttributes.REGULAR_ATTRIBUTES)
  }

  override fun getChildren(): MutableCollection<out AbstractTreeNode<*>> {
    return mutableListOf()
  }
}