package com.example.google_auth

import android.app.Instrumentation.ActivityResult
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var signinGBtn: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var signinClient: GoogleSignInClient
    private lateinit var nameDisp:TextView
    private lateinit var imgView: ImageView
    private lateinit var signoutBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgView = findViewById(R.id.photo)
        nameDisp = findViewById(R.id.nameDisp)
        signinGBtn = findViewById(R.id.googleSigninBtn)
        signoutBtn = findViewById(R.id.googleSignoutBtn)
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        signinClient = GoogleSignIn.getClient(this,gso)

        signinGBtn.setOnClickListener {
            val signIntent = signinClient.signInIntent
            launcher.launch(signIntent)
        }

        signoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(this,"Signed out!!",Toast.LENGTH_SHORT).show()
            nameDisp.text = ""
            imgView.setImageBitmap(null)
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = Firebase.auth.currentUser
        if(currentUser!=null){
            Toast.makeText(this,"Welcome back ${currentUser.displayName}",Toast.LENGTH_SHORT).show()
            nameDisp.text = "${currentUser.displayName}\n${currentUser.email}"
            var img:Bitmap? = null
            var imgUrl = currentUser.photoUrl.toString()
            val executorService = Executors.newSingleThreadExecutor()
            executorService.execute{
                try {
                    val `in` = java.net.URL(imgUrl).openStream()
                    img = BitmapFactory.decodeStream(`in`)
                }catch (e:Exception){

                }
            }
            runOnUiThread{
                try {
                    Thread.sleep(2000)
                    imgView.setImageBitmap(img)
                }catch (e:Exception){

                }
            }
        }
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result->
        if(result.resultCode == RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            manageResule(task)
        }
    }
    private fun manageResule(task:Task<GoogleSignInAccount>){
        if(task.isSuccessful){
            val account:GoogleSignInAccount? = task.result
            if(account!=null){
                updateUi(account)
            }
        }
    }
    private fun updateUi(acc: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(acc.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(this,"Sign In Success",Toast.LENGTH_SHORT).show()
                verifyUser()
            }
        }
    }
    private fun verifyUser(){
        val user = Firebase.auth.currentUser
        user?.let{
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl
            val isVerified = it.isEmailVerified

            var img:Bitmap? = null
            var imgUrl = photoUrl.toString()
            val executorService = Executors.newSingleThreadExecutor()
            if(isVerified){
                executorService.execute{
                    try {
                        val `in` = java.net.URL(imgUrl).openStream()
                        img = BitmapFactory.decodeStream(`in`)
                    }catch (e:Exception){

                    }
                }
                runOnUiThread{
                    try {
                        Thread.sleep(2000)
                        imgView.setImageBitmap(img)
                    }catch (e:Exception){

                    }
                }
                nameDisp.text = "${name}\n${email}"
                Toast.makeText(this,"Account Verified!!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}