data class HogwartsCard(val name: String, val numberOfChapter: Int, val cardType: String, val cost: Int,
                        val description: String,
                        val target: String = "self",
                        val ability: String = "giveMoney",
                        val value: Int = 1
) {
    //default parameters
    val choise = false
    val discardEffect = false

}

fun playCard(card: HogwartsCard){
    if(card.target == "lovation") {
        if(card.ability == "removeBlackMark") Table.locations[0].currentBlackMark -= card.value
    } else {
        val tar = targets(card.target)
        for(t in tar){
            val ab = card.ability.split(", ").toTypedArray()
            for (a in ab) {
                when (a) {
                    "giveMoney" -> t.money += card.value
                    "giveHp" -> t.hp += card.value
                    "giveCard" -> repeat(card.value) { draw(t.playerDeck, t.playerHand) }
                    "giveLithning" -> t.lithning += card.value
                }
            }
        }
    }
}

val alohomora = HogwartsCard("Алохомора", 1, "Spell", 0, "Give 1 money")
val trevor = choise(
    mutableListOf( HogwartsCard("Тревор", 1, "ally", 0,
    "Give 1 lithning or 2hp", target = "self", ability = "giveHp", 2), HogwartsCard("Тревор", 1, "ally", 0,
        "Give 1 lithning or 2hp", target = "self", ability = "giveHp", 2)))
