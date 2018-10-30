import com.github.fpopic.album.Album
import com.github.fpopic.song.Song

object ScalaMain {

  def main(args: Array[String]): Unit = {

    val album: Album = Album()
        .withId(123)
        .withSongs(Seq(
          Song().withId(100).withName("mysong100"),
          Song().withId(200).withName("mysong200")))

    println(album)

  }

}
