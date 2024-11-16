fun <T> draw(deck: MutableList<T>, to: MutableList<T>, index: Int = 0): T{
    to.add(deck.removeAt(index))
    return to.last()
}
fun <T> choise(deck: MutableList<T>): MutableList<T> {
    println("Choise in $deck")
    val index: Int = readln().toInt()
    val card = deck[index]
    return mutableListOf(card)
}

fun targets(target: String): MutableList<Player> {
    when (target) {
        "self" -> return mutableListOf(Table.activePlayer)
        "allPlayers" -> return Table.players
        "choisePlayer" -> return choise(Table.players)
        else -> {
            println("wrong target")
            return mutableListOf()
        }
    }
}
fun playDA(){

}
