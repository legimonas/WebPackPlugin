package by.bsu.webpack.explorer.ui.dialogs

import by.bsu.webpack.explorer.units.entities.WebPackProjectConfig
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import com.kvk.config.javassist.AnnotationInfo
import com.kvk.config.javassist.FieldInfo
import com.kvk.config.javassist.MemberInfo
import javax.swing.DefaultComboBoxModel
import javax.swing.JComponent

class NewMemberDialog(
  project: Project?,
  val webPackProjectConfig: WebPackProjectConfig,
  override val state: MemberDialogState = MemberDialogState(),
  dialogState: DialogState = DialogState.ADD
): DialogWrapper(project,true), StatefulComponent<MemberDialogState> {

  init {
    title = "${dialogState.v} Member"
    init()
  }

  override fun createCenterPanel(): JComponent? {
    return panel {
      row {
        label("Member Relation")
        comboBox(
          DefaultComboBoxModel(MemberRelation.values().map{ it.toString() }.toTypedArray()),
          { state.memberRelation.toString() },
          { state.memberRelation = MemberRelation.valueOf(it ?: "Field") }
        )
      }
      row {
        label("Member Type")
        comboBox(
          DefaultComboBoxModel(MemberType.values().map{ it.toString() }.toTypedArray()),
          { state.memberType.toString() },
          {
            state.memberType = MemberType.valueOf(it?.toUpperCase() ?: "INTEGER")
          }
        )
      }
      row {
        label("Member Name")
        textField(state::memberName)
      }
    }
  }

}

enum class MemberRelation(val value: String) {
  FIELD("Field");

  override fun toString(): String {
    return value
  }
}

enum class MemberType(private val innerValue: String) {
  INTEGER("Integer"), STRING("String");

  val value = "java.lang.$innerValue";
  override fun toString(): String {
    return innerValue
  }
}

class MemberDialogState {
  var memberRelation: MemberRelation = MemberRelation.FIELD
  var memberType: MemberType = MemberType.INTEGER
  var memberName: String = "column"

  fun toMemberInfo (): MemberInfo = FieldInfo(memberType.value, memberName)
}