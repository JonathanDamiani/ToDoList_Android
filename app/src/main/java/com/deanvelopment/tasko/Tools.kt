package com.deanvelopment.tasko

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Class create to make easier to reuse some functions
class Tools : AppCompatActivity()
{
    companion object {
        // Function to show message to user as a toast
        fun toastIt(context: Context, message: String) {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}