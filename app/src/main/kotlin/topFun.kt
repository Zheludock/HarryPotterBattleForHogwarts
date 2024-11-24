
import kotlin.math.max
import kotlin.math.min

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
        //"this" -> return mutableListOf(Table.this)
        "self" -> return mutableListOf(Table.activePlayer)
        "allPlayers" -> return Table.players
        "choisePlayer" -> return choise(Table.players)
        else -> {
            println("wrong target")
            return mutableListOf()
        }
    }
}

fun useEffect(target: String, ability: String, value: Int)
{
    if (target == "location") {
        if (ability == "removeBlackMark" && Table.locations[0].currentBlackMark > 0) {
            Table.locations[0].currentBlackMark =
                max(0, Table.locations[0].currentBlackMark - value)
            if(Table.harryMark){
                harry.useHarryAbility()
                Table.harryMark = false
            }
        }
        if (ability == "addBlackMark") {
            if((Table.locations[0].currentBlackMark + value) < Table.locations[0].maxBlackMark)
            {
                Table.locations[0].currentBlackMark += value
                if(Table.activeEnemy.contains(draco)) draco.useDracoAbility()
                //if(Table.activeEnemy.contains(lucius)) useLuciusAbility
            } else Table.locations[0].currentBlackMark = Table.locations[0].maxBlackMark
        }
    } else {
        val tar = targets(target)
        for (t in tar) {
            val ab = ability.split(", ").toTypedArray()
            for (a in ab) {
                when (a) {
                    "giveMoney" -> t.money += value
                    "giveHp" -> if(!t.isKo && !Table.banForHeal) {
                        t.hp = min(10, t.hp + value)
                    }
                    "giveCard" -> {
                        if(!Table.banForDraw)
                        {
                            repeat(value) {
                                if(t.playerDeck.isEmpty()) {
                                    t.playerDeck.addAll(t.playerTrash)
                                    t.playerTrash.clear()
                                    t.playerDeck.shuffle()
                                }
                                draw(t.playerDeck, t.playerHand)
                            }
                        }
                    }
                    "giveLithning" -> t.lithning += value
                    "looseHp" -> {
                        if(t.equals(harry) && t.playerHand.contains(invisibleManty)) {
                            if(!t.isKo) {
                                t.hp = max(t.hp - 1, 0)
                                if (t.hp == 0) t.ko()
                            }
                        }else if(!t.isKo) {
                            t.hp = max(t.hp - value, 0)
                            if (t.hp == 0) t.ko()
                        }
                    }
                }
            }
        }
    }
}

fun attack(enemy: Enemy) {
    if (Table.activePlayer.lithning > 0) {
        if (enemy.currentLithning < enemy.hpToDie) {
            enemy.currentLithning++
            Table.activePlayer.lithning--
        }
        if (enemy.currentLithning == enemy.hpToDie) {
            enemy.dieEffect()
            Table.enemyTrash.add(enemy)
            Table.activeEnemy.remove(enemy)
            if (Table.enemyDieObs.isNotEmpty()) {
                for (card in Table.enemyDieObs) {
                    for (effect in card.enemyDieEffect!!) {
                        val args = effect.split(", ")
                        useEffect(args[0], args[1], args[2].toInt())
                    }
                }
            }
        }
    }
}



fun buyCard(card: HogwartsCard){
    if(Table.activePlayer.money < card.cost){
        println("you have no money")
        return
    }
    Table.activePlayer.money -= card.cost
    if(Table.canOnTopType == card.cardType){
        Table.activePlayer.playerDeck.add(0, card)
    }else Table.activePlayer.playerTrash.add(card)
    Table.magazine.remove(card)
}
