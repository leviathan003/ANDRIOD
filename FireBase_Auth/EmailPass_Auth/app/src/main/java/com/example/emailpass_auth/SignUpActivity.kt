package com.example.emailpass_auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var emailEntry: EditText
    private lateinit var passEntry: EditText
    private lateinit var nameEntry:EditText
    private lateinit var cnfpasswordEntry:EditText
    private lateinit var registerBtn:Button
    private lateinit var signinBtn:Button
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        emailEntry = findViewById(R.id.emailEntry)
        passEntry = findViewById(R.id.passwordEntry)
        nameEntry = findViewById(R.id.nameEntry)
        cnfpasswordEntry = findViewById(R.id.cnfpasswordEntry)
        registerBtn = findViewById(R.id.registerBtn)
        signinBtn = findViewById(R.id.signinBtn)
        auth = FirebaseAuth.getInstance()

        signinBtn.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerBtn.setOnClickListener {

            var email = emailEntry.text.toString()
            var password = passEntry.text.toString()
            var name = nameEntry.text.toString()
            var cnfpassword = cnfpasswordEntry.text.toString()

            //perform error checking of user data here by making separate function
            //just implementing auth here with given condition that user data has been validated
            //var check = dataValidator(email,password,name,cnfpassword):Boolean
            //if (check==true) then the following code

            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                    task->
                    if(task.isSuccessful){
                        val intent = Intent(this,SignInActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this,"Registration Successful!!",Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this,"Registration Failed!!",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}