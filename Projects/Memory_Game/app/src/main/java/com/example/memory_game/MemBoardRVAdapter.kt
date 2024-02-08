package com.example.memory_game

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.memory_game.models.BoardSize
import com.example.memory_game.models.MemoryCard
import com.squareup.picasso.Picasso
import kotlin.math.min

class MemBoardRVAdapter(
    private val context: Context,
    private val peices: BoardSize,
    private val cards: List<MemoryCard>,
    private val cardOnClickListener: cardClickListener
) : RecyclerView.Adapter<MemBoardRVAdapter.ViewHolder>() {

    companion object{
        private var MARGIN_SIZE=10
    }

    interface cardClickListener{
        fun onCardClicked(pos:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardWidth = parent.width/peices.getWidth() - (2 * MARGIN_SIZE)
        val cardHeight = parent.height/peices.getHeight() - (2 * MARGIN_SIZE)
        val cardDimen = min(cardHeight,cardWidth)
        val view = LayoutInflater.from(context).inflate(R.layout.layout_memory_card,parent,false)
        val layoutParams = view.findViewById<CardView>(R.id.card).layoutParams as MarginLayoutParams
        layoutParams.width = cardDimen
        layoutParams.height = cardDimen
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return peices.numCards
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imageBtn:ImageButton = itemView.findViewById<ImageButton>(R.id.imageButton)
        fun bind(position: Int) {
            val memoryCard = cards[position]
            if(memoryCard.isFaceUp){
                if(memoryCard.imageUrl!=null){
                    Picasso.get().load(memoryCard.imageUrl).placeholder(R.drawable.ic_placeholder).into(imageBtn)
                }else{
                    imageBtn.setImageResource(memoryCard.identifier)
                }
            }else{
                imageBtn.setImageResource(R.drawable.card_bg)
            }
            imageBtn.alpha = if(memoryCard.isMatched) .6f else 1.0f
            val colorStateList = if(memoryCard.isMatched) ContextCompat.getColorStateList(context,R.color.lightgray) else null
            ViewCompat.setBackgroundTintList(imageBtn,colorStateList)

            imageBtn.setOnClickListener {
                cardOnClickListener.onCardClicked(position)
            }
        }
    }
}
