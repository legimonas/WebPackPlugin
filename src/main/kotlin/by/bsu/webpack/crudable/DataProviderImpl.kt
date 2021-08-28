package by.bsu.webpack.crudable

import by.bsu.webpack.data.ControllerConfig
import by.bsu.webpack.data.EntityWithUuid
import by.bsu.webpack.data.WebPackProjectConfig
import by.bsu.webpack.utils.castOrNull
import by.bsu.webpack.utils.optional
import java.util.*
import java.util.function.Predicate

class DataProviderImpl: DataProvider {

  val collections = mutableMapOf<Class<out EntityWithUuid>, MutableMap<String, EntityWithUuid>>()

  init {
    WebPackProjectConfig("some-uuid-v4-1", "currentProject").also {
      add(it)
      add(ControllerConfig("Controller1", it))
    }

    add(WebPackProjectConfig("some-uuid-v4-2", "otherProject"))
  }

  override fun collection(clazz: Class<out EntityWithUuid>): MutableMap<String, EntityWithUuid> {
    val res = collections[clazz] ?: mutableMapOf()
    collections[clazz] = res
    return res
  }


  override fun add(obj: EntityWithUuid): Optional<EntityWithUuid> {
    return obj.also { collection(obj.javaClass)[obj.uuid] = obj }.optional
  }

  override fun update(obj: EntityWithUuid): Optional<EntityWithUuid> {
    return obj.also { collection(obj.javaClass)[obj.uuid] = obj }.optional
  }

  override fun <E : EntityWithUuid> delete(uuid: String, classOfE: Class<E>): Optional<EntityWithUuid> {
    return collection(classOfE).remove(uuid).optional
  }

  override fun <E : EntityWithUuid> findAll(clazz: Class<E>): List<E> {
    return collection(clazz).map { it.value.castOrNull(clazz) }.filterNotNull()
  }

  override fun <E : EntityWithUuid> find(clazz: Class<E>, predicate: Predicate<E>): List<E> {
    return findAll(clazz).filter { predicate.test(it) }
  }


}
