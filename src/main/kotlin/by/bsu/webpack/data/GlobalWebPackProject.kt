package by.bsu.webpack.data

import by.bsu.webpack.explorer.ui.GlobalExplorer
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class GlobalWebPackProject(
  override val uuid: String,
  parentDisposable: Disposable,
  globalExplorer: GlobalExplorer,
  private val webPackProjectConfigProvider: (String) -> WebPackProjectConfig?
): WebPackProject, Disposable {

  private val lock = ReentrantLock()

  private val isDisposed = AtomicBoolean(false)

  init {
    Disposer.register(parentDisposable, this)
  }

  private val webPackProjectConfig: WebPackProjectConfig?
    get() = lock.withLock {
      isDisposed.compareAndSet(false, false).runIfTrue { webPackProjectConfigProvider(uuid) }
    }

  override val controllers: Collection<ControllerConfig>
    get() = lock.withLock { webPackProjectConfig?.controllers ?: listOf() }

  override fun addController(controller: ControllerConfig) {
    val newWppConfig = webPackProjectConfig?.clone() ?: return
    if (newWppConfig.controllers.add(controller)) {
      // TODO: Update data
    }
  }

  override fun removeController(controller: ControllerConfig) {
    val newWppConfig = webPackProjectConfig?.clone() ?: return
    if (newWppConfig.controllers.remove(controller)) {
      // TODO: Update data
    }
  }

  override val name: String
    get() = webPackProjectConfig?.name ?: ""
  override val explorer = globalExplorer

  override fun dispose() {
    isDisposed.set(true)
  }
}

inline fun <T> Boolean?.runIfTrue(block: () -> T): T? {
  return if (this == true) {
    block()
  } else null
}