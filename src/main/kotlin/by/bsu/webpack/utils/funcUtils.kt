package by.bsu.webpack.utils

import java.util.*

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