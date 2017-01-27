package interfacelift

import akka.actor.Actor.Receive
import akka.actor._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal

import scala.util._
import scala.concurrent.duration._


object WallpapersListStore {

}

class WallpapersListStore(apiKey: String, resolution: String) extends Actor with ActorLogging with Domain {
  import akka.pattern.pipe
  import context.dispatcher

  val http = Http(context.system)

  def receive: Receive = {

    case MakeRequest =>
      http.singleRequest(HttpRequest(uri = "http://www.mocky.io/v2/579a8d9c110000b40bcb7667"))
        .pipeTo(self)

    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      unwrapMeta(entity).pipeTo(self)

    case HttpResponse(code, _, _, _) =>
      log.error(s"an error occurred while retrieving wallpapers metadata. http status code = $code")
      scheduleNextRequest
  }

  def scheduleNextRequest = {
    // todo: exponential backoff
    val delay = 1.minute
    context.system.scheduler.scheduleOnce(delay, self, MakeRequest)
  }

  def unwrapMeta(entity: HttpEntity) = {
    Unmarshal(entity).to[WallpaperMetaCollection] onComplete {
      case Success(wallpapersMeta) ⇒
        log.info(s"successfully received new ${wallpapersMeta.length} wallpaper metadata.")
        self.tell(wallpapersMeta, self)
      case Failure(e) ⇒
        log.error(e, "an error occurred while parsing response from server")
    }
  }

  case class MakeRequest()
}

//
//object WallpapersListStoreCommands {
//  case class FetchMetaList(count: Int)
//}
//
//object WallpapersListStoreEvents {
//  case class MetaReady(metaList: Seq[WallpaperItemMeta])
//  case class RetrieveError(error: String)
//}
//
//class WallpapersListStore(apiKey: String, resolution: String) extends Actor with ActorLogging {
//
//  import akka.pattern.pipe
//  import context.dispatcher
//
//  val http = Http(context.system)
//
//  def awaitingResponse(replyTo: ActorRef): Receive = {
//    case HttpResponse(StatusCodes.OK, headers, entity: HttpEntity, _) =>
//      Unmarshal(entity).to[Seq[WallpaperItemMeta]] onComplete {
//        case Success(wallpapersMeta) ⇒
//          replyTo ! WallpapersListStoreEvents.MetaReady(wallpapersMeta)
//        case Failure(e) ⇒
//          replyTo ! WallpapersListStoreEvents.RetrieveError(e)
//      }
//      replyTo ! WallpapersListStoreEvents.MetaReady()
//    case HttpResponse(code, _, _, _) =>
//      replyTo ! WallpapersListStoreEvents.RetrieveError(s"unable to retrieve wallpapers: server returned $code")
//  }
//
//  def awaitingCommand(): Receive = {
//    case WallpapersListStoreCommands.FetchMetaList(count) =>
//      http.singleRequest(HttpRequest(uri = "http://www.mocky.io/v2/579a8d9c110000b40bcb7667"))
//        .pipeTo(self)
//      context become awaitingResponse(sender())
//  }
//}
