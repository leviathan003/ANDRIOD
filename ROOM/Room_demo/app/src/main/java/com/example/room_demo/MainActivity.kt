package com.example.room_demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room_demo.adapters.MainDispRVAdapter
import com.example.room_demo.database.RoomDB
import com.example.room_demo.database.UsersEntity
import com.example.room_demo.databinding.ActivityMainBinding
import com.example.room_demo.repository.RoomRepo
import com.example.room_demo.viewmodel.MAViewModelFactory
import com.example.room_demo.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainDispRVAdapter
    private lateinit var viewModel:MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userRepo = RoomRepo(RoomDB.invoke(this))
        val viewModelProviderFactory = MAViewModelFactory(application,userRepo)
        viewModel = ViewModelProvider(this,viewModelProviderFactory)[MainActivityViewModel::class.java]

        setupRecyclerView()
        observeData()

        binding.saveBtn.setOnClickListener {
            val name = binding.nameEntry.text.toString().trim()
            val email = binding.emialEntry.text.toString().trim()
            if (name.isNotEmpty() && email.isNotEmpty()) {
                val user = UsersEntity(name = name, email = email)
                viewModel.addUser(user)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = MainDispRVAdapter (
            onDeleteClickListener = {
                user->
                viewModel.deleteUser(user)
            },
            onItemClick = {
                user->
                openUpdateActivity(user)
            }
        )
        binding.dispRV.layoutManager = LinearLayoutManager(this)
        binding.dispRV.adapter = adapter
    }

    private fun openUpdateActivity(user: UsersEntity) {
        val intent = Intent(this,UpdateDataActivity::class.java)
        intent.putExtra("user_id",user.id)
        startActivity(intent)
    }

    private fun observeData() {
        viewModel.getUsers().observe(this, Observer {
                users->
            println(users)
            adapter.submitList(users)
        })
    }

}
