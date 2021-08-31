package by.bsu.webpack.explorer.units.entities


class ControllerConfig(
  val name: String,
  val controllerType: ControllerType,
  val url: String,
  val httpMethod: HttpMethod,
  webPackProjectConfig: WebPackProjectConfig,
): ProjectBelongsConfig(webPackProjectConfig)

enum class ControllerType(private val v: String) {
  SAVE_CONTROLLER("SaveController");

  override fun toString(): String {
    return v
  }
}

enum class HttpMethod(private val v: String) {
  GET("get"),
  HEAD("head"),
  POST("post"),
  PUT("put"),
  DELETE("delete"),
  CONNECT("connect"),
  OPTIONS("options"),
  TRACE("trace"),
  PATCH("patch");

  override fun toString(): String {
    return v
  }
}