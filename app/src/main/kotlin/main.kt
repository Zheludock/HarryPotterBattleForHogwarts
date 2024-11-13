fun main(){
    val locationList = ArrayList<Location>()
    val locationInScenario = 2
    val activeLocation = 0
    while (locationList.size < locationInScenario){
        locationList.add(Location())
    }
    val playersCount = 4
    val players = mutableListOf<Player>()
    while (players.size == playersCount){
        players.add(Player("Me"))
    }
}