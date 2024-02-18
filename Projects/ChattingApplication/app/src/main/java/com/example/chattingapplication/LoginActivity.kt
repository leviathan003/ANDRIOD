package com.example.chattingapplication.login


import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.chattingapplication.HomeActivity
import com.example.chattingapplication.R
import com.example.chattingapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.textPrimary )

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().reference

        if(auth.currentUser!=null){
            val userRef = db.child("users").child(auth.currentUser!!.uid)
            userRef.child("online").setValue(true)
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPass.text.toString()

            if(email.isBlank() || password.isBlank()){
                Toast.makeText(this,"Both Fields are Mandatory!!", Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    task->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Login Successful!!", Toast.LENGTH_SHORT).show()
                        val userRef = db.child("users").child(auth.currentUser!!.uid)
                        userRef.child("online").setValue(true)
                        startActivity(Intent(this,HomeActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this,"User does not exist!!", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

        binding.goToRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser!=null){
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }

}