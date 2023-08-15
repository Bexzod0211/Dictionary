package uz.gita.dictionary.ui.favourites

import android.database.Cursor

class FavouritesPresenter(private val view:FavouritesContract.View):FavouritesContract.Presenter {
    private val model:FavouritesContract.Model = FavouritesModel()
    override fun btnFavouriteClickedById(id: Int) {
        model.removeFromFavourite(id)
        val cursor = model.getAllFavouritesWords()
        view.updateCursor(cursor)
        if (cursor.count == 0){
            view.setPlaceholderVisible()
        }else{
            view.setPlaceholderGone()
        }
    }

    override fun btnBackClicked() {
        view.finishActivity()
    }

    override fun itemClicked(cursor: Cursor) {
        view.showDescriptionDialog(cursor)
    }

    init {
        val cursor = model.getAllFavouritesWords()
        view.setAllFavouriteWords(cursor)
        if (cursor.count == 0){
            view.setPlaceholderVisible()
        }else{
            view.setPlaceholderGone()
        }

    }


}