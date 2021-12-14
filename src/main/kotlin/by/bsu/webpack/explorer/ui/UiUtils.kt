package by.bsu.webpack.explorer.ui

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.roots.ui.componentsList.components.ScrollablePanel
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.AnActionButton
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.layout.Cell
import com.intellij.ui.layout.CellBuilder
import com.intellij.ui.table.TableView
import com.intellij.util.ui.ListTableModel
import java.awt.event.ItemEvent
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.event.TableModelEvent


fun <Item> Cell.tableView(tableModel: ListTableModel<Item>): CellBuilder<JPanel> {
  val tableView = TableView(tableModel)

  return panel("Table", ToolbarDecorator.createDecorator(tableView)
    .disableAddAction()
    .disableRemoveAction()
    .disableUpDownActions().createPanel())
}

fun Cell.customCombobox(items: Array<String>, getter: () -> String, setter: (String) -> Unit): CellBuilder<ComboBox<String>> {
  val combobox = ComboBox(items)
  combobox.selectedItem = getter()
  combobox.addItemListener {
    if (it.stateChange == ItemEvent.SELECTED) {
      setter(combobox.selectedItem as String)
    }
  }
  return component(combobox)
}
