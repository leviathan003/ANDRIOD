package com.example.mvvm_demo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvvm_demo.model.CalculatorData

class CalculatorViewModel:ViewModel() {
    fun calculateSum(num1:Int,num2:Int):CalculatorData{
        val sum = num1+num2
        return CalculatorData(num1,num2,sum.toDouble())
    }
    fun calculateDiff(num1:Int,num2:Int):CalculatorData{
        val sum = num1-num2
        return CalculatorData(num1,num2,sum.toDouble())
    }
    fun calculateProd(num1:Int,num2:Int):CalculatorData{
        val sum = num1*num2
        return CalculatorData(num1,num2,sum.toDouble())
    }
    fun calculateQuo(num1:Int,num2:Int):CalculatorData{
        val sum = num1/num2
        return CalculatorData(num1,num2,sum.toDouble())
    }
}