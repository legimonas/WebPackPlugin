package by.bsu.webpack.explorer.ui

import by.bsu.webpack.crudable.DATA_CHANGED_TOPIC
import by.bsu.webpack.crudable.DataChangedListener
import by.bsu.webpack.explorer.ui.nodes.ExplorerTreeNode
import by.bsu.webpack.explorer.ui.nodes.WebPackProjectNode
import by.bsu.webpack.explorer.units.entities.EntityWithUuid
import by.bsu.webpack.utils.rwLocked
import by.bsu.webpack.utils.subscribe
import com.intellij.ide.dnd.aware.DnDAwareTree
import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.ui.PopupHandler
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.tree.AsyncTreeModel
import com.intellij.ui.tree.StructureTreeModel
import com.intellij.ui.treeStructure.Tree
import java.awt.Component
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.TreeModel
import javax.swing.tree.TreeSelectionModel

val WPPROJECTS_EXPLORER_VIEW = DataKey.create<GlobalExplorerView>("wpProjectsExplorerView")

class GlobalExplorerView(
  internal val explorer: Explorer,
  project: Project,
  parentDisposable: Disposable
): JBScrollPane(), DataProvider, Disposable {

  internal val tree: Tree
  internal val treeModel: TreeModel
  internal val myStructureModel: StructureTreeModel<ExplorerTreeStructure>
  internal val myStructure: ExplorerTreeStructure
  var mySelectedNodes: List<ExplorerTreeNode<*>> by rwLocked(listOf())

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
    createListeners()
  }

  override fun getData(dataId: String): Any? {
    return if (WPPROJECTS_EXPLORER_VIEW.`is`(dataId)) this else null
  }

  override fun dispose() {}

  fun createListeners () {
    tree.addMouseListener(object : PopupHandler() {
      override fun invokePopup(comp: Component?, x: Int, y: Int) {
        val popupActionGroup = DefaultActionGroup()
        popupActionGroup.add(
          ActionManager.getInstance().getAction("GlobalExplorerTreePopupMenuGroup")
        )
        ActionManager.getInstance().createActionPopupMenu("Global Explorer", popupActionGroup).component.show(comp, x, y)
      }
    })

    tree.selectionModel.selectionMode = TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION
    tree.addTreeSelectionListener{
      mySelectedNodes = tree.selectionPaths?.map {
        val userObj = (it.lastPathComponent as DefaultMutableTreeNode).userObject
        if (userObj is ExplorerTreeNode<*>) userObj else null
      }?.filterNotNull() ?: listOf()
    }

    subscribe(
      topic = DATA_CHANGED_TOPIC,
      disposable = this,
      handler = object : DataChangedListener {

        fun changeFsTreeStructure (e: Any) {
//          myStructure.findByValue(e).forEach {
//            myStructureModel.invalidate(it, true)
//          }
          explorer.updateUnits()
          tree.invalidate()
          tree.repaint()
          myStructureModel.invalidate()
        }

        override fun onAdd(e: EntityWithUuid) {
          changeFsTreeStructure(explorer)
        }

        override fun onUpdate(e: EntityWithUuid) {
          changeFsTreeStructure(e)
        }

        override fun onDelete(e: EntityWithUuid) {
          TODO("Not yet implemented")
        }

      }
    )

  }

}