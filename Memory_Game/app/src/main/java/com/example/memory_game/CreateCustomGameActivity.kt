package com.example.memory_game

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memory_game.models.BoardSize
import com.example.memory_game.utils.BitmapScaler
import com.example.memory_game.utils.isPermissionGranted
import com.example.memory_game.utils.requestPermission
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

class CreateCustomGameActivity : AppCompatActivity() {

    private lateinit var boardSize:BoardSize
    private lateinit var imgPickerView: RecyclerView
    private lateinit var gameNameEntry:EditText
    private lateinit var saveBtn:Button
    private var numImgsReq = -1
    private val chosenImgUris = mutableListOf<Uri>()
    private lateinit var adapter: ImagePickerRVAdapter
    private lateinit var toolBar:androidx.appcompat.widget.Toolbar
    private val storage = Firebase.storage
    private val db = Firebase.firestore
    private lateinit var uploadPB: ProgressBar

    companion object{
        private const val PICK_REQ_CODE = 300
        private const val READ_PHOTO_REQ_CODE = 400
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        private const val PERMISSION = android.Manifest.permission.READ_MEDIA_IMAGES
        private const val MIN_GAMENAME_LENGTH = 3
        private const val MAX_GAMENAME_LENGTH = 15
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_custom_game)

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)

        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        imgPickerView = findViewById(R.id.imgPickerRV)
        gameNameEntry = findViewById(R.id.gameNameEntry)
        uploadPB = findViewById(R.id.uploadProgressBar)
        saveBtn = findViewById(R.id.saveBtn)

        gameNameEntry.filters = arrayOf(InputFilter.LengthFilter(MAX_GAMENAME_LENGTH))
        gameNameEntry.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                saveBtn.isEnabled = enableSaveButton()
            }
        })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        boardSize = intent.getSerializableExtra("Board_size") as BoardSize
        numImgsReq = boardSize.getNumPair()
        supportActionBar?.title = "Pictures Chosen: 0/${numImgsReq}"

        adapter = ImagePickerRVAdapter(this,chosenImgUris,boardSize,object: ImagePickerRVAdapter.ImageClickListener{
            override fun OnPlaceholderClick() {
                if(isPermissionGranted(this@CreateCustomGameActivity, PERMISSION)){
                    launchIntentForPhotos()
                }
                else{
                    requestPermission(this@CreateCustomGameActivity, PERMISSION, READ_PHOTO_REQ_CODE)
                }
            }
        })
        imgPickerView.adapter = adapter
        imgPickerView.setHasFixedSize(true)
        imgPickerView.layoutManager = GridLayoutManager(this,boardSize.getWidth())

        saveBtn.setOnClickListener{
            saveDatatoFirebase()
        }

    }

    private fun saveDatatoFirebase() {
        saveBtn.isEnabled = false
        db.collection("games").document(gameNameEntry.text.toString()).get().addOnSuccessListener {
            doc->
            if(doc!=null && doc.data!=null){
                AlertDialog.Builder(this).setTitle("Game Name Already Taken")
                    .setMessage("Game name: ${gameNameEntry.text.toString()} already in use. Please choose a different name.")
                    .setPositiveButton("OK",null).show()
                saveBtn.isEnabled = true
            }
            else{
                uploadPB.visibility = View.VISIBLE
                var encounteredError = false
                val uploadImgUrls = mutableListOf<String>()
                for((idx,imgURI) in chosenImgUris.withIndex()){
                    val imageByteArray = getImageByteArray(imgURI)
                    val path = "images/${gameNameEntry.text.toString()}/${System.currentTimeMillis()}-${idx}.jpeg"
                    val reference = storage.reference.child(path)
                    reference.putBytes(imageByteArray)
                        .continueWithTask {
                                photoUpldTask->reference.downloadUrl
                        }.addOnCompleteListener{
                                downloadURLTask->
                            if(!downloadURLTask.isSuccessful){
                                Toast.makeText(this,"Failed to upload image",Toast.LENGTH_LONG).show()
                                encounteredError = true
                                return@addOnCompleteListener
                            }
                            if(encounteredError){
                                uploadPB.visibility = View.GONE
                                return@addOnCompleteListener
                            }
                            val downloadUrl = downloadURLTask.result.toString()
                            uploadImgUrls.add(downloadUrl)
                            uploadPB.progress = uploadImgUrls.size*100 / chosenImgUris.size
                            if(uploadImgUrls.size == chosenImgUris.size){
                                saveToFirestore(gameNameEntry.text.toString(),uploadImgUrls)
                            }
                        }
                }
            }
        }.addOnFailureListener{
            failure->
            Toast.makeText(this,"Encountered error while saving game information",Toast.LENGTH_SHORT).show()
            saveBtn.isEnabled = true
        }
    }

    private fun saveToFirestore(gameName: String, imgUrls: MutableList<String>) {
        db.collection(
            "games"
        ).document(gameName).set(mapOf("images" to imgUrls)).addOnCompleteListener{
            gameCreateTask->
            uploadPB.visibility = View.GONE
            if(!gameCreateTask.isSuccessful){
                Toast.makeText(this,"Failed Game Creation",Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            }
            AlertDialog.Builder(this).setTitle("Upload Complete. Click OK to play custom game")
                .setPositiveButton("OK"){
                    _,_->
                    val resultData = Intent()
                    resultData.putExtra("Custom Game Name",gameName)
                    setResult(Activity.RESULT_OK,resultData)
                    finish()
                }.show()
        }
    }

    private fun getImageByteArray(imgURI: Uri): ByteArray {
        val origBitMap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            val source = ImageDecoder.createSource(contentResolver,imgURI)
            ImageDecoder.decodeBitmap(source)
        }else{
            MediaStore.Images.Media.getBitmap(contentResolver,imgURI)
        }
        val scaledBitmap = BitmapScaler.scaletoFitHeight(origBitMap,250)
        val byteArrayOP = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG,60,byteArrayOP)
        return byteArrayOP.toByteArray()
    }

    private fun launchIntentForPhotos() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        startActivityForResult(Intent.createChooser(intent,"Choose photos"),PICK_REQ_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == READ_PHOTO_REQ_CODE){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                launchIntentForPhotos()
            }else{
                Toast.makeText(this,"Please allow access to photos to create custom game",Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode != PICK_REQ_CODE || resultCode!= Activity.RESULT_OK || data==null){
            return
        }
        val selectedUri = data.data
        val clipData = data.clipData
        if(clipData!=null){
            for(i in 0 until clipData.itemCount){
                val clipItem = clipData.getItemAt(i)
                if(chosenImgUris.size < numImgsReq){
                    chosenImgUris.add(clipItem.uri)
                }
            }
        }else if(selectedUri!=null){
            chosenImgUris.add(selectedUri)
        }
        adapter.notifyDataSetChanged()
        supportActionBar?.title = "Pictures Chosen: ${chosenImgUris.size}/$numImgsReq"
        saveBtn.isEnabled = enableSaveButton()
    }

    private fun enableSaveButton(): Boolean {
        if(chosenImgUris.size != numImgsReq){
            return false
        }
        if(gameNameEntry.text.isBlank() || gameNameEntry.text.length<MIN_GAMENAME_LENGTH){
            return false
        }
        return true
    }

}