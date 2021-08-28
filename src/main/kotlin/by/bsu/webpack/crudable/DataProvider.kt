package by.bsu.webpack.crudable

import by.bsu.webpack.data.EntityWithUuid
import com.intellij.openapi.application.ApplicationManager
import java.util.*
import java.util.function.Predicate

val dataProvider = DataProvider.instance

interface DataProvider {

  companion object {
    @JvmStatic
    val instance: DataProvider
      get() = ApplicationManager.getApplication().getService(DataProvider::class.java)
  }

  fun collection(clazz: Class<out EntityWithUuid>): MutableMap<String, EntityWithUuid>

  fun add(obj: EntityWithUuid): Optional<EntityWithUuid>

  fun update(obj: EntityWithUuid): Optional<EntityWithUuid>

  fun <E: EntityWithUuid> delete(uuid: String, classOfE: Class<E>): Optional<EntityWithUuid>

  fun <E: EntityWithUuid> findAll(clazz: Class<E>): List<E>

  fun <E: EntityWithUuid> find(clazz: Class<E>, predicate: Predicate<E>): List<E>
}

inline fun <reified T:EntityWithUuid> DataProvider.findAll(): List<T> {
  return findAll(T::class.java)
}

inline fun <reified T:EntityWithUuid> DataProvider.find(predicate: Predicate<T>): List<T> {
  return find(T::class.java, predicate)
}

inline fun <reified T: EntityWithUuid> DataProvider.delete(uuid: String): Optional<EntityWithUuid> {
  return delete(uuid, T::class.java)
}
