import java.io.{File, FileNotFoundException}
import com.typesafe.config.{Config, ConfigFactory}
import collection.JavaConverters._

object ConfigManager {
  private val conf: Config = readConfigFile("conf/application.conf")

  private def readConfigFile(configFilePath: String): Config = {
    val configFile = new File(configFilePath)

    if (!configFile.exists())
      throw new FileNotFoundException(
        s"Config file $configFilePath does not exist"
      )

    ConfigFactory.parseFile(configFile)
  }

  def getString(configKey: String): String = conf.getString(configKey)

  def getInt(configKey: String): Int = conf.getInt(configKey)

  def getStringList(configKey: String): List[String] =
    conf.getStringList(configKey).asScala.toList

}
