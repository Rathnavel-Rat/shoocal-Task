package com.hardhammer.assignment.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CommonAdaptor<T>(private val layoutId:Int, var listItems:List<T>, private val bindItem: (T, View) -> Unit, val hasStableIds:Boolean=false):RecyclerView.Adapter<CommonAdaptor.CviewHolder<T>>() {
    init{
        setHasStableIds(hasStableIds)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CviewHolder<T> {
        val view=LayoutInflater.from(parent.context).inflate(layoutId,parent,false)
        return CviewHolder<T>(
            view
        )
    }

    override fun getItemCount(): Int {
        return listItems.size

    }
    fun add(listItems:List<T>){
        this.listItems= listItems
        notifyDataSetChanged()
    }
    fun removeAt(position: Int){

        notifyItemRemoved(position)

        notifyItemRangeChanged(position,listItems.size)
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {

        return if(hasStableIds) position.toLong() else super.getItemId(position)}




    override fun onBindViewHolder(holder: CviewHolder<T>, position: Int) {

        bindItem(listItems[position],holder.itemView)


    }



    class CviewHolder<T>(view: View):RecyclerView.ViewHolder(view){



    }



}