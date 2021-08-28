package by.bsu.webpack.explorer.ui.nodes

import by.bsu.webpack.data.FolderConfig
import by.bsu.webpack.data.WebPackProject
import by.bsu.webpack.explorer.ExplorerUnit
import by.bsu.webpack.explorer.ui.ExplorerTreeStructureBase
import by.bsu.webpack.explorer.ui.ExplorerUnitTreeNodeBase
import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.ui.SimpleTextAttributes

val folderIcon = AllIcons.Nodes.Folder

class FolderTreeNode<T: ExplorerUnit>(
  private val folderConfig: FolderConfig<T>,
  private val makeNode: (T, FolderTreeNode<T>) -> ExplorerTreeNode<T>,
  project: Project,
  parent: ExplorerTreeNode<*>,
  webPackProject: WebPackProject,
  treeStructure: ExplorerTreeStructureBase
): ExplorerUnitTreeNodeBase<FolderConfig<T>, WebPackProject>(folderConfig, project, parent, webPackProject, treeStructure) {

  override fun update(presentation: PresentationData) {
    presentation.setIcon(folderIcon)
    presentation.addText(folderConfig.name, SimpleTextAttributes.REGULAR_ATTRIBUTES)
  }

  override fun getChildren(): MutableCollection<out AbstractTreeNode<*>> {
    return folderConfig.items.map { makeNode(it, this) }.toMutableList()
  }
}