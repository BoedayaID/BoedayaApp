package com.boedayaid.boedayaapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boedayaid.boedayaapp.data.model.Province
import com.boedayaid.boedayaapp.databinding.ItemCarouselProvinceBinding
import com.bumptech.glide.Glide

class ProvinceAdapter : RecyclerView.Adapter<ProvinceAdapter.ViewHolder>() {
    private val province = mutableListOf<Province>()
    private lateinit var onClick: (Int) -> Unit


    fun setList(list: List<Province>) {
        province.clear()
        province.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnClick(click: (Int) -> Unit) {
        onClick = click
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCarouselProvinceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(province[position])
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int = province.size

    class ViewHolder(private val binding: ItemCarouselProvinceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(prov: Province) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(prov.image)
                    .into(imgProvince)
                tvProvinceName.text = prov.name
            }
        }
    }
}