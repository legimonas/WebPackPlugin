package by.bsu.webpack.explorer.ui

import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.DataProvider
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBScrollPane


class GlobalControllersExplorerVeiw(
  project: Project,
  parentDisposable: Disposable
): JBScrollPane(), DataProvider, Disposable {
  override fun getData(dataId: String): Any? {
    TODO("Not yet implemented")
  }

  override fun dispose() {
    TODO("Not yet implemented")
  }

}