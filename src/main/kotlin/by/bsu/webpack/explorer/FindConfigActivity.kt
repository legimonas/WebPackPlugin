package by.bsu.webpack.explorer

import by.bsu.webpack.crudable.dataProvider
import by.bsu.webpack.crudable.find
import by.bsu.webpack.explorer.units.entities.EntityConfig
import by.bsu.webpack.explorer.units.entities.WebPackProjectConfig
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.kvk.config.xml.XMLEntityHandler
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.io.InputStreamReader
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path

class FindConfigActivity: StartupActivity {

  private fun findSpringContextLocation(webXmlInputStream: InputStream): String? {
    val parser = XmlPullParserFactory.newInstance().newPullParser()
    parser.setInput(InputStreamReader(webXmlInputStream))
    var initParamStarted = false
    var contextParamFound = false
    while (parser.eventType != XmlPullParser.END_DOCUMENT) {
      if (parser.eventType == XmlPullParser.START_TAG) {
        if (parser.name == "init-param") {
          initParamStarted = true
        }
        if (initParamStarted && parser.name == "param-name" && parser.nextText() == "contextConfigLocation") {
          contextParamFound = true
        }
        if (contextParamFound && parser.name == "param-value") {
          return parser.nextText()
        }
      }
      if (parser.eventType == XmlPullParser.END_TAG) {
        if (parser.name == "init-param") {
          initParamStarted = false
        }
        if (parser.name == "param-name") {
          contextParamFound = true
        }
      }
      parser.next()
    }
    return null
  }

  private fun findEntitiesLocation(contextInputStream: InputStream): String? {
    val parser = XmlPullParserFactory.newInstance().newPullParser()
    parser.setInput(InputStreamReader(contextInputStream))
    var entityLocationFound = false
    while(parser.eventType != XmlPullParser.END_DOCUMENT) {
      if (parser.eventType == XmlPullParser.START_TAG) {
        if (parser.name == "bean" && parser.getAttributeValue("id") == "entity-config-location") {
          entityLocationFound = true
        }
        if (entityLocationFound && parser.name == "constructor-arg") {
          val location = parser.getAttributeValue("value")
          if (location != null) return location
        }
      }
      parser.next()
    }
    return null
  }


  @ExperimentalPathApi
  override fun runActivity(project: Project) {

    val webPackProject = dataProvider.add(WebPackProjectConfig(project.name)).get()

    val webXmlPath = "${project.basePath}/src/main/webapp/WEB-INF/web.xml"
    val file = VirtualFileManager.getInstance().findFileByNioPath(Path(webXmlPath)) ?: return
    val contextLocation = findSpringContextLocation(file.inputStream) ?: return
    val contextFile = VirtualFileManager.getInstance().findFileByNioPath(Path("${project.basePath}/src/main/webapp${contextLocation}")) ?: return

    val entitiesLocation = "${project.basePath}/src/main/webapp${findEntitiesLocation(contextFile.inputStream)}"
    val entitiesFile = VirtualFileManager.getInstance().findFileByNioPath(Path(entitiesLocation)) ?: return
    val parser = SAXParserFactory.newInstance().newSAXParser()

    val handler = XMLEntityHandler()
    parser.parse(entitiesFile.inputStream, handler)
    val entities = handler.result

    entities.forEach {
      dataProvider.add(EntityConfig(it, webPackProject))
    }
  }
}

fun XmlPullParser.getAttributeValue(attributeName: String): String? {
  for (i in 0 until attributeCount) {
    if (getAttributeName(i) == attributeName) return getAttributeValue(i)
  }
  return null
}

fun VirtualFile.content(): String {
  return String(inputStream.readBytes(), charset)
}