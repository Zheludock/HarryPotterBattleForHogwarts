object Table {
    val numberOfChapter = 1
    val deckEnemy = mutableListOf<Enemy>()
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

}