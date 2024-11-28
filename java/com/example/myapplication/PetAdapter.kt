package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PetAdapter (private val lst : ArrayList<Pet>) : RecyclerView.Adapter<PetAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder (itemView) {
        val txt : TextView = itemView.findViewById(R.id.list_item_name)
        val img : ImageView = itemView.findViewById(R.id.list_item_img)
        val txtDesc : TextView = itemView.findViewById(R.id.list_item_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetAdapter.ViewHolder {
        val petItemView = LayoutInflater.from(parent.context).inflate(R.layout.dog_item, parent, false)
        return ViewHolder(petItemView)
    }

    override fun onBindViewHolder(holder: PetAdapter.ViewHolder, position: Int) {
        val pet : Pet = lst[position]
        holder.txt.text = pet.getName()
        holder.txtDesc.text = pet.getDesc()
        holder.img.setImageResource(pet.getImg())

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, pet)
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Pet)
    }

    fun setOnClickListener (listener: OnClickListener?) {
        this.onClickListener = listener
    }
}