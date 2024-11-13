import java.util.Random

class Player(name : String) {
    val deck = mutableListOf<PlayerCard>()
    val trash = mutableListOf<PlayerCard>()
    val hand = mutableListOf<PlayerCard>()
    var hp = 10
    var money = 0
    var lithning = 0
    var isActive = false
    init {
        initialDeck()
    }
    private fun initialDeck() {
        for(card in basicCard){
            deck.add(card)
            }
        deck.shuffle()
    }
    fun drawCard(){
        if(deck.isEmpty()){
            deck.addAll(trash)
            trash.clear()
            deck.shuffle()
        }
        hand.add(deck[0])
        deck.removeAt(0)
    }
    fun useCard(card: PlayerCard){
        trash.add(card)
        hand.remove(card)
        card.whenUsed()
    }
    fun discard(card: PlayerCard){
        trash.add(card)
        hand.remove(card)
        card.whenDiscarded()
    }
}