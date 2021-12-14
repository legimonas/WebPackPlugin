package by.bsu.webpack.explorer.ui.dialogs

interface StatefulComponent<T>{
  val state: T
}

enum class DialogState (val v: String) {
  ADD("Add"), UPDATE("Update");

  override fun toString (): String {
    return v
  }
}