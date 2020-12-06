package com.amaizzzing.dictionary.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amaizzzing.dictionary.R
import com.amaizzzing.dictionary.model.data.SearchResult

class MainAdapter(private var onListItemClickListener: OnListItemClickListener, private var data: List<SearchResult>) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    fun setData(data: List<SearchResult>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val header_textview_recycler_item: TextView = view.findViewById(R.id.header_textview_recycler_item)
        val description_textview_recycler_item: TextView = view.findViewById(R.id.description_textview_recycler_item)
        fun bind(data: SearchResult) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                header_textview_recycler_item.text = data.text
                description_textview_recycler_item.text = data.meanings?.get(0)?.translation?.translation

                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }
    }

    private fun openInNewWindow(listItemData: SearchResult) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: SearchResult)
    }
}
