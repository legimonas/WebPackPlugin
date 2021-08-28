package by.bsu.webpack.data

import by.bsu.webpack.explorer.ExplorerUnit
import by.bsu.webpack.explorer.ui.Explorer

class FolderConfig<T: ExplorerUnit>(
  override val name: String,
  override val explorer: Explorer,
  val items: List<T>
) : EntityWithUuid(), ExplorerUnit