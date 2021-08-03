package com.boedayaid.boedayaapp.ui.profile.tab_layout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boedayaid.boedayaapp.data.model.Ticket
import com.boedayaid.boedayaapp.databinding.ItemTicketBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class TicketListAdapter : RecyclerView.Adapter<TicketListAdapter.ViewHolder>() {

    private val ticetList = mutableListOf<Ticket>()
    private lateinit var onClick: (Ticket) -> Unit

    fun setList(list: List<Ticket>) {
        ticetList.clear()
        ticetList.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnClick(click: (Ticket) -> Unit) {
        onClick = click
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTicketBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ticetList[position])
        holder.itemView.setOnClickListener {
            onClick(ticetList[position])
        }
    }

    override fun getItemCount(): Int = ticetList.size

    class ViewHolder(private val binding: ItemTicketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ticket: Ticket) {
            binding.apply {
                if (ticket.transactionProve.isNotEmpty()) {
                    Glide.with(itemView.context)
                        .load(ticket.tempatWisata.gambar)
                        .into(binding.imgTempatWisata)
                }
                tvTitle.text = ticket.tempatWisata.namaTempat
                tvAmount.text = "${ticket.amount} orang"
                tvExpDate.text = formatDate(ticket.dateExpired)
            }
        }

        @SuppressLint("SimpleDateFormat")
        private fun formatDate(millis: Long): String {

            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = millis

            return formatter.format(calendar.time)
        }
    }


}