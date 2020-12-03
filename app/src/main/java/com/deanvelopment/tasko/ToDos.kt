package com.deanvelopment.tasko

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.pg18jonathan.todolist.R
import kotlinx.android.synthetic.main.activity_todo_list.*
import kotlinx.android.synthetic.main.todo_item.view.*

// Class to the ToDos Activity
class ToDos : AppCompatActivity(),
    AddItemFragment.IAddFragmentButtons,
    ITodoItemClicked
{
    // Var to represent the position of the list of toDos on the general data
    private var positionIndexOfTheList: Int = 0

    // TodoAdapter, initializing the adapter for the recycler view
    private var todoAdapter = TodoViewAdapter(
        AllData.dataList[0].listOfTodos,
        this
    )

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        // Prevent keyboard to show up when the pages appears
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setSupportActionBar(findViewById(R.id.listTodoToolBar))
        // Enabling the support action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setting the position of the list in the data, as the position on the list of groups,
        // receive via intent from the groups screen.
        positionIndexOfTheList = intent.getIntExtra("position", 0)

        // Setting the title of the page as the title of the group.
        listTodoToolBar.title = AllData.dataList[positionIndexOfTheList].title

        // Setting the layout manager of the list recycler
        TodoListRecycler.layoutManager = LinearLayoutManager (this)

        // Setting the value of the todoAdapter to the right position on the data
        todoAdapter = TodoViewAdapter(
            AllData.dataList[positionIndexOfTheList].listOfTodos,
            this
        )

        // Setting the Recycler adapter to be the right adapter
        TodoListRecycler.adapter = todoAdapter

        // Setting the listener to the function to open the fragment o add ToDos
        todoFloatingActionButton.setOnClickListener {
            if (savedInstanceState == null)
            {
                supportFragmentManager.beginTransaction()
                    .add(
                        R.id.addItemFragmentId,
                        AddItemFragment.newInstance(),
                        "addItemFragmentTag")
                    .commit()
            }

            // Setting the button invisible when the fragment is open
            todoFloatingActionButton.visibility = View.INVISIBLE
        }

    }

    // Store data function to to dos
    private fun storeData ()
    {
        var stringData = Gson().toJson(AllData.dataList)

        val sharedPref = this.getSharedPreferences("AllData",Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("my-data", stringData)
            commit()
        }
    }

    // Handle the click on the To Do item from the interface
    override fun todoClicked(position: Int, itemView: View)
    {
        // Depending if the To Do is marked as done or not, set its style and change it position
        // on the screen
        if (AllData.toggleTodo(
                positionIndexOfTheList,
                position
            )
        )
        {
            // Get the last position of the list
           // val lastPosition = AllData.dataList[positionIndexOfTheList].listOfTodos.lastIndex

           // Collections.swap(AllData.dataList[positionIndexOfTheList].listOfTodos, position, lastPosition)
            // update the adapter to change the list
           // todoAdapter.notifyItemMoved(position , lastPosition)

            // Change the styles of the done to do
            itemView.todoCheckbox.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.midGray))

            // Refreshing the recycle view
           // TodoListRecycler.adapter = todoAdapter
        }
        else
        {
            //TODO : Fix position change, swap does not work
            // Change the undone to do to the first position of the list
            //Collections.swap(AllData.dataList[positionIndexOfTheList].listOfTodos, position, 0)
           // todoAdapter.notifyItemMoved(position , 0)

            // Change the styles of the undone to do to the normal
            itemView.todoCheckbox.paintFlags = 0
            itemView.backgroundTintList = ColorStateList.valueOf(getColor(R.color.lightGray))
        }
        storeData ()

    }

    // On the add button is clicked in the fragment
    override fun onAddButtonClicked (description : String)
    {
        if (description.isEmpty())
        {
            Tools.toastIt(this,"Need to type something")
        }
        else
        {
            // Close the fragment
            closeFragment()
            // Create the to do
            createTodo (description)
            // Hide the keyboard
            hideKeyboard()
            // Show the add button again
            todoFloatingActionButton.visibility = View.VISIBLE
        }
    }

    // Function to create a to do
    private fun createTodo (description: String)
    {
        // Add to do to the data
        AllData.addTodo(
            positionIndexOfTheList,
            description
        )

        // Notify the adapter the an item was inserted on the data
        todoAdapter.notifyItemInserted(0)

        // Update the recycler view
        TodoListRecycler.adapter = todoAdapter

        // Update the number of item in the group
        AllData.dataList[positionIndexOfTheList].numberOfItem = todoAdapter.itemCount
        storeData ()
    }


    // Handle the click on the delete button of the To Do item from the interface
    override fun deleteClicked(position: Int)
    {
        // Delete the item from the data
        AllData.deleteTodo(
            positionIndexOfTheList,
            position
        )

        // Notify the adapter of the removed item.
        todoAdapter.notifyItemRemoved(position)

        // Remove from the view
        TodoListRecycler.removeViewAt(position)

        // Update the item counts in the group
        AllData.dataList[positionIndexOfTheList].numberOfItem = todoAdapter.itemCount
        storeData ()
    }

    // Function to delete all to dos marked as done
    private fun deleteAllDoneToDos ()
    {
        Tools.toastIt(this,"Delete all ToDos")

        // Delete all done to dos from the data
        AllData.deleteAllDoneToDos(
            positionIndexOfTheList
        )

        // Notify the data change
        todoAdapter.notifyDataSetChanged()

        // Update the item counts in the group
        AllData.dataList[positionIndexOfTheList].numberOfItem = todoAdapter.itemCount
        storeData ()
    }

    // Function to handle the fragment when the cancel button is clicked
    override fun onCancelButtonClicked()
    {
        // Close the fragment
        closeFragment()
        // Show the add button again
        todoFloatingActionButton.visibility = View.VISIBLE
    }

    // Function to hide the keyboard
    private fun hideKeyboard ()
    {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }

    // Function to close the fragment
    private fun closeFragment ()
    {
        val listFragment = supportFragmentManager.findFragmentByTag("addItemFragmentTag")
        supportFragmentManager
            .beginTransaction()
            .remove(listFragment!!)
            .commit()
    }

    // Create the options menu
    // Menu in res/menu/menu_lists
    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.menu_todo_list, menu)
        return true
    }

    // Get the clicks for the options in the action bar
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId)
    {

        R.id.deleteAllTodos ->
        {
            deleteAllDoneToDos()
            true
        }

        else ->
        {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}
