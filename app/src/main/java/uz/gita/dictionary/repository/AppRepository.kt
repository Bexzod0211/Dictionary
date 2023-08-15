package uz.gita.dictionary.repository

import android.database.Cursor
import uz.gita.dictionary.database.AppDatabase
import uz.gita.dictionary.database.MySharedPreference
import uz.gita.dictionary.model.DictionaryData

class AppRepository {
    private val database = AppDatabase.getInstance()
    private val sharedPref = MySharedPreference.getInstance()
    companion object {
        private lateinit var instance:AppRepository

        fun init(){
            if (!::instance.isInitialized){
                instance = AppRepository()
            }
        }

        fun getInstance():AppRepository {
            return instance
        }
    }

    fun getAllWords():Cursor {
        return database.getAllWords()
    }

    fun getAllWordsByQuery(word:String):Cursor{
        return database.search(word)
    }

    fun addToFavourite(id:Int){
        database.addToFavourite(id)
    }

    fun removeFromFavourite(id:Int){
        database.removeFromFavourite(id)
    }

    fun getAllFavouriteWords():Cursor {
        return database.getAllFavourites()
    }

    fun getAllWordsUzbek():Cursor {
        return database.getAllWordsUzbek()
    }

    fun getAllWordsByQueryUzbek(query:String):Cursor {
        return database.searchUzbek(query)
    }

    fun saveState(state:Int){
        sharedPref.saveState(state)
    }

    fun getState():Int {
        return sharedPref.getState()
    }
}