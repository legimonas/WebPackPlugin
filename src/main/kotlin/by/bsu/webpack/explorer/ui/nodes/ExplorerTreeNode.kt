package by.bsu.webpack.explorer.ui.nodes

import by.bsu.webpack.explorer.ui.Explorer
import by.bsu.webpack.explorer.ui.ExplorerTreeStructureBase
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.project.Project
import javax.swing.tree.TreePath

abstract class ExplorerTreeNode<Value: Any>(
  value: Value,
  project: Project,
  val parent: ExplorerTreeNode<*>?,
  val explorer: Explorer,
  protected val treeStructure: ExplorerTreeStructureBase
) : AbstractTreeNode<Value>(project, value) {

  abstract val nodeType: NodeType

  init {
    @Suppress("LeakingThis")
    treeStructure.registerNode(this)
  }

  val notNullProject = project


  private val pathList: List<ExplorerTreeNode<*>>
    get() = if (parent != null) {
      parent.pathList + this
    } else {
      listOf(this)
    }

  val path: TreePath
    get() = TreePath(pathList.toTypedArray())
}

enum class NodeType(value: String) {
  ROOT("ROOT"),
  PROJECT("PROJECT"),
  CONTROLLERS_FOLDER("CONTROLLER_FOLDER"),
  ENTITIES_FOLDER("ENTITY_FOLDER"),
  CONTROLLER("CONTROLLER"),
  ENTITY("ENTITY"),
  MEMBER("MEMBER"),
  CLASSIFIER("CLASSIFIER"),
  MESSAGE("MESSAGE")
}

data class NodeData(
  val node: ExplorerTreeNode<*>,
  val nodeType: NodeType
)