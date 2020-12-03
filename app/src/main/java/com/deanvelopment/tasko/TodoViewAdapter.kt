package com.deanvelopment.tasko

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_item.view.*

// Adapter for the To Do recycler view
class TodoViewAdapter (private val list: List<TodoData>, contextInterface: ITodoItemClicked)
    : RecyclerView.Adapter<TodoViewHolder>() {

    // Variable that store the context Interface
    private var todoInterface : ITodoItemClicked = contextInterface

    // On create view holder override for the adapter receiving the TodoViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        return TodoViewHolder(
            inflater,
            parent,
            todoInterface
        )
    }

    // Override of the function to bind the view holder
    // Receives the holder and the position
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int)
    {
        // Creating list of the ToDoData and setting the position then binding it using the bind
        // method of the holder
        val itemList: TodoData = list[position]
        holder.bind(itemList)

        // Change some properties of the done to dos as the recycler view is generated
        if (holder.itemView.todoCheckbox.isChecked)
        {
            holder.itemView.todoCheckbox.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.itemView.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#737373"))
        }

        // Setting the delete button listener to the interface function
        holder.itemView.deleteButton.setOnClickListener {
            todoInterface.deleteClicked(position)
        }

        // Setting the checkbox button listener to the interface function
        holder.itemView.todoCheckbox.setOnClickListener {
            todoInterface.todoClicked(position, holder.itemView)
        }

    }

    // Getting the item count to the list size
    override fun getItemCount(): Int = list.size
}