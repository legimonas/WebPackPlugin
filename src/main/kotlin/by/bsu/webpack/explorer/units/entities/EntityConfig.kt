package by.bsu.webpack.explorer.units.entities

import com.kvk.config.javassist.EntityClass

class EntityConfig(
  val entityClass: EntityClass,
  webPackProjectConfig: WebPackProjectConfig
): ProjectBelongsConfig(webPackProjectConfig) {

}