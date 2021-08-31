package by.bsu.webpack.crudable

import by.bsu.webpack.explorer.units.entities.EntityWithUuid
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

  fun <E: EntityWithUuid> add(obj: E): Optional<E>

  fun <E: EntityWithUuid> update(obj: E): Optional<E>

  fun <E: EntityWithUuid> delete(uuid: String, classOfE: Class<E>): Optional<EntityWithUuid>

  fun <E: EntityWithUuid> findAll(clazz: Class<E>): List<E>

  fun <E: EntityWithUuid> find(clazz: Class<E>, predicate: Predicate<E>): List<E>

  fun <E: EntityWithUuid> findByUniqueKey(clazz: Class<E>, key: String): Optional<E>

  fun <E: EntityWithUuid> findFirstOrCreate(entity: E, predicate: Predicate<E>): Optional<E>
}

inline fun <reified T: EntityWithUuid> DataProvider.findAll(): List<T> {
  return findAll(T::class.java)
}

inline fun <reified T: EntityWithUuid> DataProvider.find(predicate: Predicate<T>): List<T> {
  return find(T::class.java, predicate)
}

inline fun <reified T: EntityWithUuid> DataProvider.delete(uuid: String): Optional<EntityWithUuid> {
  return delete(uuid, T::class.java)
}

inline fun <reified T: EntityWithUuid> DataProvider.findByUniqueKey(key: String): Optional<T> {
  return findByUniqueKey(T::class.java, key)
}