package com.deanvelopment.tasko

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pg18jonathan.todolist.R
import kotlinx.android.synthetic.main.activity_groups_of_todos.*

// Activity for the screen with the group of toDos
class GroupsOfToDos : AppCompatActivity(),
    IClickedItemList,
    AddItemFragment.IAddFragmentButtons
{
    // set val groupAdapter to be the ListViewAdapter and pass the Singleton AllData.dataList to it
    // and the context of the interface as this class
    private var groupAdapter = ListViewAdapter(
        AllData.dataList,
        this
    )

    override fun onBackPressed() { // Do Here what ever you want do on back press;
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groups_of_todos)

        // Prevent keyboard to show up when the pages appears
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setSupportActionBar(findViewById(R.id.listToolBar))

        // If there is no data in the Data, initialize the data with default data, to show the user,
        // how the ap works,
        if (AllData.dataList.isEmpty() && !checkIfExists())
        {
            AllData.init()
            storeData()
        }
        else
        {
            AllData.dataList.clear()
            readData()
            groupAdapter = ListViewAdapter(
                AllData.dataList,
                this
            )
        }

        // Setting the layout manager for the View recycler
        ListViewRecycler.layoutManager = LinearLayoutManager (this)

        // Setting the adapter for the View Recycler
        ListViewRecycler.adapter = groupAdapter

        // Setting the event listener to the floating add button
        // When clicked open the fragment to add items.
        addListFloatingButton.setOnClickListener {
            if (savedInstanceState == null)
            {
                supportFragmentManager.beginTransaction()
                    .add(
                        R.id.addListFragmentId,
                        AddItemFragment.newInstance(),
                        "addListFragmentTag")
                    .commit()
            }
            addListFloatingButton.visibility = View.INVISIBLE
        }

        logoutBtn.setOnClickListener {
            FirebaseController.logoutUser()
            FirebaseController.EMAIL = ""
            FirebaseController.PASS = ""
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    // Reading data from shared preferences
    private fun readData ()
    {
        val sharedPref =this.getSharedPreferences("AllData", Context.MODE_PRIVATE) ?: return

        val data = sharedPref.getString("my-data", "")

        val listType = object : TypeToken<MutableList<ListData>>() {}.type

        AllData.dataList.addAll(Gson().fromJson(data, listType ))
    }

    // Store data function to groups, using shared preferences
    private fun storeData ()
    {
        var stringData = Gson().toJson(AllData.dataList)

        val sharedPref = this.getSharedPreferences("AllData",Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("my-data", stringData)
            commit()
        }
    }


    // Function to check if the data exist
    private fun checkIfExists(): Boolean
    {
        val sharedPref =this.getSharedPreferences("AllData", Context.MODE_PRIVATE)

        if (sharedPref.contains("my-data"))
            return true
        return false
    }

    // override function from the add btn from the fragment. Base function in
    // AddItemFragment.IAddFragmentButtons
    override fun onAddButtonClicked (title : String)
    {

        if (title.isEmpty())
        {
            Tools.toastIt(this,"Need to type something")
        }
        else
        {
            // Close the fragment
            closeFragment()
            // Add the new group
            addNewGroup (title)
            // hide the keyboard
            hideKeyboard()
            // Set the add button to visible
            addListFloatingButton.visibility = View.VISIBLE
        }
    }

    // override function from cancel btn from the fragment. Base function in
    // AddItemFragment.IAddFragmentButtons
    override fun onCancelButtonClicked()
    {
        // Close the Fragment
        closeFragment()
        // Set the add button to visible
        addListFloatingButton.visibility = View.VISIBLE
    }
    // Override function from IClickedItemList Interface, that is used by the View Recycle
    // This function carry the position of the item clicked in the list of groups
    // And the title of the clicked
    override fun itemClicked(title: String, position : Int)
    {
        // Change activity for the to dos list within the group
        // And pass the position of the clicked item to other screen in order to track
        // the list by its index on the List that store all the data.
        val intent = Intent(this, ToDos::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
    }

    // Override function from IClickedItemList Interface, that is used by the View Recycle
    // This function carry the position of the item clicked and it calls and the user long click
    // One the items
    override fun itemLongClicked(position: Int)
    {
        AllData.deleteFromList(position)

        groupAdapter.notifyItemInserted(0)

        ListViewRecycler.adapter = groupAdapter

        storeData ()

    }

    // Function To add new group of todos
    // Receive the title as string
    private fun addNewGroup(title: String)
    {
        // Call the add method of the data
        AllData.addToList(title)

        storeData()

        // Update the recycle view
        groupAdapter.notifyItemInserted(0)
        ListViewRecycler.adapter = groupAdapter
    }

    // Function to Hide Keyboard if it exist on this view
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

    // Function to close fragment
    private fun closeFragment ()
    {
        val listFragment = supportFragmentManager.findFragmentByTag("addListFragmentTag")
        supportFragmentManager
            .beginTransaction()
            .remove(listFragment!!)
            .commit()
    }
}
