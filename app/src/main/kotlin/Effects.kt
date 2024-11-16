class Effects {



}
fun useEffect(target: String, ability: String, value: Int)
{
    if (target == "location") {
        if (ability == "removeBlackMark") Table.locations[0].currentBlackMark -= value
    } else {
        val tar = targets(target)
        for (t in tar) {
            val ab = ability.split(", ").toTypedArray()
            for (a in ab) {
                when (a) {
                    "giveMoney" -> t.money += value
                    "giveHp" -> t.hp += value
                    "giveCard" -> repeat(value) { draw(t.playerDeck, t.playerHand) }
                    "giveLithning" -> t.lithning += value
                }
            }
        }
    }
}