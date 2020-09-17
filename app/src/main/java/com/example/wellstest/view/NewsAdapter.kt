package com.example.wellstest.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wellstest.R
import com.example.wellstest.databinding.ItemNewsBinding
import com.example.wellstest.model.Articles

class NewsAdapter(private val listener: (Articles) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val articles = arrayListOf<Articles>()

    inner class ViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Articles) {
            binding.article = article
            binding.executePendingBindings()
            Glide.with(binding.imageNews.context)
                .load(article.urlToImage)
                .into(binding.imageNews)
            binding.root.setOnClickListener {
                listener.invoke(articles[layoutPosition])
            }
        }
    }

    fun setData(articles: List<Articles>) {
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_news,
            parent, false
        )
    )

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles[position])
    }
}