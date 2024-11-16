data class HogwartsCard(val name: String, val numberOfChapter: Int, val cardType: String,
                        val cost: Int,
                        val description: String,
                        val effects: MutableList<String>,
                        val choised: Boolean = false,
                        val discardEffect: Boolean = false,
                        val passiveEffect: Boolean = false
) {

}

fun playCard(card: HogwartsCard){
    for(effect in card.effects){
        val args = effect.split(", ")
        useEffect(args[0], args[1], args[2].toInt())
    }
}

val alohomora = HogwartsCard("Алохомора", 1,
    "ipell", 0, "Получите 1 галлеон", mutableListOf("self, giveMoney, 1"))
val trevor = HogwartsCard("Тревор", 1, "ally", 0,
    "Получите 2 хп или 1 молнию",
    mutableListOf("self, giveHp, 2", "self, giveLithning, 1",), true)
val remembrall = HogwartsCard("Напоминалка", 1, "item", 0,
    "Получите 1 монету, если сбросили - получите 2 монеты", mutableListOf("self, giveMoney, 1"),
    discardEffect = true)
val manragore = HogwartsCard("Корень мандрагоры", 1, "item", 0,
    "Получите 1 молнию или дайте 2 хп любому волшебнику",
    mutableListOf("self, giveLithning, 1", "choisePlayer, giveHp, 2"))
val hedwig = HogwartsCard("Букля", 1, "ally", 0,
    "Получите 1 молнию или 2 хп", mutableListOf("self, giveHp, 2", "self, giveLithning, 1"))
val firebolt = HogwartsCard("Молния", 1,"item", 0,
    "Получите 1 молнию, если победите врага - получите 1 монету", mutableListOf("self, giveLithning, 1"))
val invisibleManty = HogwartsCard("Мантия-невидимка", 1, "item", 0,
    "Получите 1 монету, вы не можете получить больше 1 урона от злодеев или событий темных искусств",
    mutableListOf("self, giveMoney, 1"))