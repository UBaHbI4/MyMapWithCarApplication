package softing.ubah4ukdev.mymapapplication.ui.map

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.yandex.mapkit.Animation
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.*
import com.yandex.mapkit.directions.driving.DrivingSession.DrivingRouteListener
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError
import softing.ubah4ukdev.mymapapplication.R
import softing.ubah4ukdev.mymapapplication.domain.models.PositionCar
import softing.ubah4ukdev.mymapapplication.ui.map.base.BaseMapFragment

class MapFragment : BaseMapFragment(), InputListener, DrivingRouteListener {
    private var drivingRouter: DrivingRouter? = null
    private var drivingSession: DrivingSession? = null
    var path: DrivingRoute? = null
    var currentRouter: PolylineMapObject? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.mapView.map.addInputListener(this)

        viewModel.getMoveLiveData()
            .observe(viewLifecycleOwner) { res -> renderCarMove(result = res) }

        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter()

        viewBinding.resetBtn.setOnClickListener {
            reset()
        }
    }

    private fun reset() {
        viewModel.stopCar()

        val requestPoints = ArrayList<RequestPoint>()
        currentRouter?.let {
            mapObjects?.remove(it)
        }

        val startPoint = ScreenPoint(MIN_DISTANCE.toFloat(), MIN_DISTANCE.toFloat())
        myPosition?.let {
            requestPoints.add(
                RequestPoint(
                    viewBinding.mapView.screenToWorld(startPoint),
                    RequestPointType.WAYPOINT,
                    null
                )
            )
        }

        val endPoint = ScreenPoint(
            (viewBinding.mapView.width() - MIN_DISTANCE).toFloat(),
            (viewBinding.mapView.height() - MIN_DISTANCE).toFloat()
        )

        requestPoints.add(
            RequestPoint(
                viewBinding.mapView.screenToWorld(endPoint),
                RequestPointType.WAYPOINT,
                null
            )
        )

        setFinish(viewBinding.mapView.screenToWorld(endPoint))

        drivingSession =
            drivingRouter?.requestRoutes(
                requestPoints,
                DrivingOptions(),
                VehicleOptions(),
                this
            )
    }

    private fun renderCarMove(result: PositionCar?) {
        if (result != null) {
            if (checkFinish(result)) return

            car?.geometry = result.position
            val iconStyle = IconStyle(
                null, RotationType.ROTATE, null, null,
                null, null, null
            )
            car?.setIconStyle(iconStyle)
            car?.direction = result.angle.toFloat()
            viewBinding.distance.text = String.format(DISTANCE_TEMPLATE, result.fullDist)

            val screenPoint = viewBinding.mapView.worldToScreen(result.position)

            checkForNeedMoveCamera(screenPoint, result)
        }
    }

    private fun checkForNeedMoveCamera(screenPoint: ScreenPoint, result: PositionCar) {
        if (
            (screenPoint.x > viewBinding.mapView.width() - MIN_DISTANCE) ||
            (screenPoint.x < MIN_DISTANCE) ||
            (screenPoint.y > viewBinding.mapView.height() - MIN_DISTANCE) ||
            (screenPoint.y < MIN_DISTANCE)
        ) {
            viewBinding.mapView.map.move(
                CameraPosition(
                    result.position,
                    DEF_ZOOM,
                    ZERO_FLOAT,
                    ZERO_FLOAT
                ),
                Animation(Animation.Type.SMOOTH, 0.5f),
                null
            )
        }
    }

    private fun checkFinish(result: PositionCar): Boolean {
        if (result.isFinish) {
            viewBinding.mapView.map.move(
                CameraPosition(
                    result.position,
                    DEF_ZOOM,
                    ZERO_FLOAT,
                    ZERO_FLOAT
                ),
                Animation(Animation.Type.SMOOTH, 0.5f),
                null
            )
            finish?.let {
                it.isVisible = false
            }
            return true
        }
        return false
    }

    override fun onMapTap(map: Map, point: Point) {}

    override fun onMapLongTap(map: Map, point: Point) {
        viewModel.stopCar()
        setFinish(point)
        finish?.isVisible = true
        moveCameraToStart()

        val requestPoints = ArrayList<RequestPoint>()

        currentRouter?.let {
            mapObjects?.remove(it)
        }

        myPosition?.let {
            requestPoints.add(RequestPoint(it, RequestPointType.WAYPOINT, null))
        }
        requestPoints.add(RequestPoint(point, RequestPointType.WAYPOINT, null))

        drivingSession =
            drivingRouter?.requestRoutes(requestPoints, DrivingOptions(), VehicleOptions(), this)
    }

    private fun moveCameraToStart() {
        myPosition?.let {
            viewBinding.mapView.map.move(
                CameraPosition(
                    it,
                    DEF_ZOOM,
                    ZERO_FLOAT,
                    ZERO_FLOAT
                ),
                Animation(Animation.Type.SMOOTH, DELAY),
                null
            )
        }
    }

    private fun setFinish(point: Point) {
        if (finish == null) {
            finish = mapObjects?.addPlacemark(
                point,
                ImageProvider.fromResource(
                    requireContext(),
                    R.drawable.ic_finish
                )
            )
        } else {
            finish?.geometry = point
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding.mapView.map.removeInputListener(this)
    }

    override fun onDrivingRoutes(routes: MutableList<DrivingRoute>) {
        path = routes.firstOrNull()
        path?.let {
            currentRouter = mapObjects?.addPolyline(it.geometry)
        }

        setCar()
        setCarClickListener()
    }

    private fun setCarClickListener() {
        car?.addTapListener { _, _ ->
            path?.geometry?.points?.let {
                viewModel.moveCar(it)
            }
            true
        }
    }

    private fun setCar() {
        path?.geometry?.points?.firstOrNull()?.let {
            if (car == null) {
                car = mapObjects?.addPlacemark(
                    it,
                    ImageProvider.fromResource(
                        requireContext(),
                        R.drawable.my_car
                    )
                )
            } else {
                car?.geometry = it
            }
        }
    }

    override fun onDrivingRoutesError(error: Error) {
        var errorMessage = "Unknown error"
        if (error is RemoteError) {
            errorMessage = "Remote server error"
        } else if (error is NetworkError) {
            errorMessage = "Network error"
        }
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val MIN_DISTANCE = 50
        const val DISTANCE_TEMPLATE = "Расстояние: %.0f м."
        const val DELAY = 1f
    }
}