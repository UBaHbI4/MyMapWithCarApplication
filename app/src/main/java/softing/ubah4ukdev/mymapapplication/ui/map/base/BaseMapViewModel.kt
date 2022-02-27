package softing.ubah4ukdev.mymapapplication.ui.map.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import softing.ubah4ukdev.mymapapplication.domain.models.PositionCar

/**
 *   Project: MyMapApplication
 *
 *   Package: softing.ubah4ukdev.mymapapplication.ui.map.base
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
abstract class BaseMapViewModel : ViewModel() {
    private val moveLiveData = MutableLiveData<PositionCar>()

    protected val viewModelScopeCoroutine = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    abstract fun handleError(throwable: Throwable): Any

    fun getMoveLiveData() = moveLiveData


    override fun onCleared() {
        super.onCleared()
        viewModelScopeCoroutine
            .coroutineContext
            .cancel()
    }
}