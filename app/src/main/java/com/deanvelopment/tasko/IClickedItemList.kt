package com.deanvelopment.tasko

// Interface for the function of the recycler view in the List of groups
interface IClickedItemList
{
    fun itemClicked (title : String, position: Int)

    fun itemLongClicked (position: Int)
}