package uz.gita.dictionary.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.gita.dictionary.utils.AppConstants
import java.io.FileOutputStream
import java.io.InputStream

class AppDatabase private constructor(private val context: Context) : SQLiteOpenHelper(context, AppConstants.DB_NAME, null, AppConstants.DB_VERSION) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var instance: AppDatabase

        fun init(context: Context) {
            if (!::instance.isInitialized) {
                instance = AppDatabase(context)
            }
        }

        fun getInstance(): AppDatabase {
            return instance
        }
    }

    private var database:SQLiteDatabase

    init {
        val file =context.getDatabasePath("dictionary.db")
        if (!file.exists()){
            copyToLocal()
        }
        database =SQLiteDatabase.openDatabase(file.absolutePath,null,SQLiteDatabase.OPEN_READWRITE)
    }

    private fun copyToLocal() {
        val inputStream: InputStream = context.assets.open(AppConstants.FILE_NAME)
        val file = context.getDatabasePath("dictionary.db")
        val fileOutPutStream = FileOutputStream(file)

        try {
            val byte = ByteArray(1024)
            var length = 0
            while (inputStream.read(byte).also { length = it } > 0) {
                fileOutPutStream.write(byte,0,length)
            }
            fileOutPutStream.flush()
        }
        catch (e:Exception){
            file.delete()
        }
        finally {
            inputStream.close()
            fileOutPutStream.close()
        }

    }

    override fun onCreate(p0: SQLiteDatabase?) {

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun getAllWords():Cursor {
        return database.rawQuery("SELECT * FROM dictionary",null)
    }

    fun search(query:String):Cursor {
        if (query.contains("\"")){
            return database.rawQuery("""
            SELECT * FROM dictionary WHERE english like "" 
        """.trimIndent(),null )
        }
        return database.rawQuery("""
            SELECT * FROM dictionary WHERE english like "$query%" or "%$query" or "%$query%"
        """.trimIndent(),null )
    }

    fun addToFavourite(id:Int){
        database.execSQL("""
            Update dictionary set favourite = 1 where id = $id
        """.trimIndent())
    }

    fun removeFromFavourite(id:Int){
        database.execSQL("Update dictionary set favourite = 0 where id = $id")
    }

    fun getAllFavourites():Cursor{
        return database.rawQuery("SELECT * FROM dictionary WHERE favourite = 1",null)
    }

    fun getAllWordsUzbek():Cursor{
        return database.rawQuery("""
            SELECT dictionary.id,dictionary.uzbek as english,  dictionary.transcript, dictionary.type,dictionary.english as uzbek ,
                dictionary.countable,dictionary.favourite FROM dictionary ORDER by english ASC
        """.trimIndent(),null)
    }

    fun searchUzbek(query: String):Cursor{
        if (query.contains("\"")){
            return database.rawQuery("""
            SELECT * FROM dictionary WHERE english like "" 
        """.trimIndent(),null )
        }
        return database.rawQuery("""SELECT dictionary.id,dictionary.uzbek as english,  dictionary.transcript, dictionary.type,dictionary.english as uzbek,
                dictionary.countable,dictionary.favourite FROM dictionary WHERE uzbek like "%$query%" or "%$query" or "%$query%" ORDER by english ASC""",null)
    }
}