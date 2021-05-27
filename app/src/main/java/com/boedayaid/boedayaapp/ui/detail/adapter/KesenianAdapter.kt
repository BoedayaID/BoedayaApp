package com.boedayaid.boedayaapp.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boedayaid.boedayaapp.data.model.DetailSuku
import com.boedayaid.boedayaapp.databinding.ItemCiriKhasDaerahBinding
import com.bumptech.glide.Glide

class KesenianAdapter : RecyclerView.Adapter<KesenianAdapter.ViewHolder>() {
    private val kesenian = mutableListOf<DetailSuku.Kesenian>()
    private lateinit var onClick: (Int) -> Unit

    fun setList(list: List<DetailSuku.Kesenian>) {
        kesenian.clear()
        kesenian.addAll(list)
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
        holder.bind(kesenian[position])
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int = kesenian.size

    class ViewHolder(private val binding: ItemCiriKhasDaerahBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(seni: DetailSuku.Kesenian) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(seni.gambar)
                    .into(imgPoster)
                tvTitle.text = seni.namaKesenian
            }
        }
    }
}