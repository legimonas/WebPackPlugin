package by.bsu.webpack.explorer.units

import by.bsu.webpack.crudable.dataProvider
import by.bsu.webpack.explorer.units.entities.ControllerConfig
import by.bsu.webpack.explorer.ui.GlobalExplorer
import by.bsu.webpack.explorer.units.entities.ControllersConfig
import by.bsu.webpack.explorer.units.entities.WebPackProjectConfig
import by.bsu.webpack.utils.clone
import by.bsu.webpack.utils.runIfTrue
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
): WebPackProject {

  private val lock = ReentrantLock()

  private val isDisposed = AtomicBoolean(false)


  private val webPackProjectConfig: WebPackProjectConfig?
    get() = lock.withLock {
      isDisposed.compareAndSet(false, false).runIfTrue { webPackProjectConfigProvider(uuid) }
    }

  override val controllers: ControllersConfig?
    get() = lock.withLock { webPackProjectConfig?.controllers }

  override val name: String
    get() = webPackProjectConfig?.name ?: ""

  override val explorer = globalExplorer

  override fun dispose() {
    isDisposed.set(true)
  }
}
