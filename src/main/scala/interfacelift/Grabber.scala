package interfacelift

import akka.actor.{Actor, ActorLogging, Props}
import network.WallpaperGrabber

class Grabber extends Actor with ActorLogging {

  private val settings = Settings(context.system)

  val apiKey = settings.apiKey
  val resolution = settings.resolution
  val wallpapersListStore = context.actorOf(Props(classOf[WallpapersListStore], apiKey, resolution))


  def receive: Receive = {
    // todo: MDC with custom execution context
    case WallpaperGrabber.Start =>
      log.info(this.getClass.getName + s" started with the following settings: " +
      s"\napiKey = $apiKey." +
      s"\nresolution = $resolution")
      wallpapersListStore ! WallpapersListStore.FetchMetaList(10)

    case WallpaperGrabber.Stop => log.info(this.getClass.getName + " stopped.")
  }

}
