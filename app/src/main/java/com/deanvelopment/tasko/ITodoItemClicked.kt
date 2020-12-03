package com.deanvelopment.tasko

import android.view.View
// Interface for the function of the recycler view in the List of to dos
interface ITodoItemClicked
{
    fun todoClicked (position: Int, itemView: View)

    fun deleteClicked (position: Int)
}