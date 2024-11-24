data class DarkArts(val numberOfChapter: Int,
                    val description: String,
                    val name: String,
                    val action: MutableList<String>,
                    val oneMoreDarkArts: Boolean = false,
                    val banForHeal: Boolean = false,
                    val banForDraw: Boolean = false,
                    val choised: Boolean = false
)

fun playDA(card: DarkArts){
    if(card.banForDraw) Table.banForDraw = true
    if(card.banForHeal) Table.banForHeal = true
    if(card.choised){
        choise(card.action)
    } else {
        for (effect in card.action) {
            val args = effect.split(", ")
            useEffect(args[0], args[1], args[2].toInt())
            if(args[1] == "discard" && Table.activeEnemy.contains(crabbGoyle)){
                useEffect(args[0], "looseHp", 1)
            }
        }
    }
}
val expulso = DarkArts(1, "Активный волшебник теряет 2 ХП",
    "Экспульсо", mutableListOf("self, looseHp, 2"))
val flippendo = DarkArts(1, "Активный волшебник теряет 1 ХП и сбрасыввает карту",
    "Флиппендо", mutableListOf("self, discard, 1", "self, looseHp, 1"))
val stunned = DarkArts(1, "Все волшебники теряют 1 ХП и не могут брать карты в этом ходу",
    "Окаменение", mutableListOf("allPlayers, looseHp, 1"), banForDraw = true)
val volodya = DarkArts(1, "Положите гроб на локацию",
    "Тот-кого-нельзя-называть", mutableListOf("location, addBlackMark, 1"))

val chapter1DAdeck = mutableListOf(expulso, expulso, expulso, volodya, volodya, volodya, flippendo, flippendo, stunned, stunned)