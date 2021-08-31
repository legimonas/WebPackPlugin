package by.bsu.webpack.explorer.units.entities

import by.bsu.webpack.crudable.dataProvider
import by.bsu.webpack.explorer.ui.Explorer

abstract class ProjectBelongsConfig(val webPackProjectConfig: WebPackProjectConfig) : EntityWithUuid()

abstract class FolderConfig<T : ProjectBelongsConfig>(
  val name: String,
  webPackProjectConfig: WebPackProjectConfig
) : ProjectBelongsConfig(webPackProjectConfig) {

  abstract val itemClass: Class<out T>

  val items: MutableCollection<T> by lazy {
    dataProvider.find(itemClass) { it.webPackProjectConfig.uuid == webPackProjectConfig.uuid }.toMutableList()
  }
}

class ControllersConfig(projectConfig: WebPackProjectConfig) :
  FolderConfig<ControllerConfig>("controllers", projectConfig) {
  override val itemClass = ControllerConfig::class.java
}
