package by.bsu.webpack.explorer.actions

import by.bsu.webpack.crudable.dataProvider
import by.bsu.webpack.crudable.findByUniqueKey
import by.bsu.webpack.explorer.ui.ExplorerUnitTreeNodeBase
import by.bsu.webpack.explorer.ui.WPPROJECTS_EXPLORER_VIEW
import by.bsu.webpack.explorer.ui.dialogs.NewControllerDialog
import by.bsu.webpack.explorer.ui.dialogs.NewMemberDialog
import by.bsu.webpack.explorer.ui.nodes.NodeType
import by.bsu.webpack.explorer.units.WebPackProject
import by.bsu.webpack.explorer.units.entities.EntityConfig
import by.bsu.webpack.explorer.units.entities.WebPackProjectConfig
import by.bsu.webpack.utils.clone
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class NewMemberAction: AnAction() {
  override fun actionPerformed(e: AnActionEvent) {
    val view = e.getData(WPPROJECTS_EXPLORER_VIEW) ?: return

    val selectedNode = view.mySelectedNodes.firstOrNull() ?: return
    val selectedUnitNode = selectedNode as ExplorerUnitTreeNodeBase<*, *>
    val unit = selectedUnitNode.unit
    if (unit !is WebPackProject) {
      return
    }
    val webPackProjectConfigOpt = dataProvider.findByUniqueKey<WebPackProjectConfig>(unit.uuid)
    val entityConfig = if (selectedNode.value is EntityConfig) selectedNode.value as EntityConfig else return
    if (!webPackProjectConfigOpt.isPresent){
      return
    }

    val dialog = NewMemberDialog(e.project, webPackProjectConfigOpt.get())
    if (dialog.showAndGet()) {
      entityConfig.also {
        it.entityClass.membersInfo.add(dialog.state.toMemberInfo())
        dataProvider.update(it)
      }
    }
  }

  override fun update(e: AnActionEvent) {
    val explorerView = e.getData(WPPROJECTS_EXPLORER_VIEW) ?: let {
      e.presentation.isEnabledAndVisible = false
      return
    }
    if (explorerView.mySelectedNodes.size > 1 || explorerView.mySelectedNodes.isEmpty()){
      e.presentation.isEnabledAndVisible = false
      return
    }
    val selectedNode = explorerView.mySelectedNodes[0]
    if (selectedNode.nodeType != NodeType.ENTITY) {
      e.presentation.isEnabledAndVisible = false
      return
    }
    e.presentation.isEnabledAndVisible = true
  }

}