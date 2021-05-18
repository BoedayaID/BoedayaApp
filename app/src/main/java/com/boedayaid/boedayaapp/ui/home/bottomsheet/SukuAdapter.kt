package com.boedayaid.boedayaapp.ui.home.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boedayaid.boedayaapp.data.model.Suku
import com.boedayaid.boedayaapp.databinding.ItemGridSukuBinding
import com.bumptech.glide.Glide


class SukuAdapter : RecyclerView.Adapter<SukuAdapter.ViewHolder>() {
    private val listSuku = mutableListOf<Suku>()
    private lateinit var onClick: (Int) -> Unit

    fun setList(list: List<Suku>) {
        listSuku.clear()
        listSuku.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnClick(click: (Int) -> Unit) {
        onClick = click
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGridSukuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listSuku[position])
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int = listSuku.size

    class ViewHolder(private val binding: ItemGridSukuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(suku: Suku) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(suku.image)
                    .into(imgSuku)
                tvSuku.text = suku.name
            }
        }
    }
}