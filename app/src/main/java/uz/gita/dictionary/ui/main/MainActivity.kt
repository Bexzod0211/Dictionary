package uz.gita.dictionary.ui.main

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import uz.gita.dictionary.BuildConfig
import uz.gita.dictionary.R
import uz.gita.dictionary.databinding.ActivityMainBinding
import uz.gita.dictionary.gone
import uz.gita.dictionary.ui.adapter.DictionaryCursorAdapter
import uz.gita.dictionary.ui.dialogs.DescriptionDialog
import uz.gita.dictionary.ui.favourites.FavouritesActivity
import uz.gita.dictionary.visible

class MainActivity : AppCompatActivity(), MainContract.View {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DictionaryCursorAdapter
    private lateinit var presenter: MainContract.Presenter
    private lateinit var cursor: Cursor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainPresenter(this)
        adapter = DictionaryCursorAdapter(cursor, resources)
        binding.apply {
            recyclerMain.adapter = adapter
            recyclerMain.layoutManager = LinearLayoutManager(this@MainActivity)
        }

        adapter.setOnFavouriteClickListener { id, favState ->
            if (favState == 0) {
                presenter.setFavouriteStateToId(id, 1)
            } else if (favState == 1) {
                presenter.setFavouriteStateToId(id, 0)
            }
        }

        adapter.setOnItemClickListener {
            presenter.itemClicked(it)
        }

        binding.apply {
            edtSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(query: CharSequence, p1: Int, p2: Int, p3: Int) {
                    adapter.setSpanString(query.toString())
                    presenter.onQueryChanged(query.toString())
                    adapter.notifyDataSetChanged()
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
        }


        attachClickListeners()
    }

    private fun attachClickListeners() {
        binding.apply {
            btnMore.setOnClickListener {
                presenter.btnMoreClicked()
            }
            btnChange.setOnClickListener {
                presenter.btnChangeClicked()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setAllWords(cursor: Cursor) {
        this.cursor = cursor
    }

    override fun updateCursor(cursor: Cursor) {
        adapter.updateCursor(cursor)
    }

    override fun openFavouritesActivity() {
        val intent = Intent(this, FavouritesActivity::class.java)
        startActivity(intent)
    }

    override fun setUpdatesOnResume(cursor: Cursor) {
        adapter.updateCursor(cursor)
        adapter.notifyDataSetChanged()
        binding.edtSearch.setText("")
    }

    override fun changeEnToUz() {
        binding.apply {
            txtLeft.text = "Uzbek"
            txtRight.text = "English"
        }


    }

    override fun changeUzToEn() {
        binding.apply {
            txtLeft.text = "English"
            txtRight.text = "Uzbek"
        }
    }

    override fun updateCursorByLang(cursor: Cursor) {
        adapter.updateCursor(cursor)
        adapter.notifyDataSetChanged()
    }

    override fun showPopupMenu() {
        val popUpMenu = PopupMenu(this, binding.btnMore)
        popUpMenu.menuInflater.inflate(R.menu.popup_menu, popUpMenu.menu)
        popUpMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_favs -> {
                    presenter.btnFavouritesClicked()
                }
                R.id.menu_about -> {
                    presenter.btnAboutClicked()
                }
                R.id.menu_share ->{
                    presenter.btnShareClicked()
                }
            }
            true
        }
        popUpMenu.show()
    }

    override fun showDescriptionDialog(cursor: Cursor, state: Int) {
        val dialog = DescriptionDialog(this, cursor, state)
        dialog.create()
        dialog.show()
    }

    override fun setPlaceholderVisible() {
        binding.txtPlaceholder.visible()
    }

    override fun setPlaceholderGone() {
        binding.txtPlaceholder.gone()
    }

    override fun showAboutDialog() {

    }

    override fun showShareActions() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,R.string.app_name)
        var shareMessage = "Download Dictionary app here, there are over 15,000 words\nhttps://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
        shareIntent.putExtra(Intent.EXTRA_TEXT,shareMessage)
        startActivity(Intent.createChooser(shareIntent,"Share via"))
    }

    override fun onPause() {
        super.onPause()
        presenter.saveState()
    }
}