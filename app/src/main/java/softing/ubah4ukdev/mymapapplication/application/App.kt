package softing.ubah4ukdev.mymapapplication.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import softing.ubah4ukdev.mymapapplication.di.Di

/**
 *   Project: MyMapApplication
 *
 *   Package: softing.ubah4ukdev.mymapapplication.application
 *
 *   Created by Ivan Sheynmaer
 *
 *   Description:
 *
 *
 *   2022.02.19
 *
 *   v1.0
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    Di.viewModelModule()
                )
            )
        }
    }
}