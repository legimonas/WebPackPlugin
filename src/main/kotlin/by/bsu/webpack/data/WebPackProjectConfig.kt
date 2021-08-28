package by.bsu.webpack.data

import by.bsu.webpack.crudable.dataProvider
import by.bsu.webpack.crudable.findAll

class WebPackProjectConfig(
  uuid: String,
  val name: String
  ) : EntityWithUuid(uuid) {

  val controllers: MutableCollection<ControllerConfig> by lazy { dataProvider.findAll<ControllerConfig>().toMutableList() }

  public override fun clone(): WebPackProjectConfig {
    return WebPackProjectConfig(uuid, name)
  }
}