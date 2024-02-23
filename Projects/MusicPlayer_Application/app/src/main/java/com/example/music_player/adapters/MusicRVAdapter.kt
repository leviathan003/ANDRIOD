package com.example.music_player.adapters

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.music_player.databinding.MusicItemBinding
import com.example.music_player.datamodels.Data
import com.example.music_player.datamodels.Music
import com.squareup.picasso.Picasso

class MusicRVAdapter(private val context: Context):RecyclerView.Adapter<MusicRVAdapter.MusicViewHolder>() {

    private var musicList: List<Data> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = MusicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val currentItem = musicList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    fun updateData(newList: List<Data>) {
        val diffResult = DiffUtil.calculateDiff(MusicDiffCallback(musicList, newList))
        musicList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class MusicViewHolder(private val binding: MusicItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Data) {
            binding.musicTitle.text = item.title
            Picasso.get().load(item.album.cover).into(binding.albumImg)
            val mediaPlayer = MediaPlayer.create(context,item.preview.toUri())

            binding.playBtn.setOnClickListener {
                mediaPlayer.start()
            }

            binding.pauseBtn.setOnClickListener {
                mediaPlayer.pause()
            }
        }
    }

    class MusicDiffCallback(private val oldList: List<Data>, private val newList: List<Data>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}