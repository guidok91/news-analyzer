package config

import java.io.{File, FileNotFoundException}
import com.typesafe.config.{Config, ConfigFactory}
import collection.JavaConverters._

class ConfigManager(configFilePath: String) {
  private val conf: Config = readConfigFile(
    configFilePath
  )

  private def readConfigFile(configFilePath: String): Config = {
    val configFile = File(configFilePath)

    if (!configFile.exists())
      throw FileNotFoundException(
        s"Config file $configFilePath does not exist"
      )

    ConfigFactory.parseFile(configFile)
  }

  def getString(configKey: String): String = conf.getString(configKey)

  def getInt(configKey: String): Int = conf.getInt(configKey)

  def getStringList(configKey: String): List[String] =
    conf.getStringList(configKey).asScala.toList

}
