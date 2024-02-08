package com.example.emailpass_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var emailEntry:EditText
    private lateinit var passEntry:EditText
    private lateinit var signInBtn:Button
    private lateinit var registerBtn:Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        emailEntry = findViewById(R.id.emailEntry)
        passEntry = findViewById(R.id.passwordEntry)
        signInBtn = findViewById(R.id.signinBtn)
        registerBtn = findViewById(R.id.registerBtn)
        auth = FirebaseAuth.getInstance()

        registerBtn.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        signInBtn.setOnClickListener {
            var email = emailEntry.text.toString()
            var password = passEntry.text.toString()

            //perform error checking of user data here by making separate function
            //just implementing auth here with given condition that user data has been validated
            //var check = dataValidator(email,password):Boolean
            //if (check==true) then the following code

            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                result->
                val intent = Intent(this,HomeActivity::class.java)
                Toast.makeText(this,"Sign in success!!",Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
            auth.signInWithEmailAndPassword(email,password).addOnFailureListener{
                Toast.makeText(this,"Sign in failure!!",Toast.LENGTH_SHORT).show()
            }
        }

    }
}