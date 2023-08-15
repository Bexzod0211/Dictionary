package uz.gita.dictionary.app

import android.app.Application
import uz.gita.dictionary.database.AppDatabase
import uz.gita.dictionary.database.MySharedPreference
import uz.gita.dictionary.repository.AppRepository

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
        MySharedPreference.init(this)
        AppRepository.init()
    }
}