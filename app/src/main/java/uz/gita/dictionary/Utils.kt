package uz.gita.dictionary

import android.util.Log
import android.view.View

enum class Utils {
    EN_UZ, UZ_EN
}

fun <T> T.myLog(message:String,tag:String="TTT"){
    Log.d(tag,message)
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}