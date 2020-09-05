package com.thesis.project.ui.mynotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thesis.project.R
import com.thesis.project.databinding.RecyclerItemArnoteModelBinding
import com.thesis.project.models.arnote.ArNote

class MyNotesAdapter(private var context: Context) : RecyclerView.Adapter<MyNotesAdapter.ViewHolder>()
{
    private var arNoteList : MutableList<ArNote> = mutableListOf()
    private var layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNotesAdapter.ViewHolder {
        val recyclerItemArNoteModelBinding =
            RecyclerItemArnoteModelBinding.inflate(layoutInflater,parent,false)

        return ViewHolder(recyclerItemArNoteModelBinding.root,recyclerItemArNoteModelBinding)
    }

    fun setArNoteList(arNoteList: List<ArNote>)
    {
        this.arNoteList.addAll(arNoteList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyNotesAdapter.ViewHolder, position: Int) { holder.setData(arNoteList[position]) }

    override fun getItemCount(): Int = arNoteList.size

    inner class ViewHolder(private var view: View, private val recyclerItemArnoteModelBinding: RecyclerItemArnoteModelBinding)
        : RecyclerView.ViewHolder(view)
        {
            private val arnote_cardview : CardView = view.findViewById(R.id.recycler_cardview)
            fun setData(arnote : ArNote)
            {
                imageViewColorSelector(arnote)
                recyclerItemArnoteModelBinding.arNoteModel = arnote
            }

            private fun imageViewColorSelector(arnote : ArNote)
            {
                var drawableId = 0
                when (arnote.type)
                {
                    "Normal" ->  drawableId = R.drawable.rounded_corner_normal
                    "Warning" -> drawableId = R.drawable.rounded_corner_warning
                    "Urgent" ->  drawableId = R.drawable.rounded_corner_urgent
                }
                arnote_cardview.setBackgroundResource(drawableId)
            }
        }
}