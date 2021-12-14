package by.bsu.webpack.explorer.ui.dialogs

import by.bsu.webpack.explorer.ui.customCombobox
import by.bsu.webpack.explorer.ui.tableView
import by.bsu.webpack.explorer.units.entities.ControllerType
import by.bsu.webpack.explorer.units.entities.WebPackProjectConfig
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import com.intellij.ui.table.JBTable
import com.intellij.ui.table.TableView
import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.ListTableModel
import com.kvk.config.javassist.AnnotationInfo
import com.kvk.config.javassist.ColumnAnnotationInfo
import javax.persistence.Id
import javax.swing.DefaultComboBoxModel
import javax.swing.JComponent
import javax.swing.JPanel

class NewClassifierDialog(
  project: Project?,
  val webPackProjectConfig: WebPackProjectConfig,
  override val state: ClassifierDialogState = ClassifierDialogState(),
  dialogState: DialogState = DialogState.ADD
): DialogWrapper(project,true), StatefulComponent<ClassifierDialogState> {
  init {
    title = "$dialogState Classifier"
    init()
  }

  var myPanel: DialogPanel? = null

  fun repaintPanel() {
    myPanel?.removeAll()
    myPanel?.invalidate()
    myPanel?.revalidate()
    myPanel?.repaint()
  }



  override fun createCenterPanel(): JComponent? {
    val parameterColumnInfo = ParameterValueColumnInfo()
    val props = state.props.map { Pair(it.key, it.value) }.toMutableList()
    val tableModel = ListTableModel<Pair<String, Any>>(ParameterNameColumnInfo, parameterColumnInfo)
      .apply { addRows(props) }
    parameterColumnInfo.setter = { p, v ->
      state.props[p.first] = v
      val ind = props.indexOfFirst { it.first == p.first }
      props[ind] = Pair(p.first, v)
      tableModel.removeAllRows()
      tableModel.addRows(props)
      tableModel.fireTableRowsUpdated(0, props.size)
    }
    myPanel = panel {
      row {
        label("Member Relation")
        customCombobox(
          ClassifierType.values().map { it.toString() }.toTypedArray(),
          { state.classifierType.toString() },
          {
            state.classifierType = ClassifierType.valueOf(it.toUpperCase() ?: "COLUMN")
            if (it == "Column") {
              state.props = ColumnAnnotationInfo("Column Name").parameters
            } else {
              state.props = mutableMapOf()
            }
            tableModel.removeAllRows()
            tableModel.addRows(state.props.map { e -> Pair(e.key, e.value) })
            tableModel.fireTableRowsInserted(0, state.props.size)
          }
        )
      }
      row {
        tableView(tableModel)
      }
    }
    return myPanel
  }

  object ParameterNameColumnInfo: ColumnInfo<Pair<String, Any>, String>("Parameter") {
    override fun valueOf(item: Pair<String, Any>?): String? = item?.first
  }

  class ParameterValueColumnInfo(public var setter: (Pair<String, Any>, String) -> Unit = { _, _ -> }): ColumnInfo<Pair<String, Any>, String>("Value") {
    override fun isCellEditable(item: Pair<String, Any>?): Boolean = true
    override fun valueOf(item: Pair<String, Any>?): String? = item?.second?.toString()

    override fun setValue(item: Pair<String, Any>, value: String) {
      setter(item, value)
    }
  }

}

fun ListTableModel<*>.removeAllRows() {
  val count = rowCount
  while (rowCount > 0) {
    removeRow(0)
  }
  fireTableRowsDeleted(0, count)
}

enum class ClassifierType(val value: String) {
  COLUMN("Column"),
  ID("Id");

  override fun toString(): String {
    return value
  }
}

class ClassifierDialogState {
  var classifierType = ClassifierType.COLUMN
  var props: MutableMap<String, Any> = ColumnAnnotationInfo("Column Name").parameters

  fun toAnnotationInfo (): AnnotationInfo {
    return if (classifierType == ClassifierType.COLUMN) {
      ColumnAnnotationInfo(props["name"] as String?)
    } else AnnotationInfo.of(Id::class.java)
  }
}