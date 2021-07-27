package com.app.soulstudio.view.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.soulstudio.R
import com.app.soulstudio.databinding.ItemBookBinding
import com.app.soulstudio.model.dataclass.Items
import com.app.soulstudio.model.dataclass.VolumeInfo
import com.app.soulstudio.view.callbacks.BookCallbacks
import com.bumptech.glide.Glide

class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val TAG = "BookViewHolder"
    private val itemBookBinding = ItemBookBinding.bind(itemView)

    fun bind(
        holder: RecyclerView.ViewHolder,
        position: Int,
        items: Items,
        iBookCallbacks: BookCallbacks.Companion.IBookCallbacks
    ) {

        itemBookBinding.run {
            tvBookTitle.text = items.volumeInfo.title

            if (items.volumeInfo.description == null) {
                tvBookDescription.text = "No description available"
            } else {
                tvBookDescription.text = items.volumeInfo.description
            }


            if (items.volumeInfo.imageLinks != null) {
                Glide.with(holder.itemView.context)
                    .load(items.volumeInfo.imageLinks.smallThumbnail)
                    .into(ivBookThumbnail)
            }

            cvBook.setOnClickListener {
                iBookCallbacks.onBookClick(position, items)
            }
        }

    }


    companion object {
        fun createViewHolder(parent: ViewGroup): BookViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
            return BookViewHolder(view)
        }
    }
}