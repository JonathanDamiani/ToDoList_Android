package com.deanvelopment.tasko

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// Adapter for the group list recycler view
class ListViewAdapter (private val list: List<ListData>, contextInterface: IClickedItemList)
    : RecyclerView.Adapter<ListViewHolder>()
{
    // Variable that store the context Interface
    private var listInterface : IClickedItemList = contextInterface

    // On create view holder override for the adapter receiving the ListViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder
    {
        val inflater = LayoutInflater.from( parent.context )
        return ListViewHolder(
            inflater,
            parent,
            listInterface
        )
    }

    // Override of the function to bind the view holder
    // Receives the holder and the position
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        // Creating list of the ListData and setting the position then binding it using the bind
        // method of the holder
        val itemList : ListData = list[position]
        holder.bind(itemList)

        // Setting the click listener on the element to the interface function
        holder.itemView.setOnClickListener {
            listInterface.itemClicked(itemList.title, position)
        }

        // Setting the long click listener on the element to the interface function
        holder.itemView.setOnLongClickListener {
            listInterface.itemLongClicked(position)
            true
        }
    }

    // Getting the item count to the list size
    override fun getItemCount(): Int = list.size
}