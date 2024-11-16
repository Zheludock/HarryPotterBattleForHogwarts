class Player(val name: String) {
    val playerDeck = mutableListOf<HogwartsCard>()
    init {
        when(name){
            "Harry" -> repeat(7) {playerDeck.add(HogwartsCard("Alohomora", 1, "Spell", 0, "Дает 1 монету"))}
        }
    }
    val playerHand = mutableListOf<HogwartsCard>()
    val playerTrash = mutableListOf<HogwartsCard>()
    var hp = 10
    var money = 0
    var lithning = 0
    var ko = false
}