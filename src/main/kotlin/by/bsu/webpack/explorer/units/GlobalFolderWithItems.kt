package by.bsu.webpack.explorer.units

import by.bsu.webpack.crudable.dataProvider
import by.bsu.webpack.explorer.ui.GlobalExplorer
import by.bsu.webpack.explorer.units.entities.*
import by.bsu.webpack.utils.clone
import by.bsu.webpack.utils.runIfTrue
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

open class GlobalFolderWithItems<Item : ProjectBelongsConfig, Folder: FolderConfig<Item>>(
  override val uuid: String,
  parentDisposable: Disposable,
  override val explorer: GlobalExplorer,
  private val folderConfigProvider: (String) -> FolderConfig<Item>?
): FolderWithItems<Item>, Disposable {
  private val lock = ReentrantLock()

  private val isDisposed = AtomicBoolean(false)

  init {
    Disposer.register(parentDisposable, this)
  }

  private val folderConfig: FolderConfig<Item>?
    get() = lock.withLock {
      isDisposed.compareAndSet(false, false).runIfTrue { folderConfigProvider(uuid) }
    }
  override val items: Collection<Item>
    get() = lock.withLock { folderConfig?.items ?: listOf() }

  override fun addItem(item: Item) {
    val newFolder = folderConfig?.clone() ?: return
    if (newFolder.items.add(item)) {
      dataProvider.update(newFolder)
    }
  }

  override fun removeItem(item: Item) {
    val newFolder = folderConfig?.clone() ?: return
    if (newFolder.items.remove(item)) {
      dataProvider.update(newFolder)
    }
  }

  override val name = folderConfig?.name ?: ""

  override fun dispose() {
    isDisposed.set(true)
  }
}

class GlobalControllersFolder(
  uuid: String,
  parentDisposable: Disposable,
  explorer: GlobalExplorer,
  controllersConfigProvider: (String) -> ControllersConfig
): GlobalFolderWithItems<ControllerConfig, ControllersConfig>(uuid, parentDisposable, explorer, controllersConfigProvider)