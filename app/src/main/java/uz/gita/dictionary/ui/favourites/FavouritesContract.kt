package uz.gita.dictionary.ui.favourites

import android.database.Cursor

interface FavouritesContract {

    interface Model {
        fun getAllFavouritesWords():Cursor
        fun removeFromFavourite(id:Int)
    }

    interface View {
        fun setAllFavouriteWords(cursor: Cursor)
        fun updateCursor(cursor:Cursor)
        fun finishActivity()
        fun showDescriptionDialog(cursor: Cursor)
        fun setPlaceholderVisible()
        fun setPlaceholderGone()
    }

    interface Presenter {
        fun btnFavouriteClickedById(id:Int)
        fun btnBackClicked()
        fun itemClicked(cursor: Cursor)
    }
}