package com.example.chattingapplication

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.chattingapplication.databinding.ActivityRegisterBinding
import com.example.chattingapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var db:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.textPrimary )

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().reference

        binding.registerBtn.setOnClickListener {
            val email = binding.registerEmail.text.toString()
            val password = binding.registerPass.text.toString()
            val username = binding.registerName.text.toString()
            val repassword = binding.registerRePass.text.toString()

            if(errorCheckField(username,email,password,repassword)){
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                    if (task.isSuccessful && addUserAcc(username)) {
                        Toast.makeText(this, "Registration Successful!!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Registration Unsuccessful!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.goToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun addUserAcc(username:String):Boolean {
        val user = auth.currentUser
        var result = false
        if(user!=null){
            val data = User(user.uid,username,false)
            db.child("users").child(user.uid).setValue(data)
            result = true
        }
        return result
    }

    private fun errorCheckField(username:String,email:String,password:String,repassword:String):Boolean {
        if(username.isBlank() || email.isBlank() || password.isBlank() || repassword.isBlank()){
            Toast.makeText(this,"All Fields are mandatory!!",Toast.LENGTH_SHORT).show()
            return false
        }
        else if(password.length<8){
            Toast.makeText(this,"Password should at least be 8 characters!!",Toast.LENGTH_SHORT).show()
            return false
        }
        else if(password != repassword){
            Toast.makeText(this,"Passwords do not match!!",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}