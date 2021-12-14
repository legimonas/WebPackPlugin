package by.bsu.webpack.explorer.ui.dialogs

import by.bsu.webpack.explorer.units.entities.ControllerConfig
import by.bsu.webpack.explorer.units.entities.ControllerType
import by.bsu.webpack.explorer.units.entities.HttpMethod
import by.bsu.webpack.explorer.units.entities.WebPackProjectConfig
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.jbTextField
import com.intellij.ui.layout.panel
import com.kvk.config.javassist.EntityClass
import javax.swing.ComboBoxModel
import javax.swing.DefaultComboBoxModel
import javax.swing.DefaultListModel
import javax.swing.JComponent



class NewControllerDialog(
  project: Project?,
  val webPackProjectConfig: WebPackProjectConfig,
  override val state: ControllerDialogState = ControllerDialogState(),
  dialogState: DialogState = DialogState.ADD
): DialogWrapper(project,true), StatefulComponent<ControllerDialogState> {

  init {
    title = "${dialogState.v} Controller"
    init()
  }


  override fun createCenterPanel(): JComponent {
    return panel{
      row {
        label("Name")
        textField(state::name)
      }
      row {
        label("Controller Type")
        comboBox(
          DefaultComboBoxModel(ControllerType.values().map { it.toString() }.toTypedArray()),
          { state.controllerType.toString() },
          { state.controllerType = ControllerType.valueOf(it ?: "SAVE_CONTROLLER")}
        )
      }
      row {
        label("Controller Url")
        textField(state::url)
      }
      row {
        label("Controller Type")
        comboBox(
          DefaultComboBoxModel(HttpMethod.values().map { it.toString() }.toTypedArray()),
          { state.httpMethod.toString() },
          { state.httpMethod = HttpMethod.valueOf(it ?: "GET")}
        )
      }
      row {
        label("Entity")
        comboBox(
          DefaultComboBoxModel(webPackProjectConfig.entities.items.map { it.entityClass.className }.toTypedArray()),
          { state.entityClass?.className },
          { res -> state.entityClass = webPackProjectConfig.entities.items.firstOrNull { res == it.entityClass.className }?.entityClass }
        )
      }
    }
  }
}

class ControllerDialogState () {
  var name: String = ""
  var controllerType: ControllerType = ControllerType.SAVE_CONTROLLER
  var url: String = ""
  var httpMethod: HttpMethod = HttpMethod.GET
  var entityClass: EntityClass? = null

  fun toControllerConfig (webPackProjectConfig: WebPackProjectConfig): ControllerConfig {
    return ControllerConfig(name, controllerType, url, httpMethod, webPackProjectConfig, entityClass)
  }
}