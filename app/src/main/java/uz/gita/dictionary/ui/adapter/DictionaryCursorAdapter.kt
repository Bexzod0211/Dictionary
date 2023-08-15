package uz.gita.dictionary.ui.adapter

import android.annotation.SuppressLint
import android.content.res.Resources
import android.database.Cursor
import android.database.DataSetObserver
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.dictionary.R

class DictionaryCursorAdapter(private var cursor: Cursor,private val resources:Resources) : RecyclerView.Adapter<DictionaryCursorAdapter.Holder>() {

    private var isValid = false
    private lateinit var listener: (Int, Int) -> Unit
    private lateinit var onItemClickListener:(Cursor)->Unit
    private var spanString = ""
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false))
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

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
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val favouriteState = cursor.getInt(cursor.getColumnIndex("favourite"))
                if (cursor.getInt(cursor.getColumnIndex("favourite")) == 0) {
                    imgFavourite.setImageResource(R.drawable.ic_favourite)
                } else {
                    imgFavourite.setImageResource(R.drawable.ic_un_favourite)
                }

                listener.invoke(id, favouriteState)
            }

            itemView.setOnClickListener {
                cursor.moveToPosition(adapterPosition)
                onItemClickListener.invoke(cursor)
            }
        }

        @SuppressLint("Range")
        fun bind(position: Int) {
            cursor.moveToPosition(position)

            val english = SpannableString(cursor.getString(cursor.getColumnIndex("english")))
            var index = english.indexOf(spanString)
            if (index != -1)
                 english.setSpan(ForegroundColorSpan(resources.getColor(R.color.color_app)),index,index+spanString.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            txtWord.text = english
            txtTranslate.text = cursor.getString(cursor.getColumnIndex("uzbek"))
            txtType.text = "(${cursor.getString(cursor.getColumnIndex("type"))}) "
            if (cursor.getInt(cursor.getColumnIndex("favourite")) == 0) {
                imgFavourite.setImageResource(R.drawable.ic_un_favourite)
            } else if (cursor.getInt(cursor.getColumnIndex("favourite")) == 1) {
                imgFavourite.setImageResource(R.drawable.ic_favourite)
            }
        }
    }

    fun setSpanString(str:String){
        spanString = str
    }

    fun setOnFavouriteClickListener(block: (Int, Int) -> Unit) {
        listener = block
    }

    fun setOnItemClickListener(block:(Cursor)->Unit){
        onItemClickListener = block
    }

}
