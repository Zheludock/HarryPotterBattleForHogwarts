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
        }
    }
    val playerHand = mutableListOf<HogwartsCard>()
    val playerTrash = mutableListOf<HogwartsCard>()
    var hp = 10
    var money = 0
    var lithning = 0
    var ko = false
}