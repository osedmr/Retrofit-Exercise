package com.example.retrofitexercise.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.RoundedCornersTransformation
import com.example.retrofitexercise.R
import com.example.retrofitexercise.data.entity.CoilImages
import com.example.retrofitexercise.databinding.ActivityCoilBinding
import com.example.retrofitexercise.databinding.CoilItemBinding

class CoilAdapter : RecyclerView.Adapter<CoilAdapter.CoilViewHolder>() {

    inner class CoilViewHolder(val binding: CoilItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<CoilImages>() {
        override fun areItemsTheSame(oldItem: CoilImages, newItem: CoilImages): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CoilImages, newItem: CoilImages): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer<CoilImages>(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoilViewHolder {
        val binding = CoilItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoilViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CoilViewHolder, position: Int) {
        val image = differ.currentList[position]
        holder.binding.apply {
            imageView.load(image.download_url){
                placeholder(R.drawable.placeholder)

                crossfade(true)
                crossfade(500)
                transformations(RoundedCornersTransformation(40f)) //radius veriyor

            }
        }

    }
}