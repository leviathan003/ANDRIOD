package com.example.memory_game.models

import com.example.memory_game.utils.ASSETS

class MemoryGame(private val boardSize: BoardSize, private val customImgs: List<String>?) {

    private var numCardFlips:Int=0
    private var indexofSingleFlippedCard:Int? = null
    val cards: List<MemoryCard>
    var numPair = 0

    init {
        if(customImgs==null){
            val chosenImgs = ASSETS.shuffled().take(boardSize.getNumPair())
            val randomImgs = (chosenImgs+chosenImgs).shuffled()
            cards = randomImgs.map { MemoryCard(it) }
        }
        else{
            val randomImages = (customImgs + customImgs).shuffled()
            cards = randomImages.map{MemoryCard(it.hashCode(),it)}
        }
    }

    fun flipCard(pos: Int):Boolean {
        numCardFlips++
        val card = cards[pos]
        var foundMatch=false
        if(indexofSingleFlippedCard==null){
            restore()
            indexofSingleFlippedCard = pos
        }
        else{
            foundMatch = checkMatch(indexofSingleFlippedCard!!,pos)
            indexofSingleFlippedCard = null
        }
        card.isFaceUp = !card.isFaceUp
        return foundMatch
    }

    private fun checkMatch(indexofSingleFlippedCard: Int, pos: Int): Boolean {
        if(cards[pos].identifier != cards[indexofSingleFlippedCard].identifier){
            return false
        }
        cards[pos].isMatched = true
        cards[indexofSingleFlippedCard].isMatched = true
        numPair++
        return true
    }

    private fun restore() {
        for(card in cards){
            if(!card.isMatched){
                card.isFaceUp = false;
            }
        }
    }

    fun haveWon(): Boolean {
        return numPair == boardSize.getNumPair()
    }

    fun isCardFaceUp(pos: Int): Boolean {
        return cards[pos].isFaceUp
    }

    fun getMovesMade(): Int {
        return numCardFlips/2
    }
}