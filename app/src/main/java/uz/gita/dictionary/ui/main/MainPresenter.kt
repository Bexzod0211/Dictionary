package uz.gita.dictionary.ui.main

import android.database.Cursor

class MainPresenter(private val view:MainContract.View) :MainContract.Presenter {
    private val model:MainContract.Model = MainModel()
    private var onChangeState = model.getState()

    init {
        if (onChangeState == 0) {
            view.setAllWords(model.getAllWords())
            view.changeUzToEn()
        }else {
            view.setAllWords(model.getAllWordsUzbek())
            view.changeEnToUz()
        }
    }

    override fun setFavouriteStateToId(id: Int, favState: Int) {
        if (favState == 0){
            model.removeFromFavourite(id)
        }else {
            model.addToFavourite(id)
        }
        view.updateCursor(model.getAllWords())
    }

    override fun btnFavouritesClicked() {
        view.openFavouritesActivity()
    }

    override fun onQueryChanged(query: String) {
        if (onChangeState == 0){
            view.updateCursor(model.getAllWordsByQuery(query))
            if (model.getAllWordsByQuery(query).count == 0){
                view.setPlaceholderVisible()
            }else{
                view.setPlaceholderGone()
            }
        }
        else {
            view.updateCursor(model.getAllWordsByQueryUzbek(query))
            if (model.getAllWordsByQueryUzbek(query).count == 0){
                view.setPlaceholderVisible()
            }else{
                view.setPlaceholderGone()
            }
        }
    }

    override fun onResume() {
        view.setUpdatesOnResume(model.getAllWords())
    }

    override fun btnChangeClicked() {
        if (onChangeState == 0){
            view.changeEnToUz()
            view.updateCursorByLang(model.getAllWordsUzbek())
            onChangeState = 1
        }else {
            view.changeUzToEn()
            view.updateCursorByLang(model.getAllWords())
            onChangeState = 0
        }
    }

    override fun btnMoreClicked() {
        view.showPopupMenu()
    }

    override fun itemClicked(cursor: Cursor) {
        view.showDescriptionDialog(cursor,onChangeState)
    }

    override fun saveState() {
        model.saveState(onChangeState)
    }

    override fun btnAboutClicked() {
        view.showAboutDialog()
    }

    override fun btnShareClicked() {
        view.showShareActions()
    }
}