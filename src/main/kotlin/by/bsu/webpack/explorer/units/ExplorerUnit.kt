package by.bsu.webpack.explorer.units

import by.bsu.webpack.explorer.ui.Explorer

interface ExplorerUnit{
  companion object

  val name: String
  val uuid: String
  val explorer: Explorer
}