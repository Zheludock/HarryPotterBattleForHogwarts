fun darkArtsDraw(){
    if(darkDeck.isEmpty()){
        darkDeck.addAll(darkTrash)
        darkTrash.clear()
        darkDeck.shuffle()
    }
    whenUsed(darkDeck[0])
    darkDeck.removeAt(0)
}