package interfacelift

import akka.http.scaladsl.model.Uri
import spray.json.{DefaultJsonProtocol, DeserializationException, JsNumber, JsString, JsValue, RootJsonFormat}

object Marshallers extends DefaultJsonProtocol with Domain {

  implicit val wallpaperItemMetaJsonFormat = jsonFormat2(WallpaperMeta)

//  implicit object WallpaperJsonFormat extends RootJsonFormat[Wallpaper] {
//    def write(obj: Wallpaper): JsValue = ???
//
//    def read(json: JsValue): Wallpaper = {
//      json.asJsObject.getFields("wallpaper_id", "download_url") match {
//        case Seq(JsNumber(id), JsString(uri)) =>
//          Wallpaper(id.intValue(), Uri(uri))
//        case _ => throw new DeserializationException("Color expected")
//      }
//    }
//  }
}


