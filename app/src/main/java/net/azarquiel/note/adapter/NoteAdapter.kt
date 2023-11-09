package net.azarquiel.note.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.note.R
import net.azarquiel.note.model.Note

class NoteAdapter(val context: Context,
                     val layout: Int
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var dataList: List<Note> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setNotes(notes: List<Note>) {
        this.dataList = notes
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Note){
            val tvRowNoteName = itemView.findViewById(R.id.tvRowNoteName) as TextView
            val tvRowNoteStock = itemView.findViewById(R.id.tvRowNoteStock) as TextView


            tvRowNoteName.text = dataItem.name
            tvRowNoteStock.text = dataItem.stock

            itemView.tag = dataItem
        }
    }
}