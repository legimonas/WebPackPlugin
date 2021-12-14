package by.bsu.webpack.explorer.ui.dialogs

import by.bsu.webpack.explorer.units.entities.ControllerType
import by.bsu.webpack.explorer.units.entities.EntityConfig
import by.bsu.webpack.explorer.units.entities.HttpMethod
import by.bsu.webpack.explorer.units.entities.WebPackProjectConfig
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import com.intellij.util.ui.ListTableModel
import com.kvk.config.javassist.*
import javax.persistence.Id
import javax.swing.JComponent

class NewEntityDialog(
  project: Project?,
  val webPackProjectConfig: WebPackProjectConfig,
  override val state: EntityDialogState = EntityDialogState(),
  dialogState: DialogState = DialogState.ADD
): DialogWrapper(project,true), StatefulComponent<EntityDialogState> {
  init {
    title = "${dialogState.v} Controller"
    init()
  }

  override fun createCenterPanel(): JComponent? {
    return panel {
      row {
        label("Class Name")
        textField(state::className)
      }
      row {
        label("Table Name")
        textField(state::tableName)
      }
    }
  }
}


class EntityDialogState {
  var className: String = ""
  var tableName: String = ""

  fun toEntityConfig(webPackProjectConfig: WebPackProjectConfig): EntityConfig {
    val idMemberInfo = FieldInfo("java.lang.Integer", "id", mutableListOf(AnnotationInfo.of(Id::class.java)))
    return EntityConfig(
      EntityClass(className, EntityAnnotationInfo(tableName), TableAnnotationInfo()).apply { membersInfo = mutableListOf<MemberInfo>(idMemberInfo) },
      webPackProjectConfig
    )
  }
}