package com.app.soulstudio.view.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.soulstudio.model.dataclass.Items
import com.app.soulstudio.model.dataclass.VolumeInfo
import com.app.soulstudio.view.callbacks.BookCallbacks
import com.app.soulstudio.view.viewholder.BookViewHolder

class BookAdapter(val iBookCallbacks: BookCallbacks.Companion.IBookCallbacks) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "BookAdapter"


    private val differCallback = object : DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
            Log.d(TAG, "areItemsTheSame: " + (oldItem.id == newItem.id))
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
            Log.d(TAG, "areContentsTheSame: " + (oldItem == newItem))
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BookViewHolder.createViewHolder(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bookViewHolder = holder as BookViewHolder
        bookViewHolder.bind(
            holder,
            position,
            differ.currentList.get(position),
            iBookCallbacks
        )
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Items>?) {
        differ.submitList(list)
    }

}