package uz.gita.dictionary.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import uz.gita.dictionary.databinding.DialogAboutBinding

class InfoDialog(context: Context) : AlertDialog(context) {
    private lateinit var binding:DialogAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}