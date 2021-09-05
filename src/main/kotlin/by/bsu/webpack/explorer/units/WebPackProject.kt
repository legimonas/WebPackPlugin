package by.bsu.webpack.explorer.units

import by.bsu.webpack.explorer.units.entities.ControllerConfig
import by.bsu.webpack.explorer.units.entities.ControllersConfig
import by.bsu.webpack.explorer.units.entities.EntitiesConfig
import com.intellij.openapi.Disposable

interface WebPackProject : ExplorerUnit, Disposable {

  val controllers: ControllersConfig?

  val entities: EntitiesConfig?
}