import kotlin.math.max

data class Enemy(val name: String, val description: String, val numberOfChapter: Int,
                 val hpToDie: Int, val effectWhenDie: MutableList<String>, val ability: MutableList<String>? = null) {
    fun useAbility(){
        if (ability != null) {
            for(effect in ability){
                val args = effect.split(", ")
                useEffect(args[0], args[1], args[2].toInt())
            }
        }
    }
    fun dieEffect(){
        for(effect in effectWhenDie){
            val args = effect.split(", ")
            useEffect(args[0], args[1], args[2].toInt())
        }
    }
    fun useDracoAbility(){
        if(!Table.activePlayer.isKo) {
            Table.activePlayer.hp = max(Table.activePlayer.hp - 2, 0)
            if(Table.activePlayer.hp == 0) Table.activePlayer.ko()
        }
    }
    var currentLithning = 0
}

val draco = Enemy("Драко Малфой", "Если на локацию кладется гроб - активный волшебник теряет 2 хп",
    1, 6, mutableListOf("location, removeBlackMark, 1"))
val crabbGoyle = Enemy("Крэбб и Гойл", "Если событие темных искусств или злодей вынуждает вас сбросить карту - потеряйте 1ХП",
    1, 5, mutableListOf("allPlayers, giveCard, 1"))
val quirell = Enemy("Квирелл", "Активный волшебник теряет 2ХП", 1, 6,
    effectWhenDie = mutableListOf("allPlayers, giveHp, 1", "allPlayers, giveMoney, 1"), ability = mutableListOf("self, looseHp, 1"))

val chapter1Enemy = mutableListOf(draco, crabbGoyle, quirell)