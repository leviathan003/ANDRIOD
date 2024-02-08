package com.example.memory_game.utils

import android.graphics.Bitmap

object BitmapScaler {


    fun scaletoFitWidth(b:Bitmap,width:Int):Bitmap{
        val factor = width / b.width.toFloat()
        return Bitmap.createScaledBitmap(b,width,(b.height*factor).toInt(),true)
    }

    fun scaletoFitHeight(b:Bitmap,height:Int):Bitmap{
        val factor = height / b.height.toFloat()
        return Bitmap.createScaledBitmap(b,(b.width*factor).toInt(),height,true)
    }
}