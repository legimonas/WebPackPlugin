package by.bsu.webpack.data

import by.bsu.webpack.explorer.ui.Explorer
import by.bsu.webpack.explorer.ui.GlobalExplorer
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class GlobalFolderWithItems<T: EntityWithUuid>(
  override val uuid: String,
  parentDisposable: Disposable,
  override val explorer: GlobalExplorer,
  private val webPackProjectConfigProvider: (String) -> WebPackProjectConfig?
): FolderWithItems<T>, Disposable {
  private val lock = ReentrantLock()

  private val isDisposed = AtomicBoolean(false)

  init {
    Disposer.register(parentDisposable, this)
  }

  private val webPackProjectConfig: WebPackProjectConfig?
    get() = lock.withLock {
      isDisposed.compareAndSet(false, false).runIfTrue { webPackProjectConfigProvider(uuid) }
    }
  override val items: Collection<T>
    get() = TODO("Not yet implemented")

  override fun addItem(item: T) {
    TODO("Not yet implemented")
  }

  override fun removeItem(item: T) {
    TODO("Not yet implemented")
  }

  override val name: String
    get() = TODO("Not yet implemented")

  override fun dispose() {
    TODO("Not yet implemented")
  }
}