interface IsActive {
    fun targetChoise(targets: String): MutableList<Player>{
        var targetList = mutableListOf<Player>()
        when (targets){
            "ActiveWizard" -> {
                targetList.add(Round.activeWizard)
                return targetList
            }
            "All" -> for(player in Round.players){
                targetList.add(player)
                return targetList
            }
            "Ron" -> {
                targetList.add(Round.ron)
                return targetList
                }
            "Harry" -> {
                targetList.add(Round.garry)
                return targetList
            }
            }
        return targetList
    }
    val targets: String
    val hpChange: Int
    val moneyChange: Int
    val lithningChange: Int
    val cardChange: Int
    fun action(hpChange: Int,
               moneyChange: Int,
               lithningChange: Int,
               cardChange: Int){
        targetChoise(targets)
        for(target in targetChoise(targets)){
            target.hp += hpChange
            target.money += moneyChange
            target.ligthning += lithningChange
            if(cardChange > 0) repeat(cardChange) {
                target.draw()
            }
            if(cardChange < 0) {
                repeat(Math.abs(cardChange)) {
                    target.discard(0)
                }
            }

        }
    }
}