package by.bsu.webpack.explorer.ui

import by.bsu.webpack.explorer.ui.nodes.ExplorerTreeNode
import by.bsu.webpack.explorer.ui.nodes.ExplorerTreeNodeRoot
import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.openapi.project.Project
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class ExplorerTreeStructure(
  explorer: Explorer,
  project: Project
) : ExplorerTreeStructureBase(project) {

  private val valueToNodeMap = Collections.synchronizedMap(
    WeakHashMap<Any, ConcurrentLinkedQueue<ExplorerTreeNode<*>>>()
  )

  override fun registerNode(node: ExplorerTreeNode<*>) {
    valueToNodeMap.getOrPut(node.value) { ConcurrentLinkedQueue() }.add(node)
  }

  override fun <V : Any> findByValue(value: V): Collection<ExplorerTreeNode<V>> {
    return valueToNodeMap[value] as Collection<ExplorerTreeNode<V>>? ?: emptySet()
  }

  override fun findByPredicate(predicate: (ExplorerTreeNode<*>) -> Boolean): Collection<ExplorerTreeNode<*>> {
    return valueToNodeMap.values.flatten().filter(predicate)
  }

  private val root by lazy { ExplorerTreeNodeRoot(project, explorer,this) }

  override fun getRootElement(): Any = root

  override fun commit() {}

  override fun hasSomethingToCommit() = false

  override fun getProviders(): MutableList<TreeStructureProvider> {
    return mutableListOf()
  }
}