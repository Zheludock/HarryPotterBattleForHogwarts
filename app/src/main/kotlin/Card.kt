import android.media.Image

interface Card {
    val name: String
    val description: String?
    val chapterNumber: Int
    val height: Int?
    val width: Int?
    val image: Image?
}