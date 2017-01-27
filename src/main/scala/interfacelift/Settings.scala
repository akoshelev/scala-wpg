package interfacelift

import akka.actor.{ExtendedActorSystem, Extension, ExtensionId, ExtensionIdProvider}
import com.typesafe.config.Config

import scala.concurrent.duration.Duration

class SettingsImpl(config: Config) extends Extension {

  private val systemName = "wallpaper-grabber"

  val apiKey = config.getString(s"$systemName.interface-lift.key")
  val resolution = config.getString(s"$systemName.resolution")

}
object Settings extends ExtensionId[SettingsImpl] with ExtensionIdProvider {

  override def lookup = Settings

  override def createExtension(system: ExtendedActorSystem) =
    new SettingsImpl(system.settings.config)

}