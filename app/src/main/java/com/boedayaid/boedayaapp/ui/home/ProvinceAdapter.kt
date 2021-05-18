package com.boedayaid.boedayaapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boedayaid.boedayaapp.R
import com.boedayaid.boedayaapp.data.model.Province
import com.boedayaid.boedayaapp.databinding.CarouselProvinceItemBinding
import com.bumptech.glide.Glide

class ProvinceAdapter : RecyclerView.Adapter<ProvinceAdapter.ViewHolder>() {
    private val province = mutableListOf<Province>()

    fun setList(castList: List<Province>) {
        province.clear()
        province.addAll(castList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CarouselProvinceItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(province[position])
    }

    override fun getItemCount(): Int = province.size

    class ViewHolder(private val binding: CarouselProvinceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(prov: Province) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(prov.image)
                    .into(imgProvince)
                textViewNameProvince.text = prov.name
            }
        }
    }
}