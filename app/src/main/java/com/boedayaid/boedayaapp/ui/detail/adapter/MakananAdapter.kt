package com.boedayaid.boedayaapp.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boedayaid.boedayaapp.data.model.DetailSuku
import com.boedayaid.boedayaapp.databinding.ItemCiriKhasDaerahBinding
import com.bumptech.glide.Glide

class MakananAdapter : RecyclerView.Adapter<MakananAdapter.ViewHolder>() {
    private val makanan = mutableListOf<DetailSuku.Makanan>()
    private lateinit var onClick: (Int) -> Unit

    fun setList(list: List<DetailSuku.Makanan>) {
        makanan.clear()
        makanan.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnClick(click: (Int) -> Unit) {
        onClick = click
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCiriKhasDaerahBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(makanan[position])
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int = makanan.size

    class ViewHolder(private val binding: ItemCiriKhasDaerahBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(makan: DetailSuku.Makanan) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(makan.gambar)
                    .into(imgPoster)
                tvTitle.text = makan.namaMakanan
            }
        }
    }
}