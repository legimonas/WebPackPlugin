package by.bsu.webpack.explorer.ui

import by.bsu.webpack.explorer.ui.nodes.ExplorerTreeNode
import com.intellij.ide.util.treeView.AbstractTreeStructureBase
import com.intellij.openapi.project.Project

abstract class ExplorerTreeStructureBase(project: Project) : AbstractTreeStructureBase(project) {

  abstract fun registerNode(node: ExplorerTreeNode<*>)

  abstract fun <V : Any> findByValue(value: V): Collection<ExplorerTreeNode<V>>

  abstract fun findByPredicate(predicate: (ExplorerTreeNode<*>) -> Boolean): Collection<ExplorerTreeNode<*>>
}