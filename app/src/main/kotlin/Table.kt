object Table {
    val numberOfChapter = 1
    val deckEnemy = chapter1Enemy
    val activeEnemy = mutableListOf<Enemy>()
    val enemyTrash = mutableListOf<Enemy>()
    val deckDarkArts = chapter1DAdeck
    val darkArtsTrash = mutableListOf<DarkArts>()
    val locations = mutableListOf(kosoleya, mirror)
    val hogwartsDeck = chapter1hogCard
    val magazine = mutableListOf<HogwartsCard>()
    val enemyDieObs = mutableListOf<HogwartsCard>()
    var banForDraw = false
    var banForHeal = false
    var harryMark = true
    var allyPlayed = 0
    var canOnTopType: String? = null
    val numberOfActiveEnemy = when(numberOfChapter){
        1, 2 -> 1
        3, 4 -> 2
        else -> 3
    }
    var endRound: Boolean = false
    val players = mutableListOf(harry, ron, hermiona, nevill)
    var numRound = 1
    var indexActivePlayer = numRound % players.size
    var activePlayer = players[indexActivePlayer]
    fun round(){
        endRound = false
        repeat(locations[0].numberOfDarkArts) {
            if(deckDarkArts.size == 0) {
                deckDarkArts.addAll(darkArtsTrash)
                deckDarkArts.shuffle()
                darkArtsTrash.clear()
            }
            val card = deckDarkArts[0]
            playDA(card)
            darkArtsTrash.add(card)
            deckDarkArts.remove(card)
        }
        for(enemy in activeEnemy){
            enemy.useAbility()
        }
        while(endRound == false){

        }
        if(locations[0].currentBlackMark == locations[0].maxBlackMark){
            if(locations.size > 1) {
                locations.removeAt(0)
            }
            else return println("Game Over")
        }
        while(activeEnemy.size < numberOfActiveEnemy && deckEnemy.isNotEmpty()) {
            draw(deckEnemy, activeEnemy)
        }
        while(magazine.size < 6){
            draw(hogwartsDeck, magazine)
        }
        while(activePlayer.playerHand.size > 0){
            draw(activePlayer.playerHand, activePlayer.playerTrash)
        }
        activePlayer.money = 0
        activePlayer.lithning = 0
        while(activePlayer.playerHand.size < 6){
            draw(activePlayer.playerDeck, activePlayer.playerHand)
        }
        if(activePlayer.isKo){
            activePlayer.hp = 10
            activePlayer.isKo = false
        }
        if(activeEnemy.isEmpty()) return println("Victory!")
        banForDraw = false
        banForHeal = false
        harryMark = true
        canOnTopType = null
        allyPlayed = 0
        numRound++
    }


}