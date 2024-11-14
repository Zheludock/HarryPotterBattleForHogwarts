class Player(val name: String){
    val handDeck = mutableListOf<HogwartsCard>()
    var hp = 10
    var money = 0
    var ligthning = 0
    val deck = mutableListOf<HogwartsCard>()
    val trash = mutableListOf<HogwartsCard>()

    fun draw(){
        if(deck.isEmpty()){
            deck.addAll(trash)
            trash.clear()
            deck.shuffle()
        }
        handDeck.add(deck.removeAt(0))
    }
    fun discard(index: Int){
        if(handDeck.size > 0) {
            trash.add(handDeck[index])
            handDeck.removeAt(index)
        }
    }

}