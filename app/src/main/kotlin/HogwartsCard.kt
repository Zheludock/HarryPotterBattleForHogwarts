data class HogwartsCard(val name: String, val numberOfChapter: Int, val cardType: String, val cost: Int,
                   val description: String, val moneySelf: Int = 0
) {
    //default parameters
    val hpSelf = 0
    val hpAll = 0
    val moneyAll = 0
    val lithningSelf = 0
    val lithningAll = 0
    val cardSelf = 0
    val cardAll = 0
    val blackMarkDelete = 0
    val choise = false
    val discardEffect = false
    val anyTarget = false


}

