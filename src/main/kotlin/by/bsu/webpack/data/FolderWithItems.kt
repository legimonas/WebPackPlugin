package by.bsu.webpack.data

import by.bsu.webpack.explorer.ExplorerUnit

interface FolderWithItems<T: EntityWithUuid>: ExplorerUnit {
  val items: Collection<T>

  fun addItem(item: T)

  fun removeItem(item: T)
}