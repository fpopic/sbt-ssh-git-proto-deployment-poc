import static com.github.fpopic.SongOuterClass.Song;
import static com.github.fpopic.AlbumOuterClass.Album;

public class JavaMain {

    public static void main(String[] args) {

        Album album = Album.newBuilder()
                .setId(123)
                .setSongs(0, Song.newBuilder().setId(100).setName("mysong100"))
                .setSongs(1, Song.newBuilder().setId(200).setName("mysong200"))
                .build();

        System.out.println(album);
    }

}
