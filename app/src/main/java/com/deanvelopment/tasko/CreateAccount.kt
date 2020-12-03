package com.deanvelopment.tasko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.pg18jonathan.todolist.R
import kotlinx.android.synthetic.main.activity_create_account.*

// Create Account Activity

// It is no accessible yet
class CreateAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        saveAccountBtn.setOnClickListener {
            createAccountFun()
        }
    }


    private fun createAccountFun()
    {
        // Get text from user and store in string values
        val enteredEmail : String = emailTextInput.text.toString()
        val enteredPassword : String = passwordTextInput.text.toString()
        val comparePassword : String = passwordConfirmTextInput.text.toString()

        if ( enteredPassword == comparePassword)
        {
            FirebaseController.EMAIL = enteredEmail
            FirebaseController.PASS = enteredPassword

            FirebaseController.registerUser(this)
        }
        else
        {
            Tools.toastIt(this, "Passwords do not match.")
        }

    }
    // Function to call the finish method from this Activity to go back to previous screen
    fun goBackBtn (v: View)
    {
        finish()
    }

}
