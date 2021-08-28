package by.bsu.webpack.explorer.ui.nodes

import by.bsu.webpack.data.WebPackProject
import by.bsu.webpack.explorer.ui.Explorer
import by.bsu.webpack.explorer.ui.ExplorerTreeStructureBase
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project

class ExplorerTreeNodeRoot(
  project: Project,
  explorer: Explorer,
  treeStructure: ExplorerTreeStructureBase
): ExplorerTreeNode<Explorer>(explorer, project, null, explorer, treeStructure) {

  override fun isAlwaysExpand() = true

  override fun update(presentation: PresentationData) {}

  override fun getChildren(): MutableCollection<out AbstractTreeNode<*>> {
    return explorer.units.filterIsInstance<WebPackProject>()
      .map{ WebPackProjectNode(it, notNullProject, this, treeStructure) }.toMutableList()
  }
}