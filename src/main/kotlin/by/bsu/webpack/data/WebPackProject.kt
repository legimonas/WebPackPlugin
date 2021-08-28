package by.bsu.webpack.data

import by.bsu.webpack.explorer.ExplorerUnit

interface WebPackProject : ExplorerUnit {

  val controllers: Collection<ControllerConfig>

  fun addController(controller: ControllerConfig)

  fun removeController(controller: ControllerConfig)

}