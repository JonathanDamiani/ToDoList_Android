package com.deanvelopment.tasko

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.pg18jonathan.todolist.R
import java.lang.ClassCastException

// Fragment used to add items to the list
class AddItemFragment : Fragment() {
    // Interface of function to this fragment
    interface IAddFragmentButtons
    {
        fun onAddButtonClicked(title: String)

        fun onCancelButtonClicked()
    }

    // A lateinit var that will initialize late in the application
    // representing the interface of this fragment.
    private lateinit var buttonsListener: IAddFragmentButtons

    // Companion to make this fragment a singleton, it can only have one
    companion object {
        fun newInstance() : AddItemFragment
        {
            return AddItemFragment()
        }
    }

    // OnCreateView of this fragment, it is called when the fragment is created
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // inflater that represent which layout will be use for the fragment
        val listFragment = inflater.inflate(R.layout.add_item_fragment, container, false)

        view?.requestLayout()

        // Elements from the layout of the fragment, to be used
        val addButton = listFragment.findViewById<Button>(R.id.addListBtn)
        val cancelButton = listFragment.findViewById<Button>(R.id.cancelAddListBtn)
        val enteredTitle = listFragment.findViewById<EditText>(R.id.addListTitleInput)

        // event listener for click that pass the value of the input as string to
        // the onAddButtonClicked of the interface
        addButton.setOnClickListener {
            buttonsListener.onAddButtonClicked(enteredTitle.text.toString())
        }

        // event listener for the click on the cancel button
        cancelButton.setOnClickListener {
            buttonsListener.onCancelButtonClicked()
        }

        return listFragment
    }

    // Function to attach the fragment
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // If context is IAddFragmentButtons set the interface as the context
        if (context is IAddFragmentButtons)
        {
            buttonsListener = context
        }
        else
        {
            throw  ClassCastException("$context must implement IAddListFragmentButtons")
        }
    }
}
