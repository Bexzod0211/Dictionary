package uz.gita.dictionary.ui.favourites

import android.database.Cursor
import uz.gita.dictionary.repository.AppRepository

class FavouritesModel : FavouritesContract.Model {

    private val repository = AppRepository.getInstance()
    override fun getAllFavouritesWords(): Cursor {
        return repository.getAllFavouriteWords()
    }

    override fun removeFromFavourite(id: Int) {
        repository.removeFromFavourite(id)
    }


}