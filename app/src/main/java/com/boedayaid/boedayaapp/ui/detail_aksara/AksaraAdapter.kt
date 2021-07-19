package com.boedayaid.boedayaapp.ui.detail_aksara

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boedayaid.boedayaapp.data.model.Aksara
import com.boedayaid.boedayaapp.databinding.ItemAksaraBinding
import com.bumptech.glide.Glide


class AksaraAdapter : RecyclerView.Adapter<AksaraAdapter.ViewHolder>() {
    private val listAksara = mutableListOf<Aksara>()
    private lateinit var onClick: (Aksara) -> Unit

    fun setList(list: List<Aksara>) {
        listAksara.clear()
        listAksara.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnClick(click: (Aksara) -> Unit) {
        onClick = click
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAksaraBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listAksara[position])
        holder.binding.btnCoba.setOnClickListener {
            onClick(listAksara[position])
        }
    }

    override fun getItemCount(): Int = listAksara.size

    class ViewHolder(val binding: ItemAksaraBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(aksara: Aksara) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(aksara.symbol)
                    .into(imgAksara)
                tvAksara.text = aksara.nama
            }
        }
    }
}