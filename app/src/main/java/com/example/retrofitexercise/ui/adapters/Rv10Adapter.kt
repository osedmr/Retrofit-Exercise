package com.example.retrofitexercise.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexercise.data.entity.Post
import com.example.retrofitexercise.databinding.RvItemBinding

class Rv10Adapter : RecyclerView.Adapter<Rv10Adapter.PostViewHolder>()  {

    inner class PostViewHolder(val binding : RvItemBinding) :RecyclerView.ViewHolder(binding.root)

    private val differCallBAck = object : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }


    val differ = AsyncListDiffer<Post>(this,differCallBAck)


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostViewHolder(binding)
    }



    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = differ.currentList[position]
        holder.binding.apply {
            userId.text = post.userId.toString()
            id.text = post.id.toString()
            title.text = post.title
            body.text = post.body

        }
    }
}