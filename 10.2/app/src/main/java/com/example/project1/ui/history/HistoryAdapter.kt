package com.example.project1.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project1.R
import com.example.project1.base.ParentViewHolder
import com.example.project1.data.models.MemeData


class HistoryAdapter :
    RecyclerView.Adapter<ParentViewHolder>() {

    private val VIEW_TYPE_ERROR = 0
    private val VIEW_TYPE_NORMAL = 1

    private val memes = ArrayList<MemeData>()


    fun clearAll() {
        memes.clear()
        notifyDataSetChanged()
    }


    fun addItems(list: List<MemeData>) {
        memes.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_NORMAL -> HistoriesViewHolder(
                inflater.inflate(R.layout.adapter_meme, parent, false)
            )
            else -> throw Throwable("invalid view")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MemeData -> {
                return VIEW_TYPE_NORMAL
            }
            else -> {
                return VIEW_TYPE_ERROR
            }
        }
    }

    override fun getItemCount(): Int = memes.size

    fun getItem(position: Int): MemeData? {
        return memes[position]
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        if (holder is HistoriesViewHolder) {
            val history = memes[position]
            holder.bind(history)
        }
    }

    inner class HistoriesViewHolder(view: View) : ParentViewHolder(view) {
        private val ivMeme: ImageView = view.findViewById(R.id.iv_meme)

        fun bind(memeResponse: MemeData) {
            Glide.with(itemView.context)
                .load(memeResponse.url)
                .into(ivMeme)
        }

        override fun clear() {}
    }

}