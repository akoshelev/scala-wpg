import akka.actor.{ActorSystem, Props}
import interfacelift.Grabber
import network.WallpaperGrabber

import scala.concurrent.duration._
import scala.concurrent.Await

object Main extends App {

  // Initialize the ActorSystem
  val actorSystem = ActorSystem("wpGrabber")

  val grabberActorRef = actorSystem.actorOf(Props[Grabber])

  grabberActorRef ! WallpaperGrabber.Start

  // Let's wait for a couple of seconds before we shut down the system
  Thread.sleep (10000)

  // Shut down the ActorSystem.
  Await.result(actorSystem.terminate(), 3.seconds)
}
