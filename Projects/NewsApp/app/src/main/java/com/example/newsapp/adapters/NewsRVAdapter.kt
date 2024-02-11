package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.models.Article
import com.example.newsprojectpractice.R

class NewsRVAdapter :RecyclerView.Adapter<NewsRVAdapter.ViewHolder>(){

    private lateinit var articleImg:ImageView
    private lateinit var articleSource:TextView
    private lateinit var articleTitle:TextView
    private lateinit var articleDescription:TextView
    private lateinit var articleDateTime:TextView

    private var onItemClickListener: ((Article)->Unit)?=null

    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differ.currentList[position]

        articleImg = holder.itemView.findViewById(R.id.articleImage)
        articleSource = holder.itemView.findViewById(R.id.articleSource)
        articleTitle = holder.itemView.findViewById(R.id.articleTitle)
        articleDescription = holder.itemView.findViewById(R.id.articleDescription)
        articleDateTime = holder.itemView.findViewById(R.id.articleDateTime)

        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(articleImg)
            articleSource.text = article.source?.name
            articleTitle.text = article.title
            articleDescription.text = article.description
            articleDateTime.text = article.publishedAt

            setOnClickListener {
                onItemClickListener?.let{
                    it(article)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener:(Article)->Unit){
        onItemClickListener = listener
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
}