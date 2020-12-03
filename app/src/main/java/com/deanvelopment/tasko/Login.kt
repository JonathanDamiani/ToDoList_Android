package com.deanvelopment.tasko

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.pg18jonathan.todolist.R
import kotlinx.android.synthetic.main.activity_main.*

// It is no accessible yet
class Login : AppCompatActivity()
{

    val tools = Tools()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
        // Prevent keyboard to show up when the pages appears
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        FirebaseController.auth = FirebaseAuth.getInstance()
//
//        if (FirebaseController.auth.currentUser != null)
//        {
//            val intent = Intent(this, GroupsOfToDos::class.java)
//            startActivity(intent)
//        }

    }

    // Login Function, user: admin / password = admin.
    fun loginFunction (v: View)
    {
        // Get text from user and store in string values
        val enteredEmail : String = emailLoginTextInput.text.toString()
        val enteredPassword : String = passwordLoginTextInput.text.toString()

        FirebaseController.EMAIL = enteredEmail;
        FirebaseController.PASS = enteredPassword;

        // If pass the right information go to another activity
        // else show message to the user with the right information

        var successLogin = FirebaseController.loginUser(this)

        if (successLogin)
        {
            // Change screen/activity
            val intent = Intent(this, GroupsOfToDos::class.java)
            startActivity(intent)
        }
    }

//    // Login Function, user: admin / password = admin.
//    fun loginGmailFunction (v: View)
//    {
//        // Get text from user and store in string values
//        val enteredEmail : String = emailLoginTextInput.text.toString()
//        val enteredPassword : String = passwordLoginTextInput.text.toString()
//
//        FirebaseController.EMAIL = enteredEmail;
//        FirebaseController.PASS = enteredPassword;
//
//        // If pass the right information go to another activity
//        // else show message to the user with the right information
//
//        var successLogin = FirebaseController.loginUser(this)
//
//        if (successLogin)
//        {
//            // Change screen/activity
//            val intent = Intent(this, GroupsOfToDos::class.java)
//            startActivity(intent)
//        }
//    }

    // Go to create account page
    fun createAccount (v: View)
    {
        // Change screen/activity
        val intent = Intent(this, CreateAccount::class.java)
        startActivity(intent)
    }
}
