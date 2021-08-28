package by.bsu.webpack.explorer.ui

import by.bsu.webpack.explorer.ui.nodes.WebPackProjectNode
import com.intellij.ide.dnd.aware.DnDAwareTree
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.DataKey
import com.intellij.openapi.actionSystem.DataProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.tree.AsyncTreeModel
import com.intellij.ui.tree.StructureTreeModel
import com.intellij.ui.treeStructure.Tree
import javax.swing.tree.TreeModel

val CONTROLLERS_EXPLORER_VIEW = DataKey.create<GlobalExplorerView>("controllersExplorerView")

class GlobalExplorerView(
  internal val explorer: Explorer,
  project: Project,
  parentDisposable: Disposable
): JBScrollPane(), DataProvider, Disposable {

  internal val tree: Tree
  internal val treeModel: TreeModel
  internal val myStructureModel: StructureTreeModel<ExplorerTreeStructure>
  internal val myStructure: ExplorerTreeStructure

  init {
    Disposer.register(parentDisposable, this)
    myStructureModel = StructureTreeModel(
      ExplorerTreeStructure(explorer, project).also { myStructure = it },
      { o1, o2 ->
        if (o1 is WebPackProjectNode && o2 is WebPackProjectNode) {
          o1.unit.name.compareTo(o2.unit.name)
        } else {
          0
        }
      },
      this
    ).also { stm ->
      treeModel = AsyncTreeModel(stm, false, this).also {
        tree = DnDAwareTree(it).apply { isRootVisible = false }.also { t ->
          setViewportView(t)
        }
      }
    }
  }

  override fun getData(dataId: String): Any? {
    return if (CONTROLLERS_EXPLORER_VIEW.`is`(dataId)) this else null
  }

  override fun dispose() {}

}