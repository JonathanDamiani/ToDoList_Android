package com.deanvelopment.tasko

import android.content.Context
import com.google.firebase.auth.FirebaseAuth

class FirebaseController
{
    companion object
    {
        lateinit var auth: FirebaseAuth

        var EMAIL = ""
        var PASS = ""

        var ISLOGGED = false

        var userUUID = auth.currentUser!!.uid

        fun registerUser (activity : CreateAccount)
        {
            auth.createUserWithEmailAndPassword(EMAIL, PASS)
                .addOnCompleteListener(activity) { task ->
                    if(task.isSuccessful)
                    {
                        Tools.toastIt(activity, "Success")
                    }
                    else
                    {
                        Tools.toastIt(activity, task.exception.toString())
                    }
                }
        }

        fun loginUser(activity : Login) : Boolean
        {
            auth.signInWithEmailAndPassword(EMAIL, PASS)
                .addOnCompleteListener(activity) { task ->
                    ISLOGGED = task.isSuccessful
                    if (!task.isSuccessful)
                    {
                        Tools.toastIt(activity, task.exception.toString())
                    }
                }

            userUUID = auth.currentUser!!.uid

            return ISLOGGED
        }

        fun logoutUser()
        {
            auth.signOut()
        }
    }
}