package com.example.wargame

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.util.Random

class TestActivity : AppCompatActivity() {


    lateinit var p2kortEtt:  ImageView
    lateinit var p2kortTva:  ImageView
    lateinit var p2kortTre:  ImageView
    lateinit var p2kortFyra: ImageView

    lateinit var shown_card1: ImageView //to show the cards
    lateinit var shown_card2: ImageView //to show the cards

    lateinit var points_player1: TextView //points
    lateinit var points_player2: TextView //points

    lateinit var P1_hand: ImageView

    lateinit var cardsLeftP1: TextView //cards at hand
    lateinit var cardsLeftP2: TextView



    lateinit var cardSoldier1: ImageView
    lateinit var cardSoldier2: ImageView
    lateinit var cardSoldier3: ImageView
    lateinit var cardSoldier4: ImageView
    lateinit var cardSoldier5: ImageView
    lateinit var cardSoldier6: ImageView


    lateinit var againButton: ImageButton //Play again button


    lateinit var random: Random

    var player1 = 0
    var player2 = 0
    var initialCardsP1 = 0
    var initialCardsP2 = 0

    var cardsArray = intArrayOf(
        R.drawable.hearts2,  R.drawable.clover2,  R.drawable.diams2,  R.drawable.spade2,
        R.drawable.hearts3,  R.drawable.clover3,  R.drawable.diams3,  R.drawable.spade3,
        R.drawable.hearts4,  R.drawable.clover4,  R.drawable.diams4,  R.drawable.spade4,
        R.drawable.hearts5,  R.drawable.clover5,  R.drawable.diams5,  R.drawable.spade5,
        R.drawable.hearts6,  R.drawable.clover6,  R.drawable.diams6,  R.drawable.spade6,
        R.drawable.hearts7,  R.drawable.clover7,  R.drawable.diams7,  R.drawable.spade7,
        R.drawable.hearts8,  R.drawable.clover8,  R.drawable.diams8,  R.drawable.spade8,
        R.drawable.hearts9,  R.drawable.clover9,  R.drawable.diams9,  R.drawable.spade9,
        R.drawable.hearts10, R.drawable.clover10, R.drawable.diams10, R.drawable.spade10,
        R.drawable.hearts11, R.drawable.clover11, R.drawable.diams11, R.drawable.spade11,
        R.drawable.hearts12, R.drawable.clover12, R.drawable.diams12, R.drawable.spade12,
        R.drawable.hearts13, R.drawable.clover13, R.drawable.diams13, R.drawable.spade13,
        R.drawable.hearts14, R.drawable.clover14, R.drawable.diams14, R.drawable.spade14,
    )
    //Divide my card array list in to two decks for the players
    lateinit var deckOfCardsP1: MutableList<Int>
   lateinit var deckOfCardsP2: MutableList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        random = Random()

        //init objects
        shown_card1 = findViewById(R.id.shown_card1)
        shown_card2 = findViewById(R.id.shown_card2)

        shown_card1.setImageResource(R.drawable.red_cardimage)
        shown_card2.setImageResource(R.drawable.red_cardimage)

        points_player1 = findViewById(R.id.points_player1)
        points_player2 = findViewById(R.id.points_player2)


        cardSoldier1 = findViewById(R.id.cardSoldier1)
        cardSoldier2 = findViewById(R.id.cardSoldier2)
        cardSoldier3 = findViewById(R.id.cardSoldier3)
        cardSoldier4 = findViewById(R.id.cardSoldier4)
        cardSoldier5 = findViewById(R.id.cardSoldier5)
        cardSoldier6 = findViewById(R.id.cardSoldier6)


        p2kortEtt = findViewById(R.id.p2kortEtt2)
        p2kortTva = findViewById(R.id.p2kortTva2)
        p2kortTre = findViewById(R.id.p2kortTre2)
        p2kortFyra = findViewById(R.id.p2kortFyra2)


        P1_hand = findViewById(R.id.P1_hand)

        cardsLeftP1 = findViewById(R.id.cardsLeftP1) // reference to where on the screen to show the info
        cardsLeftP2 = findViewById(R.id.cardsLeftP2)

        val midpoint = cardsArray.size / 2
        deckOfCardsP1 = cardsArray.toMutableList().subList(0, midpoint)
        deckOfCardsP2 = cardsArray.toMutableList().subList(midpoint, cardsArray.size)

        numberOfCards()

        val p2kortEttIndex = random.nextInt(deckOfCardsP2.size)
        val p2kortTvaIndex = random.nextInt(deckOfCardsP2.size)
        val p2kortTreIndex = random.nextInt(deckOfCardsP2.size)
        val p2kortFyraIndex = random.nextInt(deckOfCardsP2.size)

        setCardImage(deckOfCardsP2[p2kortEttIndex], p2kortEtt)
        setCardImage(deckOfCardsP2[p2kortTvaIndex], p2kortTva)
        setCardImage(deckOfCardsP2[p2kortTreIndex], p2kortTre)
        setCardImage(deckOfCardsP2[p2kortFyraIndex], p2kortFyra)


        p2kortEtt.setOnClickListener {
            chosenCardtoDeal(p2kortEtt, shown_card2)
            val newP2kortIndex = random.nextInt(deckOfCardsP2.size)
            setCardImage(deckOfCardsP2[newP2kortIndex], p2kortEtt)
        }
       /* p2kortTva.setOnClickListener {
            chosenCardtoDeal()
        }
        p2kortTre.setOnClickListener {
            chosenCardtoDeal()
        }
        p2kortFyra.setOnClickListener {
            chosenCardtoDeal()
        } */





    }

    private fun numberOfCards() {
        initialCardsP1 = deckOfCardsP1.size
        initialCardsP2 = deckOfCardsP2.size
        cardsLeftP1.text = "Cards Left: ${initialCardsP1}" //showing how many cards left for p1
        cardsLeftP2.text = "Cards Left: ${initialCardsP2}"
    }

    fun getValueFromIndex (index: Int):Int {
        return (index/4)+2 //index genom 4 ta bort decimalen plus 2
    }

    private fun setCardImage(imageResource: Int, imageView: ImageView) {
        imageView.setImageResource(imageResource)
    }

    private fun chosenCardtoDeal(cardToMove: ImageView, destinationCard:ImageView) {
        Log.d("dealButton()", "dealfunction")

            //hide()

            if (deckOfCardsP1.size < 1 || deckOfCardsP2.size < 1) {
                endGame()
            } else {
                if (cardToMove == p2kortEtt){
                    setImageView(shown_card2, p2kortEtt)
                    }
                }
                //generate cards
                val P1card = random.nextInt(deckOfCardsP1.size)
                val P2card = random.nextInt(deckOfCardsP2.size)

                Log.d("b_deal.onClick", "deal.onclick")
                //Set images
                setCardImage(P1card, shown_card1)
                setCardImage(P2card, shown_card2)

                val p1CardValue = getValueFromIndex(P1card)
                val p2CardValue = getValueFromIndex(P2card)
                //compare the cards
                if (p1CardValue > p2CardValue) {
                    player1++
                    deckOfCardsP2.removeAt(P2card)
                    deckOfCardsP1.add(P2card)
                    points_player1.text = "Player 1: $player1"
                    numberOfCards()
                    //checkGameStatus()
                } else if (p1CardValue < p2CardValue) {
                    player2++
                    deckOfCardsP1.removeAt(P1card)
                    deckOfCardsP2.add(P1card)
                    points_player2.text = "Player 2: $player2"
                    numberOfCards()
                    //checkGameStatus()
                } else {

                    //warWar()
                }
            }

    private fun setImageView(shownCard2: ImageView, p2kortEtt: ImageView) {
            return
    }


    private fun endGame() {

    }

    }






