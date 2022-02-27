package softing.ubah4ukdev.mymapapplication.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import softing.ubah4ukdev.mymapapplication.ui.map.MapViewModel

/**
 *   Project: MyMapApplication
 *
 *   Package: softing.ubah4ukdev.mymapapplication.di
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
object Di {

    fun viewModelModule() = module {
        viewModel() {
            MapViewModel()
        }
    }
}