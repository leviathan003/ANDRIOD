package com.example.mvvm_demo.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_demo.databinding.ActivityMainBinding
import com.example.mvvm_demo.viewmodel.CalculatorViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var calViewModel:CalculatorViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calViewModel = ViewModelProvider(this)[CalculatorViewModel::class.java]

        binding.calBtn.setOnClickListener {
            val num1 = binding.num1.text.toString().toIntOrNull() ?:0
            val num2 = binding.num2.text.toString().toIntOrNull() ?:0
            when{
                binding.operation.text.toString() == "+"->{
                    binding.answerDisp.text = calViewModel.calculateSum(num1,num2).answer.toString()
                }
                binding.operation.text.toString() == "-"->{
                    binding.answerDisp.text = calViewModel.calculateDiff(num1,num2).answer.toString()
                }
                binding.operation.text.toString() == "*"->{
                    binding.answerDisp.text = calViewModel.calculateProd(num1,num2).answer.toString()
                }
                binding.operation.text.toString() == "/"->{
                    binding.answerDisp.text = calViewModel.calculateQuo(num1,num2).answer.toString()
                }
            }
        }
    }
}