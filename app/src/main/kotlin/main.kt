
fun main(){

    Table.deckDarkArts.shuffle()
    Table.deckEnemy.shuffle()
    Table.hogwartsDeck.shuffle()
    while (Table.magazine.size < 6){
        draw(Table.hogwartsDeck, Table.magazine)
    }
    while(Table.activeEnemy.size < Table.numberOfActiveEnemy){
        draw(Table.deckEnemy, Table.activeEnemy)
    }
    for(player in Table.players){
        player.playerDeck.shuffle()
        repeat(5){draw(player.playerDeck, player.playerHand)}
    }
    while (true) {
        Table.round()
    }
}