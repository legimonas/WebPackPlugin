package by.bsu.webpack.explorer.ui

import by.bsu.webpack.explorer.ExplorerUnit
import by.bsu.webpack.explorer.ui.nodes.ExplorerTreeNode
import com.intellij.openapi.project.Project

abstract class ExplorerUnitTreeNodeBase<Value : Any, U : ExplorerUnit>(
  value: Value,
  project: Project,
  parent: ExplorerTreeNode<*>,
  val unit: U,
  treeStructure: ExplorerTreeStructureBase
): ExplorerTreeNode<Value>(value, project, parent, unit.explorer, treeStructure)