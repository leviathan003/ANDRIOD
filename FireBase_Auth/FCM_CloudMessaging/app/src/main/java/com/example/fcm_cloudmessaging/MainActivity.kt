package com.example.fcm_cloudmessaging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {

    private lateinit var scoreDisp:TextView
    private var data:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreDisp = findViewById(R.id.scoreDisp)

        FirebaseMessaging.getInstance().token.addOnCompleteListener{
            if(it.isSuccessful){
                Log.d("FCMToken",it.result.toString())
            }
        }

        if(intent!= null){
            if(intent.hasExtra("key1")){
                for(key in intent.extras!!.keySet()){
                    if(key in arrayOf("target","score","overs")){
                        data += intent.extras!!.getString(key)+"\n"
                    }
                }
                scoreDisp.text = data
            }
        }
    }
}