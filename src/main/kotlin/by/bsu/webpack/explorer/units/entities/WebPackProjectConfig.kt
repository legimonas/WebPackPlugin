package by.bsu.webpack.explorer.units.entities

import by.bsu.webpack.crudable.dataProvider

class WebPackProjectConfig(
  val name: String
  ) : EntityWithUuid() {

  val controllers: ControllersConfig by lazy {
    dataProvider.findFirstOrCreate(ControllersConfig(this),{ it.webPackProjectConfig.uuid == uuid }).get()
  }

  val entities: EntitiesConfig by lazy {
    dataProvider.findFirstOrCreate(EntitiesConfig(this), { it.webPackProjectConfig.uuid == uuid }).get()
  }
}