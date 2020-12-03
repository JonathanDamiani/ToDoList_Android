package com.deanvelopment.tasko

// Data for the groups
data class ListData (var title: String,
                     var numberOfItem : Int)
{
    // list of to dos, as mutable list, nested to the groups
    var listOfTodos = mutableListOf<TodoData>()
}