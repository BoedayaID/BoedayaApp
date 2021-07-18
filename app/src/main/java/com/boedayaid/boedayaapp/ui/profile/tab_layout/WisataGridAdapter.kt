package com.boedayaid.boedayaapp.ui.profile.tab_layout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boedayaid.boedayaapp.data.model.DetailSuku
import com.boedayaid.boedayaapp.databinding.ItemCiriKhasDaerahBinding
import com.boedayaid.boedayaapp.databinding.ItemGridTempatWisataBinding
import com.bumptech.glide.Glide

class WisataGridAdapter : RecyclerView.Adapter<WisataGridAdapter.ViewHolder>() {

    private val tempatWisata = mutableListOf<DetailSuku.TempatWisata>()
    private lateinit var onClick: (DetailSuku.TempatWisata) -> Unit

    fun setList(list: List<DetailSuku.TempatWisata>) {
        tempatWisata.clear()
        tempatWisata.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnClick(click: (DetailSuku.TempatWisata) -> Unit) {
        onClick = click
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGridTempatWisataBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tempatWisata[position])
        holder.itemView.setOnClickListener {
            onClick(tempatWisata[position])
        }
    }

    override fun getItemCount(): Int = tempatWisata.size

    class ViewHolder(private val binding: ItemGridTempatWisataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(wisata: DetailSuku.TempatWisata) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(wisata.gambar)
                    .into(imgWisata)
                tvWisata.text = wisata.namaTempat
            }
        }
    }
}