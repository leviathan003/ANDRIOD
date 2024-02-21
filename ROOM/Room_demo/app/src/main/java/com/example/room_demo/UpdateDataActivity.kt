package com.example.room_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.room_demo.database.RoomDB
import com.example.room_demo.database.UsersEntity
import com.example.room_demo.databinding.ActivityUpdateDataBinding
import com.example.room_demo.repository.RoomRepo
import com.example.room_demo.viewmodel.MAViewModelFactory
import com.example.room_demo.viewmodel.MainActivityViewModel

class UpdateDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateDataBinding
    private lateinit var viewModel:MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getIntExtra("user_id", -1)
        val userRepo = RoomRepo(RoomDB.invoke(this))
        val viewModelProviderFactory = MAViewModelFactory(application,userRepo)
        viewModel = ViewModelProvider(this,viewModelProviderFactory)[MainActivityViewModel::class.java]

        binding.updtBtn.setOnClickListener {
            val updatedName = binding.nameUpdtEntry.text.toString().trim()
            val updatedEmail = binding.emialUpdtEntry.text.toString().trim()

            if(updatedName.isNotEmpty() && updatedEmail.isNotEmpty()){
                val user = UsersEntity(id = userId, name = updatedName, email = updatedEmail)
                viewModel.updateUsers(user)
                finish()
            }
        }

    }
}