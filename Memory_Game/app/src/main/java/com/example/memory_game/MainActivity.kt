package com.example.memory_game

import android.animation.ArgbEvaluator
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memory_game.models.BoardSize
import com.example.memory_game.models.MemoryGame
import com.example.memory_game.models.UserImgList
import com.github.jinatonic.confetti.CommonConfetti
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    private lateinit var toolBar:androidx.appcompat.widget.Toolbar
    private lateinit var recyclerView:RecyclerView
    private lateinit var movesDisp:TextView
    private lateinit var pairsFoundDisp:TextView
    private lateinit var memoryGame: MemoryGame
    private lateinit var adapter:MemBoardRVAdapter
    private lateinit var rootLay:CoordinatorLayout
    private val db = Firebase.firestore
    private var gameName: String? = null
    private var boardSize: BoardSize = BoardSize.EASY
    private var customGameImgs: List<String>? = null

    companion object{
        private const val CREATE_REQUEST = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)

        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        recyclerView = findViewById(R.id.cardDispRV)
        movesDisp = findViewById(R.id.movesTV)
        pairsFoundDisp = findViewById(R.id.matchPairTV)
        rootLay = findViewById(R.id.rootLayout)

        setupBoard()

    }

    private fun setupBoard() {

        memoryGame = MemoryGame(boardSize,customGameImgs)

        adapter = MemBoardRVAdapter(this,boardSize,memoryGame.cards,object: MemBoardRVAdapter.cardClickListener{
            override fun onCardClicked(pos: Int) {
                cardFlippedChange(pos)
            }

        })
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this,boardSize.getWidth())

        supportActionBar?.title = when(boardSize){
            BoardSize.EASY-> "Level: Easy (4x2)"
            BoardSize.MEDIUM-> "Level: Medium (6x3)"
            else-> "Level: Hard (6x4)"
        }
        movesDisp.text = "Moves: ${memoryGame.getMovesMade()}"
        pairsFoundDisp.text = "Pairs Found: ${memoryGame.numPair}/${boardSize.getNumPair()}"
        pairsFoundDisp.setTextColor(ContextCompat.getColor(this,R.color.no_progress))
    }

    private fun cardFlippedChange(pos: Int) {
        if(memoryGame.haveWon()){
            Snackbar.make(rootLay,"You have already won!!",Snackbar.LENGTH_SHORT).show()
            return
        }
        if(memoryGame.isCardFaceUp(pos)){
            Snackbar.make(rootLay,"Invalid Move!!",Snackbar.LENGTH_SHORT).show()
            return
        }
        if(memoryGame.flipCard(pos)){
            val color = ArgbEvaluator().evaluate(
                memoryGame.numPair.toFloat()/boardSize.getNumPair(),
                ContextCompat.getColor(this,R.color.no_progress),
                ContextCompat.getColor(this,R.color.full_progress)
            ) as Int
            pairsFoundDisp.setTextColor(color)
            pairsFoundDisp.text = "Pairs Found: ${memoryGame.numPair}/${boardSize.getNumPair()}"
            if(memoryGame.haveWon()){
                CommonConfetti.rainingConfetti(rootLay, intArrayOf(Color.YELLOW,Color.BLUE,Color.GREEN,Color.RED))
                    .oneShot();
                Snackbar.make(rootLay,"Congrats!! You win.",Snackbar.LENGTH_LONG).show()
            }
        }
        movesDisp.text = "Moves: ${memoryGame.getMovesMade()}"
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refreshButton -> {
                if(memoryGame.getMovesMade()>0 && !memoryGame.haveWon()){
                    showAlert("Quit current game?",null,View.OnClickListener {
                        setupBoard()
                        Toast.makeText(this,"Board Refreshed",Toast.LENGTH_SHORT).show()
                    })
                }else{
                    setupBoard()
                    Toast.makeText(this,"Board Refreshed",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.newGameButton->{
                showResizeDialog()
            }
            R.id.customGameBtn->{
                showCreation()
            }
            R.id.playCustomGame->{
                playCustomGame()
            }
        }
        return true
    }

    private fun playCustomGame() {
        val down_gameView = LayoutInflater.from(this).inflate(R.layout.customgame_name_entry,null)
        showAlert("Fetch Game",down_gameView,View.OnClickListener {
            val customGName = down_gameView.findViewById<EditText>(R.id.customGNameEntry)
            val gametoGet = customGName.text.toString()
            downloadGame(gametoGet)
        })
    }

    private fun showCreation() {
        val boardSizeView = LayoutInflater.from(this).inflate(R.layout.choose_board_size,null)
        val radioGrp = boardSizeView.findViewById<RadioGroup>(R.id.levelRG)

        showAlert("Create Custom Memory Board",boardSizeView,View.OnClickListener {
            val chosenBoardSize = when(radioGrp.checkedRadioButtonId){
                R.id.easyLevelRBtn-> BoardSize.EASY
                R.id.mediumLevelRBtn-> BoardSize.MEDIUM
                else-> BoardSize.HARD
            }
            val intent = Intent(this,CreateCustomGameActivity::class.java)
            intent.putExtra("Board_size",chosenBoardSize)
            startActivityForResult(intent,CREATE_REQUEST)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_REQUEST && resultCode==Activity.RESULT_OK){
            val customGameName = data?.getStringExtra("Custom Game Name")
            if(customGameName==null){
                return
            }
            downloadGame(customGameName)
        }
    }

    private fun downloadGame(customGameName: String) {
        db.collection("games").document(customGameName).get().addOnSuccessListener {
            document->
            val userImgList = document.toObject(UserImgList::class.java)
            if(userImgList?.images==null){
                Snackbar.make(rootLay,"Game not found",Snackbar.LENGTH_LONG).show()
                return@addOnSuccessListener
            }
            val numCards = userImgList.images.size * 2
            boardSize = BoardSize.getBySize(numCards)
            customGameImgs = userImgList.images
            for(imgurl in userImgList.images){
                Picasso.get().load(imgurl).fetch()
            }
            Snackbar.make(rootLay,"You are now playing ${customGameName}",Snackbar.LENGTH_SHORT).show()
            setupBoard()
            gameName = customGameName
        }.addOnFailureListener{
            failure->Toast.makeText(this,"Download Failed",Toast.LENGTH_SHORT).show()
        }
    }

    private fun showResizeDialog() {
        val boardSizeView = LayoutInflater.from(this).inflate(R.layout.choose_board_size,null)
        val radioGrp = boardSizeView.findViewById<RadioGroup>(R.id.levelRG)
        when(boardSize){
            BoardSize.EASY-> radioGrp.check(R.id.easyLevelRBtn)
            BoardSize.MEDIUM-> radioGrp.check(R.id.mediumLevelRBtn)
            else-> radioGrp.check(R.id.hardLevelRBtn)
        }
        showAlert("Choose new size",boardSizeView,View.OnClickListener {
            boardSize = when(radioGrp.checkedRadioButtonId){
                R.id.easyLevelRBtn-> BoardSize.EASY
                R.id.mediumLevelRBtn-> BoardSize.MEDIUM
                else-> BoardSize.HARD
            }
            gameName = null
            customGameImgs = null
            setupBoard()
        })
    }

    private fun showAlert(title:String, view: View?, positiveClickListener: View.OnClickListener) {
        AlertDialog.Builder(this).setTitle(title)
            .setView(view).setNegativeButton("Cancel",null)
            .setPositiveButton("Ok"){
                _,_->positiveClickListener.onClick(null)
            }.show()
    }
}