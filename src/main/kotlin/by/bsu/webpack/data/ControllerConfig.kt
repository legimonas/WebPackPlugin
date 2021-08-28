package by.bsu.webpack.data

import by.bsu.webpack.explorer.ExplorerUnit
import by.bsu.webpack.explorer.ui.Explorer

class ControllerConfig(
  val name: String,
  val webPackProjectConfig: WebPackProjectConfig,
): EntityWithUuid()