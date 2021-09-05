package by.bsu.webpack.explorer.units.entities

import com.kvk.config.javassist.EntityClass


class ControllerConfig(
  val name: String,
  val controllerType: ControllerType,
  val url: String,
  val httpMethod: HttpMethod,
  webPackProjectConfig: WebPackProjectConfig,
  val entityClass: EntityClass? = null
): ProjectBelongsConfig(webPackProjectConfig)

enum class ControllerType(private val v: String) {
  SAVE_CONTROLLER("SaveController");

  override fun toString(): String {
    return v
  }
}

enum class HttpMethod(private val v: String) {
  GET("GET"),
  HEAD("HEAD"),
  POST("POST"),
  PUT("PUT"),
  DELETE("DELETE"),
  CONNECT("CONNECT"),
  OPTIONS("OPTIONS"),
  TRACE("TRACE"),
  PATCH("PATCH");

  override fun toString(): String {
    return v
  }
}