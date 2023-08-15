package uz.gita.dictionary.ui.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.speech.tts.TextToSpeech
import uz.gita.dictionary.databinding.WordDescriptionDialogBinding
import java.util.*

class DescriptionDialog(context: Context,private val cursor: Cursor,private val state:Int) :AlertDialog(context){
    private var _binding:WordDescriptionDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var  t:TextToSpeech
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = WordDescriptionDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        t = TextToSpeech(context){
            if (it != TextToSpeech.ERROR){
                t.language = Locale.US
            }
        }

        binding.apply {
            txtWord.text = "${cursor.getString(cursor.getColumnIndex("english"))}  "
            txtTranslation.text = cursor.getString(cursor.getColumnIndex("uzbek"))
            txtType.text = cursor.getString(cursor.getColumnIndex("type"))
            txtTranscript.text = "[ ${cursor.getString(cursor.getColumnIndex("transcript"))} ]"

        }

        attachClickListeners()

    }

    private fun attachClickListeners(){

        binding.apply {
            var toSpeak = if (state == 0) txtWord.text.toString() else txtTranslation.text.toString()
            btnOk.setOnClickListener {
                cancel()
            }
            btnSpeech.setOnClickListener {
                 t.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null)
            }

        }
    }



}