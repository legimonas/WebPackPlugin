package by.bsu.webpack.explorer.units.entities

import by.bsu.webpack.crudable.dataProvider


abstract class ProjectBelongsConfig(val webPackProjectConfig: WebPackProjectConfig) : EntityWithUuid()

abstract class FolderConfig<T : ProjectBelongsConfig>(
  val name: String,
  webPackProjectConfig: WebPackProjectConfig
) : ProjectBelongsConfig(webPackProjectConfig) {

  abstract val itemClass: Class<out T>

//  val items: MutableCollection<T> by lazy {
//    dataProvider.find(itemClass) { it.webPackProjectConfig.uuid == webPackProjectConfig.uuid }.toMutableList()
//  }
  val items: MutableCollection<T>
    get() = dataProvider.find(itemClass) { it.webPackProjectConfig.uuid == webPackProjectConfig.uuid }.toMutableList()
}

class ControllersConfig(projectConfig: WebPackProjectConfig) :
  FolderConfig<ControllerConfig>("controllers", projectConfig) {
  override val itemClass = ControllerConfig::class.java
}

class EntitiesConfig(projectConfig: WebPackProjectConfig) :
    FolderConfig<EntityConfig>("entities", projectConfig) {
  override val itemClass = EntityConfig::class.java
}