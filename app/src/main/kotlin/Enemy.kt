data class Enemy(val name: String, val description: String, val numberOfChapter: Int,
            val hpToDie: Int, val effectWhenDie: String) {
    fun dieEffect(effectWhenDie: String){
        null
    }
    fun useAbility(){
        null
    }
    var currentLithning = 0
}