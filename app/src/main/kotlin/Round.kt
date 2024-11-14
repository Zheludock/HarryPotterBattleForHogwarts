object Round {
    val ron = Player("Ron")
    val garry = Player("Harry")
    val hermiona = Player("Hermiona")
    val nevill = Player("Nevill")
    val players = mutableListOf(ron, garry, hermiona, nevill)
    val darkArts = mutableListOf<DarkArts>()
    val trashDarkArts = mutableListOf<DarkArts>()
    var roundNumber = 0
    val countPlayers = players.size
    var indexOfActive = roundNumber % countPlayers
    var activeWizard = players[indexOfActive]
    fun newRound() {

        roundNumber++
    }
}