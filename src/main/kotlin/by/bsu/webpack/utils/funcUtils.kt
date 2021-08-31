package by.bsu.webpack.utils

import com.google.gson.Gson
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
