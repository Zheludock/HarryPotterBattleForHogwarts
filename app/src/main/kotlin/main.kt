fun <T> draw(deck: MutableList<T>, to: MutableList<T>, index: Int){
    to.add(deck.removeAt(index))
}
fun main(){
    val players = mutableListOf<Player>(Player("Harry"), Player("Ron"),
        Player("Hermiona"), Player("Nevill"))
    println(players[0].playerDeck)
}