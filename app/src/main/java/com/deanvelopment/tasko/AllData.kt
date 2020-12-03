package com.deanvelopment.tasko

// Class with all the data of the application
class AllData
{
    // Singleton object to old all the data, can be used anywhere
    companion object AllAppData
    {
        // mutable list to store the data, It is type ListData represent the group of todos,
        // inside that list we have another one of type TodoData that represents the list of todos
        // inside the group
        var dataList = mutableListOf<ListData>()

        //var dataList = HashMap<String, ListData>()

        // Init function to initialize some data for the first use
        fun init ()
        {
            addToList("Click And Hold To Delete")
            addToList("ToDos Tutorial")

            addTodo(
                0,
                "Click the trash in the Top Bar"
            )
            addTodo(
                0,
                "delete all done is really simple "
            )
            addTodo(
                0,
                "To delete click on the bin ->>"
            )
            addTodo(
                0,
                "simple todo"
            )

            toggleTodo(0, 2)
            toggleTodo(0, 3)

            dataList[0].numberOfItem = 5
        }

        // Function to add to the data
        fun addToList (title : String)
        {
            val dataToAdd = ListData(title, 0)

            dataList.add(0, dataToAdd);
        }

        // Function to delete a item from the data
        fun deleteFromList (position: Int)
        {
            dataList.removeAt(position)
        }

        // Function to add todos to the list of todos within the group of todos
        fun addTodo (positionOnGeneralList: Int, description: String)
        {
            val dataToAdd =
                TodoData(description, false)

            dataList[positionOnGeneralList].listOfTodos.add(0, dataToAdd)
        }

        // Function to delete todos
        fun deleteTodo (positionOnGeneralList: Int, position: Int )
        {
            dataList[positionOnGeneralList].listOfTodos.removeAt(position)
        }

        // Function to toggle todos to be done or not.
        fun toggleTodo (positionOnGeneralList: Int, position: Int) : Boolean
        {
            dataList[positionOnGeneralList].listOfTodos[position].isDone =
                !dataList[positionOnGeneralList].listOfTodos[position].isDone

            return dataList[positionOnGeneralList].listOfTodos[position].isDone
        }

        // function to delete all done todos
        fun deleteAllDoneToDos (positionOnGeneralList: Int)
        {
            dataList[positionOnGeneralList].listOfTodos.removeAll { it.isDone }
        }
    }
}