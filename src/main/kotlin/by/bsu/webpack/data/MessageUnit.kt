package by.bsu.webpack.data

import by.bsu.webpack.explorer.ExplorerUnit
import by.bsu.webpack.explorer.ui.Explorer

class MessageUnit(override val uuid: String, value: String, override val explorer: Explorer) : ExplorerUnit {
  override val name = value
}