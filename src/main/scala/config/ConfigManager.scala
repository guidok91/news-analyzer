package config
import java.io.{File, FileNotFoundException}
import com.typesafe.config.{Config, ConfigFactory}

object ConfigManager {
  val config: Config = this.readConfigFile("conf/application.conf")

  def readConfigFile(configFilePath: String): Config = {
    val configFile = new File(configFilePath)

    if (!configFile.exists())
      throw new FileNotFoundException(s"Config file $configFilePath does not exist")

    ConfigFactory.parseFile(configFile)
  }
}
