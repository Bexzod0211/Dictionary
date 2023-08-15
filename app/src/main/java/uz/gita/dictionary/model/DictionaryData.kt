package uz.gita.dictionary.model

data class DictionaryData(
    val id:Int,
    val english:String,
    val type:String,
    val transcript:String,
    val uzbek:String,
    val countable:String,
    val favourite:Int
)
