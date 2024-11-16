
object Table {
    val numberOfChapter = 1
    val deckEnemy = mutableListOf<Enemy>()
    val activeEnemy = mutableListOf<Enemy>()
    val enemyTrash = mutableListOf<Enemy>()
    val deckDarkArts = mutableListOf<DarkArts>()
    val darkArtsTrash = mutableListOf<DarkArts>()
    val locations = mutableListOf<Location>()
    val hogwartsDeck = mutableListOf<HogwartsCard>()
    val magazine = mutableListOf<HogwartsCard>()
    val numberOfActiveEnemy = when(numberOfChapter){
        1, 2 -> 1
        3, 4 -> 2
        else -> 3
    }
    val players = mutableListOf<Player>(Player("Harry"), Player("Ron"),
        Player("Hermiona"), Player("Nevill"))
    var numRound = 1
    var indexActivePlayer = numRound % players.size
    var activePlayer = players[indexActivePlayer]
    fun round(){
        repeat(locations[0].numberOfDarkArts) {playIt(draw(deckDarkArts, darkArtsTrash))}
        for(enemy in activeEnemy){
            enemy.useAbility()
        }
        while(true){
            println("choise action: ")
            val action = readln()
            when(action){
                "attack" -> null
                "use card" -> null
                "buy card" -> null
            }
            if(locations[0].currentBlackMark == locations[0].maxBlackMark){
                if(locations.size > 1) {
                    locations.removeAt(0)
                }
                else return println("Game Over")
            }
            while(activeEnemy.size < numberOfActiveEnemy) {
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
        }
        numRound++
    }

}