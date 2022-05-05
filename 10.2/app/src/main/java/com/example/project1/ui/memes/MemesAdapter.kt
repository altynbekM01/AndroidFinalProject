package com.example.project1.ui.memes

import android.content.ClipData
import android.content.ClipboardManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project1.R
import com.example.project1.base.ParentViewHolder
import com.example.project1.data.models.DefaultMemeInfo


class MemesAdapter :
    RecyclerView.Adapter<ParentViewHolder>() {

    private val VIEW_TYPE_ERROR = 0
    private val VIEW_TYPE_NORMAL = 1

    private val memes = ArrayList<DefaultMemeInfo>()


    fun clearAll() {
        memes.clear()
        notifyDataSetChanged()
    }


    fun addItems(list: List<DefaultMemeInfo>) {
        memes.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_NORMAL -> MemesViewHolder(
                inflater.inflate(R.layout.adapter_meme_default, parent, false)
            )
            else -> throw Throwable("invalid view")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DefaultMemeInfo -> {
                return VIEW_TYPE_NORMAL
            }
            else -> {
                return VIEW_TYPE_ERROR
            }
        }
    }

    override fun getItemCount(): Int = memes.size

    fun copyToClipboard(value: String) {

    }

    fun getItem(position: Int): DefaultMemeInfo? {
        return memes[position]
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        if (holder is MemesViewHolder) {
            val meme = memes[position]
            holder.bind(meme)
        }
    }

    inner class MemesViewHolder(view: View) : ParentViewHolder(view) {
        private val ivMeme: ImageView = view.findViewById(R.id.iv_meme)
        private val tvInfo: TextView = view.findViewById(R.id.tv_info)
        private val ivCopy: ImageView = view.findViewById(R.id.iv_copy)

        fun bind(memeResponse: DefaultMemeInfo) {
            ivCopy.setOnClickListener {
                val clipboard =
                    ContextCompat.getSystemService(itemView.context, ClipboardManager::class.java)
                val clip = ClipData.newPlainText("meme", memeResponse.id)
                clipboard?.setPrimaryClip(clip)
                Toast.makeText(itemView.context, "id скопирован!", Toast.LENGTH_SHORT).show()
            }
            Glide.with(itemView.context)
                .load(memeResponse.url)
                .into(ivMeme)
            val str = StringBuilder()
                .append("name: ${memeResponse.name}\n")
                .append("template id: ${memeResponse.id}")
            tvInfo.text = str
        }

        override fun clear() {}
    }

}