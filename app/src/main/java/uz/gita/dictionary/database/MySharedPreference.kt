package uz.gita.dictionary.database

import android.content.Context
import android.content.SharedPreferences

class MySharedPreference private constructor(context: Context){
    private lateinit var pref:SharedPreferences
    private val DB_NAME = "dictionary"
    private val STATE_NAME = "STATE"

    init {
        pref = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE)
    }

    companion object {
        private lateinit var instance:MySharedPreference

        fun init(context: Context){
            if (!::instance.isInitialized){
                instance = MySharedPreference(context)
            }
        }

        fun getInstance():MySharedPreference {
            return instance
        }

    }

    fun saveState(state:Int){
        pref.edit().putInt(STATE_NAME,state).apply()
    }

    fun getState():Int {
        return pref.getInt(STATE_NAME,0)
    }
}