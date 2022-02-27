package softing.ubah4ukdev.mymapapplication.utils.extensions

import com.yandex.mapkit.geometry.Point
import kotlin.math.*

/**
 *   Project: MyMapApplication
 *
 *   Package: softing.ubah4ukdev.mymapapplication.utils.extensions
 *
 *   Created by Ivan Sheynmaer
 *
 *   Description:
 *
 *
 *   2022.02.27
 *
 *   v1.0
 */

fun calculateDistance(startPoint: Point, endPoint: Point): Double {
    try {
        //косинусы и синусы широт и разницы долгот
        val cosLatitudeFirst = cos(latitudeInRadian(startPoint))
        val cosLatitudeSecond = cos(latitudeInRadian(endPoint))
        val sinLatitudeFirst = sin(latitudeInRadian(startPoint))
        val sinLatitudeSecond = sin(latitudeInRadian(endPoint))
        val delta = longitudeInRadian(endPoint) - longitudeInRadian(startPoint)
        val cosDelta = cos(delta)
        val sinDelta = sin(delta)

        //вычисления длины большого круга
        val y = sqrt(
            (cosLatitudeSecond * sinDelta).pow(2)
                    + (cosLatitudeFirst * sinLatitudeSecond -
                    sinLatitudeFirst * cosLatitudeSecond * cosDelta).pow(2)
        )
        val x = sinLatitudeFirst * sinLatitudeSecond +
                cosLatitudeFirst * cosLatitudeSecond * cosDelta

        val arcTan = atan2(y, x)
        val distance = arcTan * radiusEarth
        if (distance.isNaN()) return 0.0
        return distance
    } catch (err: Exception) {
        return 0.0
    }
}

fun calculateAngle(startPoint: Point, endPoint: Point): Double {
    try {
        //косинусы и синусы широт и разницы долгот
        val cosLatitudeFirst = cos(latitudeInRadian(startPoint))
        val cosLatitudeSecond = cos(latitudeInRadian(endPoint))
        val sinLatitudeFirst = sin(latitudeInRadian(startPoint))
        val sinLatitudeSecond = sin(latitudeInRadian(endPoint))
        val delta = longitudeInRadian(endPoint) - longitudeInRadian(startPoint)
        val cosDelta = cos(delta)
        val sinDelta = sin(delta)

        //вычисление начального азимута
        val x = (cosLatitudeFirst * sinLatitudeSecond) -
                (sinLatitudeFirst * cosLatitudeSecond * cosDelta)
        val y = sinDelta * cosLatitudeSecond
        var z = Math.toDegrees(atan(-y / x))

        if (x < 0) z += angle

        var z2 = (z + angle) % 360 - angle
        z2 = -Math.toRadians(z2)
        val angleRad = z2 - ((2 * pi) * floor((z2 / (2 * pi))))
        val angleDeg = (angleRad * angle) / pi
        if (angleDeg.isNaN()) return 0.0
        return angleDeg
    } catch (err: Exception) {
        return 0.0
    }
}

private fun longitudeInRadian(startPoint: Point) = startPoint.longitude * pi / angle

private fun latitudeInRadian(startPoint: Point) = startPoint.latitude * pi / angle

const val angle = 180
const val pi = Math.PI
const val radiusEarth = 6372795