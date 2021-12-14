package by.bsu.webpack.crudable

import by.bsu.webpack.explorer.units.entities.*
import by.bsu.webpack.utils.castOrNull
import by.bsu.webpack.utils.list
import by.bsu.webpack.utils.optional
import by.bsu.webpack.utils.sendTopic
import com.intellij.openapi.application.ApplicationManager
import com.intellij.util.messages.Topic
import com.kvk.config.javassist.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Predicate
import javax.persistence.Id

val DATA_CHANGED_TOPIC: Topic<DataChangedListener> = Topic.create("DataChanged", DataChangedListener::class.java)

interface DataChangedListener {
  fun onAdd (e: EntityWithUuid)
  fun onUpdate (e: EntityWithUuid)
  fun onDelete (e: EntityWithUuid)
}


class DataProviderImpl: DataProvider {

  private val collections = ConcurrentHashMap(mutableMapOf<Class<out EntityWithUuid>, MutableMap<String, EntityWithUuid>>())

//  init {
//    WebPackProjectConfig("currentProject").also {
//      add(it)
//      val entityClass = EntityClass(
//        "org.example.User", EntityAnnotationInfo(), TableAnnotationInfo("users")
//      )
//      entityClass.membersInfo = mutableListOf(
//        FieldInfo(java.lang.Integer::class.java.name, "id", AnnotationInfo.of(Id::class.java).list().toMutableList()),
//        FieldInfo(String::class.java, "name", ColumnAnnotationInfo("name").list<AnnotationInfo>().toMutableList())
//      ) as List<MemberInfo>?
//
//      val entityConfig = EntityConfig(entityClass, it)
//      add(entityConfig)
//
//      val controller = ControllerConfig("My Super Save Controller",ControllerType.SAVE_CONTROLLER, "/save", HttpMethod.POST, it)
//      add(controller)
//      add(ControllersConfig(it))
//    }
//
//    add(WebPackProjectConfig("otherProject"))
//  }

  override fun collection(clazz: Class<out EntityWithUuid>): MutableMap<String, EntityWithUuid> {
    val res = collections[clazz] ?: mutableMapOf()
    collections[clazz] = res
    return res
  }

  override fun <E : EntityWithUuid> add(obj: E): Optional<E> {
    return obj.also {
      collection(obj.javaClass)[obj.uuid] = obj
      sendTopic(DATA_CHANGED_TOPIC).onAdd(obj)
    }.optional
  }

  override fun <E : EntityWithUuid> update(obj: E): Optional<E> {
    if (findByUniqueKey(obj.javaClass, obj.uuid).isPresent){
      return obj.also {
        collection(obj.javaClass)[obj.uuid] = obj
        sendTopic(DATA_CHANGED_TOPIC).onUpdate(obj)
      }.optional
    }
    return Optional.empty()
  }

  override fun <E : EntityWithUuid> delete(uuid: String, classOfE: Class<E>): Optional<EntityWithUuid> {
    return collection(classOfE).remove(uuid).optional.also {
      sendTopic(DATA_CHANGED_TOPIC).onDelete(it.get())
    }
  }

  override fun <E : EntityWithUuid> findAll(clazz: Class<E>): List<E> {
    return collection(clazz).map { it.value.castOrNull(clazz) }.filterNotNull()
  }

  override fun <E : EntityWithUuid> find(clazz: Class<E>, predicate: Predicate<E>): List<E> {
    return findAll(clazz).filter { predicate.test(it) }
  }

  override fun <E : EntityWithUuid> findByUniqueKey(clazz: Class<E>, key: String): Optional<E> {
    return collection(clazz)[key].castOrNull(clazz).optional
  }

  override fun <E : EntityWithUuid> findFirstOrCreate(entity: E, predicate: Predicate<E>): Optional<E> {
    val resultSet = find(entity.javaClass, predicate)
    if (resultSet.isEmpty()){
      return add(entity)
    }
    return resultSet[0].optional
  }
}
