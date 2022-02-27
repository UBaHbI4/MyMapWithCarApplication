package softing.ubah4ukdev.mymapapplication.ui.map

import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import softing.ubah4ukdev.mymapapplication.domain.models.PositionCar
import softing.ubah4ukdev.mymapapplication.ui.map.base.BaseMapViewModel
import softing.ubah4ukdev.mymapapplication.utils.extensions.calculateAngle
import softing.ubah4ukdev.mymapapplication.utils.extensions.calculateDistance
import kotlin.math.roundToInt
import kotlin.properties.Delegates

class MapViewModel(

) : BaseMapViewModel() {

    override fun handleError(throwable: Throwable) {}

    fun stopCar() {
        viewModelScopeCoroutine.launch {
            viewModelScopeCoroutine.coroutineContext.cancelChildren()
        }
    }

    fun moveCar(points: List<Point>) {
        viewModelScopeCoroutine.launch {
            move(points)
        }
    }

    private fun move(points: List<Point>): Job =
        viewModelScopeCoroutine.launch {
            startMoveCar(points)
        }

    private suspend fun startMoveCar(points: List<Point>) {
        var isFirstPoint = true
        var previousPoint = Point()
        var angle: Double = ZERO_VALUE
        var distance: Double = ZERO_VALUE
        var fullDist: Double = ZERO_VALUE
        points.forEach { currentPoint ->
            if (isFirstPoint.not()) {
                delay(DELAY_MOVE)
                angle = calculateAngle(previousPoint, currentPoint)
                distance = calculateDistance(previousPoint, currentPoint)
                fullDist += distance
                if (distance > MIN_DISTANCE) {
                    getMorePoints(currentPoint, previousPoint, distance, angle, fullDist)
                } else {
                    getMoveLiveData().postValue(
                        PositionCar(currentPoint, angle, distance, fullDist)
                    )
                }
            }
            previousPoint = currentPoint
            isFirstPoint = false
        }
        delay(DELAY_MOVE)
        getMoveLiveData().postValue(
            PositionCar(previousPoint, ZERO_VALUE, ZERO_VALUE, ZERO_VALUE, true)
        )
    }

    private suspend fun getMorePoints(
        currentPoint: Point,
        previousPoint: Point,
        distance: Double,
        angle: Double,
        fullDist: Double
    ) {
        val dx = currentPoint.latitude - previousPoint.latitude
        val dy = currentPoint.longitude - previousPoint.longitude
        var latitude by Delegates.notNull<Double>()
        var longitude by Delegates.notNull<Double>()
        for (i in 0..distance.roundToInt()) {
            latitude = previousPoint.latitude.plus((dx * i / distance))
            longitude = previousPoint.longitude.plus((dy * i / distance))
            delay(DELAY_MOVE)
            getMoveLiveData().postValue(
                PositionCar(Point(latitude, longitude), angle, distance, fullDist)
            )
        }
    }

    companion object {
        const val ZERO_VALUE = 0.0
        const val DELAY_MOVE = 20L
        const val MIN_DISTANCE = 1
    }
}