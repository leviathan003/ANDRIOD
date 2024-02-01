package com.example.memory_game

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.memory_game.models.BoardSize
import kotlin.math.min

class ImagePickerRVAdapter(private val context: Context,
                           private val chosenImgUris: List<Uri>,
                           private val boardSize: BoardSize,
                           private val imgClickListener:ImageClickListener ) : RecyclerView.Adapter<ImagePickerRVAdapter.ViewHolder>(){

    interface ImageClickListener{
        fun OnPlaceholderClick()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_image,parent,false)
        val cardWidth = parent.width / boardSize.getWidth()
        val cardHeight = parent.height / boardSize.getHeight()
        val cardDimens = min(cardHeight,cardWidth)
        val layoutParams = view.findViewById<ImageView>(R.id.customImg).layoutParams
        layoutParams.width = cardDimens
        layoutParams.height = cardDimens
        return ViewHolder(view)
    }

    override fun getItemCount() = boardSize.getNumPair()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(chosenImgUris.size>position){
            holder.bind(chosenImgUris[position])
        }
        else{
            holder.bind()
        }
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        private val img = itemView.findViewById<ImageView>(R.id.customImg)
        fun bind(uri: Uri) {
            img.setImageURI(uri)
            img.setOnClickListener(null)
        }
        fun bind(){
            img.setOnClickListener{
                imgClickListener.OnPlaceholderClick()
            }
        }

    }
}
