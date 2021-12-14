package by.bsu.webpack.utils

import com.google.gson.Gson
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ComponentManager
import com.intellij.util.messages.Topic
import java.util.*
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.instanceParameter

@Suppress("UNCHECKED_CAST")
fun <T> Any?.castOrNull(clazz: Class<T>): T? =
  if (this != null && clazz.isAssignableFrom(this::class.java)) this as T else null

inline fun <reified T> Any?.castOrNull(): T? = (this is T).runIfTrue { this as T }

inline fun <T> Boolean?.runIfTrue(block: () -> T): T? {
  return if (this == true) {
    block()
  } else null
}

val <E> E?.optional: Optional<E>
  inline get() = Optional.ofNullable(this)

val gson by lazy { Gson() }

inline fun <reified T : Any> T.clone() = clone(T::class.java)

fun <T : Any> T.clone(clazz: Class<out T>): T {
  return with(gson) {
    fromJson(toJson(this@clone), clazz)
  }
}

fun <T: Any> T.list(): List<T> {
  return listOf(this)
}

fun <L> sendTopic(
  topic: Topic<L>,
  componentManager: ComponentManager = ApplicationManager.getApplication()
): L {
  return componentManager.messageBus.syncPublisher(topic)
}

fun <L : Any> subscribe(
  componentManager: ComponentManager = ApplicationManager.getApplication(),
  topic: Topic<L>,
  handler: L,
  disposable: Disposable
) = componentManager
  .messageBus
  .connect(disposable)
  .subscribe(topic, handler)