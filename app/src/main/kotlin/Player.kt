class Player(val name: String) {
    val playerDeck = mutableListOf<HogwartsCard>()
    init {
        when(name){
            "Nevill" -> {
                repeat(7) {playerDeck.add(alohomora)}
                playerDeck.add(manragore)
                playerDeck.add(trevor)
                playerDeck.add(remembrall)
                playerDeck.shuffle()
            }
            "Harry" -> {
                repeat(7) {playerDeck.add(alohomora)}
                playerDeck.add(hedwig)
                playerDeck.add(firebolt)
                playerDeck.add(invisibleManty)
                playerDeck.shuffle()
            }
            "Hermiona" -> {
                repeat(7) {playerDeck.add(alohomora)}
                playerDeck.add(zhivoglot)
                playerDeck.add(timeTurner)
                playerDeck.add(bardBeadle)
                playerDeck.shuffle()
            }
            "Ron" -> {
                repeat(7) {playerDeck.add(alohomora)}
                playerDeck.add(pigwidgeon)
                playerDeck.add(draje)
                playerDeck.add(cleansweep)
                playerDeck.shuffle()
            }
        }
    }
    val playerHand = mutableListOf<HogwartsCard>()
    val playerTrash = mutableListOf<HogwartsCard>()
    var hp = 10
    var money = 0
    var lithning = 0
    var isKo = false
    fun ko(){
        isKo = true
        val cardToTrash = (playerHand.size + 1)/2
        repeat(cardToTrash){
            discard()
        }
        lithning = 0
        money = 0
    }
    fun discard(){
        val card = choise(playerHand)[0]
        playerTrash.add(card)
        playerDeck.remove(card)
        if(card.discardEffect){
            useDiscardEffect(card)
        }
    }
    fun useHarryAbility(){
        when(Table.numberOfChapter) {
            1,2 -> null
        }
    }
}
val harry = Player("Harry")
val ron = Player("Ron")
val hermiona = Player("Hermiona")
val nevill = Player("Nevill")


