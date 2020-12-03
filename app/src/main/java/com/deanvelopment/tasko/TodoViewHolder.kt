package com.deanvelopment.tasko

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton

import androidx.recyclerview.widget.RecyclerView
import com.pg18jonathan.todolist.R

// Holder for the recycler view of the list of to dos
class TodoViewHolder (inflater: LayoutInflater,
                      parent: ViewGroup,
                      todoInterface : ITodoItemClicked
)
    : RecyclerView.ViewHolder(inflater.inflate(R.layout.todo_item, parent,false))
{
    // Create variables to store the elements of the layout
    private var todoCheckbox: CheckBox? = null
    private var deleteButton: ImageButton? = null

    init
    {
        // Initializing the variables setting them to the elements of the to dos items
        todoCheckbox = itemView.findViewById(R.id.todoCheckbox)
        deleteButton = itemView.findViewById(R.id.deleteButton)
    }

    fun bind (todoData: TodoData)
    {
        // Bind the elements of the layout to the data
        todoCheckbox?.text = todoData.description
        todoCheckbox?.isChecked = todoData.isDone
    }

}