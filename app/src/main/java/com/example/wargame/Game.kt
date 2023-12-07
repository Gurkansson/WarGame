package com.example.wargame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class Game : AppCompatActivity() {

    lateinit var shown_card1: ImageView //to show the cards
    lateinit var shown_card2: ImageView //to show the cards

    lateinit var points_player1: TextView //points
    lateinit var points_player2: TextView //points

    lateinit var P1_hand: ImageView

    lateinit var cardsLeftP1: TextView //cards at hand
    lateinit var cardsLeftP2: TextView

    lateinit var tv_war: TextView //show war

    lateinit var cardSoldier1: ImageView
    lateinit var cardSoldier2: ImageView
    lateinit var cardSoldier3: ImageView
    lateinit var cardSoldier4: ImageView
    lateinit var cardSoldier5: ImageView
    lateinit var cardSoldier6: ImageView

    lateinit var warCardp1: ImageView
    lateinit var warCardp2: ImageView

    lateinit var winner: ImageView
    lateinit var loser_view: ImageView

    lateinit var b_deal: ImageButton // deal button
    lateinit var againButton: ImageButton //Play again button

    lateinit var VS_button: ImageButton //VS button (war button)
    lateinit var blixt_uppe: ImageView
    lateinit var blixt_nere: ImageView

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

    var warInProgress = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        Log.d("???","oncreate")

        random = Random()

        //init objects
        shown_card1 = findViewById(R.id.shown_card1)
        shown_card2 = findViewById(R.id.shown_card2)

        shown_card1.setImageResource(R.drawable.red_cardimage)
        shown_card2.setImageResource(R.drawable.red_cardimage)

        points_player1 = findViewById(R.id.points_player1)
        points_player2 = findViewById(R.id.points_player2)

        winner = findViewById(R.id.winner)
        loser_view = findViewById(R.id.loser_view)
        againButton = findViewById(R.id.againButton)
        tv_war = findViewById(R.id.tv_war)

        VS_button = findViewById(R.id.VS_button)
        blixt_nere = findViewById(R.id.blixt_nere)
        blixt_uppe = findViewById(R.id.blixt_uppe)

        cardSoldier1 = findViewById(R.id.cardSoldier1)
        cardSoldier2 = findViewById(R.id.cardSoldier2)
        cardSoldier3 = findViewById(R.id.cardSoldier3)
        cardSoldier4 = findViewById(R.id.cardSoldier4)
        cardSoldier5 = findViewById(R.id.cardSoldier5)
        cardSoldier6 = findViewById(R.id.cardSoldier6)

        warCardp1 = findViewById(R.id.warCardp1)
        warCardp2 = findViewById(R.id.warCardp2)

        warCardp1.setImageResource(R.drawable.red_cardimage)
        warCardp2.setImageResource(R.drawable.red_cardimage)

        P1_hand = findViewById(R.id.P1_hand)

        cardsLeftP1 = findViewById(R.id.cardsLeftP1) // reference to where on the screen to show the info
        cardsLeftP2 = findViewById(R.id.cardsLeftP2)
        // Deal card button
        b_deal = findViewById(R.id.b_deal)

        againButton.setOnClickListener {
            playagain()
        }

        // 2 Arraylist that counts the cards on each players hand. //You also have a count that, so the player can see.
        val midpoint = cardsArray.size / 2
        deckOfCardsP1 = cardsArray.toMutableList().subList(0, midpoint)
        deckOfCardsP2 = cardsArray.toMutableList().subList(midpoint, cardsArray.size)


        VS_button.setOnClickListener {
            VS()
        }

        numberOfCards()
        hide()
        dealButton()

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

    private fun setCardImage(number: Int, image: ImageView) {

       val index = if (number in 0 until cardsArray.size) {
            number
        } else { 0
        //Using first card as a standard card,if the index is out of bounce.
        }
        image.setImageResource(cardsArray[index])
    }

    private fun dealButton() {
    Log.d("dealButton()", "dealfunction")

         b_deal.setOnClickListener {
             warCardp1.visibility = View.INVISIBLE
             warCardp2.visibility = View.INVISIBLE
             hide()

             if (deckOfCardsP1.size < 1 || deckOfCardsP2.size < 1) {
                 endGame()
             } else {
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
                    checkGameStatus()
                } else if (p1CardValue < p2CardValue) {
                    player2++
                    deckOfCardsP1.removeAt(P1card)
                    deckOfCardsP2.add(P1card)
                    points_player2.text = "Player 2: $player2"
                    numberOfCards()
                    checkGameStatus()
                } else {
                     //warInProgress = true
                    VS_button.isEnabled = true
                    b_deal.isEnabled = false
                    warWar()
                }
            }
        }
    }

    private fun warWar() {
        Log.d("???", "war")

        show()

        if(!warInProgress) {
            if (deckOfCardsP1.size < 3 || deckOfCardsP2.size < 3) {
                endGame()
            } else {
                VS()
            }
        }
        dealButton()
    }


    private fun VS() {
        Log.d("???", "VS_button")
        show()

        VS_button.setOnClickListener {
            if (deckOfCardsP1.size < 4 || deckOfCardsP2.size < 4) {
                endGame()
            } else {
                val warCardsP1 = mutableListOf<Int>()
                val warCardsP2 = mutableListOf<Int>()

                warCardp1.setImageResource(R.drawable.red_cardimage)
                warCardp2.setImageResource(R.drawable.red_cardimage)

                // Select random cards for war
                val warCardP1Index = random.nextInt(deckOfCardsP1.size)
                val warCardP2Index = random.nextInt(deckOfCardsP2.size)

                val warCardP1 = deckOfCardsP1.removeAt(warCardP1Index)
                val warCardP2 = deckOfCardsP2.removeAt(warCardP2Index)

                warCardsP1.add(warCardP1)
                warCardsP2.add(warCardP2)

                setCardImage(warCardP1Index, warCardp1)
                setCardImage(warCardP2Index, warCardp2)

                // Select three additional cards for each player
                for (i in 0 until 3) {
                    if (deckOfCardsP1.isNotEmpty()) {
                        val cardP1 = deckOfCardsP1.removeAt(0)
                        warCardsP1.add(cardP1)
                    }
                    if (deckOfCardsP2.isNotEmpty()) {
                        val cardP2 = deckOfCardsP2.removeAt(0)
                        warCardsP2.add(cardP2)
                    }
                }

                val w1cardValue = getValueFromIndex(warCardsP1.last())
                val w2cardValue = getValueFromIndex(warCardsP2.last())

                //different results depending on the showing cards
                if (w1cardValue > w2cardValue) {
                    player1 += 4
                    deckOfCardsP1.addAll(warCardsP1)
                    deckOfCardsP1.addAll(warCardsP2)
                    points_player1.text = "Points: $player1"
                    numberOfCards()
                    checkGameStatus()
                    VS_button.isEnabled = false
                    b_deal.isEnabled = true
                    dealButton()
                } else if (w1cardValue < w2cardValue) {
                    player2 += 4
                    deckOfCardsP2.addAll(warCardsP1)
                    deckOfCardsP2.addAll(warCardsP2)
                    points_player2.text = "Points: $player2"
                    numberOfCards()
                    checkGameStatus()
                    VS_button.isEnabled = false
                    b_deal.isEnabled = true
                    dealButton()
                } else {
                    warInProgress = true
                    warWar()
                }
            }
        }
    }

    private fun checkGameStatus() {
        if (deckOfCardsP1.size < 4 || deckOfCardsP2.size < 4) {
            endGame()
        } else {
            warInProgress = false
            dealButton() // Start a new round if the game continues
        }
    }

    private fun endGame() {
        hide()
        b_deal.setOnClickListener{}
        againButton.visibility =View.VISIBLE

        Log.d("endGame", "!!!")
        if (initialCardsP1 < initialCardsP2) {

            P1_hand.visibility = View.INVISIBLE
            shown_card1.visibility = View.INVISIBLE
            winner.visibility = View.VISIBLE


        } else if (initialCardsP1 > initialCardsP2) {

            b_deal.visibility = View.INVISIBLE
            shown_card2.visibility = View.INVISIBLE
            loser_view.visibility = View.VISIBLE
        }
    }

        private fun hide() {

            //hide war label, VS_button, blixt
            tv_war.visibility = View.INVISIBLE
            VS_button.visibility = View.INVISIBLE
            blixt_uppe.visibility = View.INVISIBLE
            blixt_nere.visibility = View.INVISIBLE

            //hide extra cards
            cardSoldier1.visibility = View.INVISIBLE
            cardSoldier2.visibility = View.INVISIBLE
            cardSoldier3.visibility = View.INVISIBLE
            cardSoldier4.visibility = View.INVISIBLE
            cardSoldier5.visibility = View.INVISIBLE
            cardSoldier6.visibility = View.INVISIBLE

            warCardp1.visibility = View.INVISIBLE
            warCardp2.visibility = View.INVISIBLE

            winner.visibility = View.INVISIBLE
            loser_view.visibility = View.INVISIBLE
            againButton.visibility =View.INVISIBLE
        }

        private fun show() {
            b_deal.setOnClickListener {}
            //war label
            tv_war.visibility = View.VISIBLE

            //lightning + VS button
            VS_button.visibility = View.VISIBLE

            warCardp1.visibility = View.VISIBLE
            warCardp2.visibility = View.VISIBLE
            warCardp1.setImageResource(R.drawable.red_cardimage)
            warCardp2.setImageResource(R.drawable.red_cardimage)

            blixt_uppe.visibility = View.VISIBLE
            blixt_nere.visibility = View.VISIBLE

            // extra cards "soldiers"
            cardSoldier1 = findViewById(R.id.cardSoldier1)
            cardSoldier1.visibility = View.VISIBLE
            cardSoldier2 = findViewById(R.id.cardSoldier2)
            cardSoldier2.visibility = View.VISIBLE
            cardSoldier3 = findViewById(R.id.cardSoldier3)
            cardSoldier3.visibility = View.VISIBLE
            cardSoldier4 = findViewById(R.id.cardSoldier4)
            cardSoldier4.visibility = View.VISIBLE
            cardSoldier5 = findViewById(R.id.cardSoldier5)
            cardSoldier5.visibility = View.VISIBLE
            cardSoldier6 = findViewById(R.id.cardSoldier6)
            cardSoldier6.visibility = View.VISIBLE
        }
    fun playagain() {
        val intent = Intent(this, Game::class.java)
        startActivity(intent)
    }


    }
