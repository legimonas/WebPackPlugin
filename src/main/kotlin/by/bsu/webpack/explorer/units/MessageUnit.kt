package by.bsu.webpack.explorer.units

import by.bsu.webpack.explorer.ui.Explorer
import javax.swing.Icon

class MessageUnit(value: String, val subValue: String?, val icon: Icon?, override val explorer: Explorer, override val uuid: String) : ExplorerUnit {
  override val name = value
}