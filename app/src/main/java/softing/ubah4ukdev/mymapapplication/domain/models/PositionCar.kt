package softing.ubah4ukdev.mymapapplication.domain.models

import com.yandex.mapkit.geometry.Point

/**
 *   Project: MyMapApplication
 *
 *   Package: softing.ubah4ukdev.mymapapplication.domain.models
 *
 *   Created by Ivan Sheynmaer
 *
 *   Description:
 *
 *
 *   2022.02.25
 *
 *   v1.0
 */
data class PositionCar(
    val position: Point,
    val angle: Double,
    val dist: Double,
    val fullDist: Double,
    val isFinish: Boolean = false
)