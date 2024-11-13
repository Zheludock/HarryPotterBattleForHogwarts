class Round {
    var roundCount = 0
    fun newRound() {
        var activePlayer = [roundCount % playersCount]
        repeat(location.darkArtsCount()) darkArtsDraw()
        for (enemy in activeEnemys) {
            enemy.abilityEnemy()
        }
        while (true) {
            //Выберите действие:
            when (input) {
                "playcard index" -> {
                    handCard[index].play()
                }

                "use lithning for enemy index" -> {
                    enemy[index].damage++
                    activePlayer.lithning--
                }

                "buy card index" -> {
                    if (player.money >= magazine[index].cost) {
                        player.money -= magazin[index].cost
                        player.trash.add(magazin[index])
                        magazin.removeAt(index)
                    }
                }

                "use ability" -> {
                    player.ability()
                }

                "stop" -> {
                    break
                }
            }
        }
        if (location.blackMark == location.maxBlackMark) {
            if (location.hasNext()) {
                location.next()
            } else {
                return gameOver()
            }
        }
        while (activeEnemy.size < activeEnemyExpected && enemyDeck.hazNext()) {
            addEnemy()
        }
        while (magazin.size == 6) {
            magazin.add(hogwartsDeck[0])
            hogwartsDeck.removeAt[0]
        }
        while (activePlayer.handCard.size() == 0) activePlayer.discard(0)
        activePlayer.money = 0
        activePlayer.lithning = 0
        while (activePlayer.handCard.size() == 5) activePlayer.drawCard()
        roundCount++
    }
}