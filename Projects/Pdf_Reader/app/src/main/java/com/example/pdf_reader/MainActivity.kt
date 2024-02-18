package com.example.pdf_reader

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.pdf_reader.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black )

        binding.pdfOpenBtn.setOnClickListener {
            launcher.launch("application/pdf")
        }
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ){uri->
        if(uri!=null){
            val fileName = getFileName(uri)
            binding.openPdfPrompt.visibility = View.INVISIBLE
            binding.pageNumDisp.visibility = View.VISIBLE
            binding.filenameDisp.text = fileName
            binding.filenameDisp.visibility = View.VISIBLE
            binding.pdfView.fromUri(uri).onPageChange { page, pageCount ->
                binding.pageNumDisp.text = "Page No: "+(page+1).toString()+"/"+pageCount.toString()
            }.load()
        }
    }

    @SuppressLint("Range")
    private fun getFileName(uri: Uri): String? {
        val cursor = contentResolver.query(uri, null, null, null, null)
        var fileName: String? = null
        cursor?.use {
            if (it.moveToFirst()) {
                fileName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
        return fileName?.removeSuffix(".pdf")
    }
}