package com.example.paging3_newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.paging3_newsapp.databinding.NewsLayoutBinding
import com.example.paging3_newsapp.model.Article

class NewsPagingAdapter : PagingDataAdapter<Article, NewsPagingAdapter.MyViewHolder>(diffCallBack) {

    inner class MyViewHolder( val binding: NewsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.binding.apply {
            textView.text = currentItem?.title

            val imageLink = currentItem?.urlToImage

            imageView.load(imageLink){
                crossfade(true)
                crossfade(1000)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            NewsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}