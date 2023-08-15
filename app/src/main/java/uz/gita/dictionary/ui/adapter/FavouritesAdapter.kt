package uz.gita.dictionary.ui.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.dictionary.R
import uz.gita.dictionary.model.DictionaryData

class FavouritesAdapter(private var cursor:Cursor) : RecyclerView.Adapter<FavouritesAdapter.Holder>() {

    private lateinit var onItemFavButtonClickListener:(Int)->Unit
    private var isValid = false
    private lateinit var onItemClickListener:(Cursor)->Unit
    private val notifyingDataSetObserver = object : DataSetObserver() {
        override fun onChanged() {
            super.onChanged()
            isValid = true
        }

        override fun onInvalidated() {
            super.onInvalidated()
            isValid = false
        }
    }

    init {
        cursor.registerDataSetObserver(notifyingDataSetObserver)
        isValid = true
    }


    fun updateCursor(newCursor: Cursor) {
        isValid = false
        cursor.unregisterDataSetObserver(notifyingDataSetObserver)
        cursor.close()

        newCursor.registerDataSetObserver(notifyingDataSetObserver)
        cursor = newCursor
        isValid = true
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false))

    override fun getItemCount(): Int = cursor.count

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    @SuppressLint("Range")
    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtWord: TextView = view.findViewById(R.id.txt_word)
        private val txtTranslate: TextView = view.findViewById(R.id.txt_translate)
        private val txtType: TextView = view.findViewById(R.id.txt_type)
        private val imgFavourite: ImageView = view.findViewById(R.id.btn_fav)

        init {
            imgFavourite.setOnClickListener {
                cursor.moveToPosition(adapterPosition)
                onItemFavButtonClickListener.invoke(cursor.getInt(cursor.getColumnIndex("id")))
            }
            itemView.setOnClickListener {
            cursor.moveToPosition(adapterPosition)
                onItemClickListener.invoke(cursor)
            }
        }

        @SuppressLint("Range")
        fun bind(position: Int) {
            cursor.moveToPosition(position)
            cursor.apply {
                txtWord.text = cursor.getString(cursor.getColumnIndex("english"))
                txtTranslate.text = cursor.getString(cursor.getColumnIndex("uzbek"))
                txtType.text = "(${cursor.getString(cursor.getColumnIndex("type"))}) "

                if (cursor.getInt(cursor.getColumnIndex("favourite")) == 0) {
                    imgFavourite.setImageResource(R.drawable.ic_un_favourite)
                } else  {
                    imgFavourite.setImageResource(R.drawable.ic_favourite)
                }
            }
        }
    }

  /*  @SuppressLint("Range")
    private fun cursorToList() {
        if (cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val english = cursor.getString(cursor.getColumnIndex("english"))
                val type = cursor.getString(cursor.getColumnIndex("type"))
                val transcript = cursor.getString(cursor.getColumnIndex("transcript"))
                val uzbek = cursor.getString(cursor.getColumnIndex("uzbek"))
                val countable = cursor.getString(cursor.getColumnIndex("countable"))
                val favourite = cursor.getInt(cursor.getColumnIndex("favourite"))

                val data = DictionaryData(id,english,type,transcript,uzbek,countable,favourite)
                list.add(data)
            } while (cursor.moveToNext())

        }
    }*/

    fun setOnItemFavouriteCButtonClickListener(block:(Int)->Unit){
        onItemFavButtonClickListener = block
    }

    fun setOnItemClickListener(block: (Cursor) -> Unit){
        onItemClickListener = block
    }
}
