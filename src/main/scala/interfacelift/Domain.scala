package interfacelift


trait Domain {

  case class WallpaperMeta(id: String, resolution: String)

  type WallpaperMetaCollection = Vector[WallpaperMeta]

}
