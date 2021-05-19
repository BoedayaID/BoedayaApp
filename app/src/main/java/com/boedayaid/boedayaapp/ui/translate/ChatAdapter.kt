package com.boedayaid.boedayaapp.ui.translate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boedayaid.boedayaapp.data.model.Chat
import com.boedayaid.boedayaapp.data.model.ChatFrom
import com.boedayaid.boedayaapp.data.model.ChatTo
import com.boedayaid.boedayaapp.data.model.ChatType
import com.boedayaid.boedayaapp.databinding.ItemChatFromBinding
import com.boedayaid.boedayaapp.databinding.ItemChatToBinding

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listChat = mutableListOf<Chat>()
    var onClickTranslate: (position: Int) -> Unit = {}

    fun setList(list: List<Chat>) {
        listChat.clear()
        listChat.addAll(list)
        notifyDataSetChanged()
    }

    fun addChat(chat: Chat) {
        listChat.add(chat)
    }

    fun setOnTranslate(onClick: (position: Int) -> Unit) {
        onClickTranslate = onClick
    }

    override fun getItemViewType(position: Int): Int {
        return if (listChat[position] is ChatFrom) {
            0
        } else {
            1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val binding = ItemChatFromBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ViewHolderChatFrom(binding)
        } else {
            val binding = ItemChatToBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            ViewHolderChatTo(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            val hold = holder as ViewHolderChatFrom
            hold.bind(listChat[position] as ChatFrom)
            hold.binding.btnTranslateAkssara.setOnClickListener {
                onClickTranslate(position)
            }
        } else {
            val hold = holder as ViewHolderChatTo
            hold.bind(listChat[position] as ChatTo)
        }
    }

    override fun getItemCount(): Int = listChat.size

    class ViewHolderChatFrom(val binding: ItemChatFromBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatFrom) {
            binding.apply {
                tvChat.text = chat.text
                when (chat.type) {
                    ChatType.Regular -> {
                        tvChat.textSize = 14f
                        btnTranslateAkssara.visibility = View.VISIBLE
                    }
                    ChatType.Aksara -> {
                        tvChat.textSize = 24f
                        btnTranslateAkssara.visibility = View.GONE
                    }
                }
            }
        }
    }

    class ViewHolderChatTo(private val binding: ItemChatToBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatTo) {
            binding.apply {
                tvChat.text = chat.text
            }
        }
    }
}