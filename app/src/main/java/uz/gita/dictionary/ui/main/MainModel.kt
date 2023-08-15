package uz.gita.dictionary.ui.main

import android.database.Cursor
import uz.gita.dictionary.repository.AppRepository

class MainModel :MainContract.Model {
    private val repository = AppRepository.getInstance()
    override fun getAllWords(): Cursor {
        return repository.getAllWords()
    }

    override fun addToFavourite(id: Int) {
        repository.addToFavourite(id)
    }

    override fun removeFromFavourite(id: Int) {
        repository.removeFromFavourite(id)
    }

    override fun getAllWordsByQuery(query: String): Cursor {
        return repository.getAllWordsByQuery(query)
    }

    override fun getAllWordsUzbek(): Cursor {
        return repository.getAllWordsUzbek()
    }

    override fun getAllWordsByQueryUzbek(query: String): Cursor {
        return repository.getAllWordsByQueryUzbek(query)
    }

    override fun saveState(state: Int) {
        repository.saveState(state)
    }

    override fun getState(): Int {
        return repository.getState()
    }


}