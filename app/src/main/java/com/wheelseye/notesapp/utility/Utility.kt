package com.wheelseye.notesapp.utility

class Utility {

    companion object {
        public fun isEmailValid(email : String) : Boolean{
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

}