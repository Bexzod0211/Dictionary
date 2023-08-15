package uz.gita.dictionary.ui.favourites

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import uz.gita.dictionary.R
import uz.gita.dictionary.databinding.ActivityFavouritesBinding
import uz.gita.dictionary.gone
import uz.gita.dictionary.ui.adapter.FavouritesAdapter
import uz.gita.dictionary.ui.dialogs.DescriptionDialog
import uz.gita.dictionary.visible

class FavouritesActivity : AppCompatActivity(),FavouritesContract.View {
    private var _binding:ActivityFavouritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter:FavouritesAdapter
    private lateinit var presenter:FavouritesContract.Presenter
    private lateinit var cursor:Cursor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavouritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = FavouritesPresenter(this)
        adapter = FavouritesAdapter(cursor)
        binding.apply {
            recyclerFavourites.adapter = adapter
            recyclerFavourites.layoutManager = LinearLayoutManager(this@FavouritesActivity)
        }

        adapter.setOnItemFavouriteCButtonClickListener {
            presenter.btnFavouriteClickedById(it)
        }

        adapter.setOnItemClickListener {
            presenter.itemClicked(it)
        }

        attachClickListeners()

    }

    private fun attachClickListeners(){
        binding.btnBack.setOnClickListener {
            presenter.btnBackClicked()
        }
    }

    override fun setAllFavouriteWords(cursor: Cursor) {
        this.cursor = cursor
    }

    override fun updateCursor(cursor: Cursor) {
        adapter.updateCursor(cursor)
    }

    override fun finishActivity() {
        finish()
    }

    override fun showDescriptionDialog(cursor: Cursor) {
        val dialog = DescriptionDialog(this,cursor,0)
        dialog.create()
        dialog.show()
    }

    override fun setPlaceholderVisible() {
        binding.txtPlaceholder.visible()
    }

    override fun setPlaceholderGone() {
        binding.txtPlaceholder.gone()
    }


}