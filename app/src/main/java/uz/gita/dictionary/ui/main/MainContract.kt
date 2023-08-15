package uz.gita.dictionary.ui.main

import android.database.Cursor

interface MainContract {
    interface Model {
        fun getAllWords():Cursor
        fun addToFavourite(id:Int)
        fun removeFromFavourite(id:Int)
        fun getAllWordsByQuery(query:String):Cursor
        fun getAllWordsUzbek():Cursor
        fun getAllWordsByQueryUzbek(query: String):Cursor
        fun saveState(state:Int)
        fun getState():Int
    }

    interface View {
        fun setAllWords(cursor: Cursor)
        fun updateCursor(cursor: Cursor)
        fun openFavouritesActivity()
        fun setUpdatesOnResume(cursor: Cursor)
        fun changeEnToUz()
        fun changeUzToEn()
        fun updateCursorByLang(cursor: Cursor)
        fun showPopupMenu()
        fun showDescriptionDialog(cursor: Cursor,state:Int)
        fun setPlaceholderVisible()
        fun setPlaceholderGone()
        fun showAboutDialog()
        fun showShareActions()
    }

    interface Presenter {
        fun setFavouriteStateToId(id:Int,favState:Int)
        fun btnFavouritesClicked()
        fun onQueryChanged(query: String)
        fun onResume()
        fun btnChangeClicked()
        fun btnMoreClicked()
        fun itemClicked(cursor: Cursor)
        fun saveState()
        fun btnAboutClicked()
        fun btnShareClicked()
    }
}