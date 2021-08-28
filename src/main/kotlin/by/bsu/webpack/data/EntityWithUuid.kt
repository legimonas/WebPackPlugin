package by.bsu.webpack.data

import java.util.*

open class EntityWithUuid(
  var uuid: String = UUID.randomUUID().toString()
): Cloneable {
  companion object {
    val EMPTY_ID: String = ""
  }

  override fun hashCode(): Int {
    return uuid.hashCode()
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || EntityWithUuid::class.java != other::class.java) return false

    val that = other as EntityWithUuid
    return uuid == that.uuid
  }

  override fun toString(): String {
    return "EntityWithUuid{" +
        "uuid='" + uuid + '\'' +
        '}'
  }
}