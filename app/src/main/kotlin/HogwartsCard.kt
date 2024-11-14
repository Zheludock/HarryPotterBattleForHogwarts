import android.media.Image

data class HogwartsCard(
    override val name: String,
    override val targets: String,
    override val hpChange: Int,
    override val moneyChange: Int,
    override val lithningChange: Int,
    override val cardChange: Int
) : Card, IsActive {
    override val description: String?
        get() = null
    override val chapterNumber: Int
        get() = 1
    override val height: Int?
        get() = 100
    override val width: Int?
        get() = 50
    override val image: Image?
        get() = null
}