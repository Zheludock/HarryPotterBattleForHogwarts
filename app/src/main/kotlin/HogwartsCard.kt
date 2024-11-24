data class HogwartsCard(val name: String, val numberOfChapter: Int, val cardType: String,
                        val cost: Int,
                        val description: String,
                        val effects: MutableList<String>,
                        val choised: Boolean = false,
                        val discardEffect: Boolean = false,
                        val passiveEffect: Boolean = false,
                        val discardedEffect: MutableList<String>? = null,
                        val enemyDieObserver: Boolean = false,
                        val enemyDieEffect: MutableList<String>? = null,
                        val canOnTop: String? = null
){
    override fun toString(): String {
        return "$name, $description, cost: $cost"
    }
}

fun playCard(card: HogwartsCard){
    if(card.choised){
        choise(card.effects)
    } else {
        for (effect in card.effects) {
            val args = effect.split(", ")
            useEffect(args[0], args[1], args[2].toInt())
        }
    }
    if(card.enemyDieObserver){
        Table.enemyDieObs.add(card)
    }
    if(Table.activePlayer.equals(ron) && card.cardType.equals("ally")){
        Table.allyPlayed++
    }
    if (card.canOnTop != null) {
        Table.canOnTopType = card.canOnTop
    }
}

fun useCard(card: HogwartsCard){
    playCard(card)
    Table.activePlayer.playerTrash.add(card)
    Table.activePlayer.playerHand.remove(card)
}

fun useDiscardEffect(card: HogwartsCard){
    for(effect in card.discardedEffect!!){
        val args = effect.split(", ")
        useEffect(args[0], args[1], args[2].toInt())
    }
}

//basic
val alohomora = HogwartsCard("Алохомора", 1,
    "spell", 0, "Получите 1 галлеон", mutableListOf("self, giveMoney, 1"))
val trevor = HogwartsCard("Тревор", 1, "ally", 0,
    "Получите 2 хп или 1 молнию",
    mutableListOf("self, giveHp, 2", "self, giveLithning, 1",), true)
val remembrall = HogwartsCard("Напоминалка", 1, "item", 0,
    "Получите 1 монету, если сбросили - получите 2 монеты", mutableListOf("self, giveMoney, 1"),
    discardEffect = true, discardedEffect = mutableListOf("self, giveMonney, 2"))
val manragore = HogwartsCard("Корень мандрагоры", 1, "item", 0,
    "Получите 1 молнию или дайте 2 хп любому волшебнику",
    mutableListOf("self, giveLithning, 1", "choisePlayer, giveHp, 2"),  true)
val hedwig = HogwartsCard("Букля", 1, "ally", 0,
    "Получите 1 молнию или 2 хп", mutableListOf("self, giveHp, 2", "self, giveLithning, 1"),  true)
val firebolt = HogwartsCard("Молния", 1,"item", 0,
    "Получите 1 молнию, если победите врага - получите 1 монету", mutableListOf("self, giveLithning, 1"),
    enemyDieObserver = true, enemyDieEffect = mutableListOf("self, giveMoney, 1"))
val invisibleManty = HogwartsCard("Мантия-невидимка", 1, "item", 0,
    "Получите 1 монету, вы не можете получить больше 1 урона от злодеев или событий темных искусств",
    mutableListOf("self, giveMoney, 1"))
val zhivoglot = HogwartsCard("Живоглот", 1, "ally", 0,"Получите 2 ХП или 1 молнию",
    mutableListOf("self, giveHp, 2", "self, giveLithning, 1"), choised = true)
val timeTurner = HogwartsCard("Маховик времени", 1, "item", 0, "Получите 1 монету, если купите заклинание - можете положить его наверх колоды, а не в сброс",
    mutableListOf("self, giveMoney, 1"), canOnTop = "spell")
val bardBeadle = HogwartsCard("Сказки барда Бидля", 1, "item", 0, "Получите 2 монеты, или все волшебники получат по монете",
    mutableListOf("self, giveMoney, 2", "allPlayers, giveMoney, 1"), choised = true)
val cleansweep = HogwartsCard("Чистомет 11", 1,"item", 0,
    "Получите 1 молнию, если победите врага - получите 1 монету", mutableListOf("self, giveLithning, 1"),
    enemyDieObserver = true, enemyDieEffect = mutableListOf("self, giveMoney, 1"))
val pigwidgeon = HogwartsCard("Сычик", 1, "ally", 0,
    "Получите 1 молнию или 2 хп", mutableListOf("self, giveHp, 2", "self, giveLithning, 1"),  true)
val draje = HogwartsCard("Драже бурти-бурти", 1, "item", 0,
    "Получите 1 монету, получите 1 молнию за каждого сыгранного союзника",
    mutableListOf("self, giveMoney, 1", "self, giveLithning, ${Table.allyPlayed}"))

//spells
val lumos = HogwartsCard("Люмос", 1, "spell", 4,
    "Все волшебники берут по карте", mutableListOf("allPlayers, giveCards, 1"))
val reparo = HogwartsCard("Репаро", 1, "spell", 3,
    "Получите 2 монеты или возьмите карту", mutableListOf("self, giveMoney, 2", "self, giveCard, 1"), choised = true)
val insendio = HogwartsCard("Инсендио", 1, "spell", 4,
    "Получите молнию и возьмите карту", mutableListOf("self, giveLithning, 1", "self, giveCard, 1"))
val leviosa = HogwartsCard("Вингардиум левиоса", 1, "spell", 2,
    "Получите 1 монету, если купите предмет - положите его на верх колоды", mutableListOf("self, giveMoney, 1"), canOnTop = "item")
val descendo = HogwartsCard("Десцендо", 1, "spell", 5,
    "Получите 2 молнии", mutableListOf("self, giveLithning, 2"))
val sunshine = HogwartsCard("Сердце львицы, взгляд волчицы", 1, "spell", 4,
    "Получите молнию и возьмите карту", mutableListOf("self, looseHp, 1", "self, giveCard, 2"))

//items
val dittany = HogwartsCard("Экстракт бадьяна", 1, "item", 2,
    "Дайте 2 ХП любому волшебнику", mutableListOf("choisePlayer, giveHp, 2"))
val qwiddich = HogwartsCard("Набор для квиддича", 1, "item", 3,
    "Получите 1 ХП и 1 молнию", mutableListOf("self, giveHp, 1", "self, giveLithning, 1"))
val hat = HogwartsCard("Распределяющая шляпа", 1, "item", 4,
    "Получите 2 монеты, если купите союзника, можете положить его на верх колоды", mutableListOf("self, giveMoney, 2"), canOnTop = "ally")
val snitch = HogwartsCard("Снитч", 1, "item", 5,
    "Получите 2 монеты и карту", mutableListOf("self, giveMoney, 2", "self, giveCard, 1"))

//ally
val wood = HogwartsCard("Оливер Вуд", 1,"ally", 3,
    "Получите 1 молнию, если победите врага - дайте 2 ХП любому волшебнику", mutableListOf("self, giveLithning, 1"),
    enemyDieObserver = true, enemyDieEffect = mutableListOf("choisePlayer, giveHp, 2"))
val hagrid = HogwartsCard("Хагрид", 1,"ally", 4,
    "Получите 1 молнию, все волшебники получают 1 ХП", mutableListOf("self, giveLithning, 1", "allPlayers, giveHp, 1"))
val dumbledor = HogwartsCard("Дамблдор", 1,"ally", 8,
    "Все волшебник получают по 1 монете, карте, молнии, ХП",
    mutableListOf("allPlayers, giveHp, 1", "allPlayers, giveMoney, 1", "allPlayers, giveCard, 1", "allPlayers, giveLithning, 1"))

val chapter1hogCard = mutableListOf(dumbledor, hagrid, wood, hat, snitch, qwiddich, qwiddich, qwiddich, qwiddich, dittany, dittany, dittany,
    dittany, lumos, lumos, reparo, reparo, reparo, reparo, reparo, reparo, insendio, insendio, insendio, insendio, leviosa, leviosa, leviosa,
    descendo, descendo, sunshine)