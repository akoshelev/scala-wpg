package network


object WallpaperGrabber {
  case class Start()
  case class WallpaperDiscovered(name: String, data: Seq[Byte])
  case class Stop()
}

