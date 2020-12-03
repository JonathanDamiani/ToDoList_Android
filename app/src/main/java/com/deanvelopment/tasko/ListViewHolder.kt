package com.deanvelopment.tasko

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.pg18jonathan.todolist.R

// Holder for the recycler view of the groups
class ListViewHolder (inflater: LayoutInflater,
                      parent: ViewGroup,
                      listInterface: IClickedItemList
)
    : RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_card, parent,false))
{
    // Create variables to store the elements of the layout
    private var titleTextView: TextView? = null
    private var numberOfItems: TextView? = null

    init
    {
        // Initializing the variables setting them to the elements of the list item
        titleTextView = itemView.findViewById(R.id.ListItemTitleId)
        numberOfItems = itemView.findViewById(R.id.ListItemNumberId)
    }

    fun bind (listData: ListData)
    {
        // Bind the elements of the layout to the data
        titleTextView?.text = listData.title
        numberOfItems?.text = listData.numberOfItem.toString()
    }

}