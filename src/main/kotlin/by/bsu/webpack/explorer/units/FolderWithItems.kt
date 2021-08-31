package by.bsu.webpack.explorer.units

import by.bsu.webpack.explorer.units.entities.EntityWithUuid

interface FolderWithItems<T: EntityWithUuid>: ExplorerUnit {
  val items: Collection<T>

  fun addItem(item: T)

  fun removeItem(item: T)
}