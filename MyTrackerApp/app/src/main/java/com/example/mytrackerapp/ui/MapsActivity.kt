package com.example.mytrackerapp.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mytrackerapp.R
import com.example.mytrackerapp.Utils.LOCATION_PERMISSION_REQUEST
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mytrackerapp.databinding.ActivityMapsBinding
import com.example.mytrackerapp.model.LocationLogging
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocClient: FusedLocationProviderClient         //this class is used to request location updates and get the latest location
//    private lateinit var locationRequest: LocationRequest
//    private lateinit var locationCallback: LocationCallback
//    private lateinit var databaseRef: DatabaseReference

//    private fun getLocationAccess() {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            mMap.isMyLocationEnabled = true
//            getLocationUpdates()
//            startLocationUpdates()
//        } else
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                LOCATION_PERMISSION_REQUEST
//            )
//    }
//
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == LOCATION_PERMISSION_REQUEST) {
//            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
//
//                getLocationUpdates()
//                startLocationUpdates()
//            }
//        } else {
//            Toast.makeText(
//                this,
//                "User has not granted location access permission",
//                Toast.LENGTH_LONG
//            ).show()
//            return
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocClient = LocationServices.getFusedLocationProviderClient(this)

    }

//    private fun getLocationUpdates() {
//        locationRequest = LocationRequest()
//        locationRequest.interval = 30000
//        locationRequest.fastestInterval = 20000
//        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult) {
//                super.onLocationResult(locationResult)
//                if (locationResult.locations.isNotEmpty()) {
//                    val location = locationResult.lastLocation
//                    val locationLogging = LocationLogging(location.latitude, location.longitude)
//                    databaseRef = Firebase.database.reference
//                    databaseRef.child("userLocation").setValue(locationLogging)
//                        .addOnSuccessListener {
//                            Toast.makeText(
//                                applicationContext,
//                                "Location written into database",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                        .addOnFailureListener {
//                            Toast.makeText(
//                                applicationContext,
//                                "Error occurred while writing location to database",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    /**to update only one attribute, use the following statement:
//                    database.child("userLocation".child("Latitude").setValue(location.latitude))*/
//
//                        val latLng = LatLng(location.latitude, location.longitude)
//                        val markerOptions = MarkerOptions().position(latLng).title("You are currently here!").icon(
//                            BitmapDescriptorFactory.fromResource(R.drawable.ic_baseline_person_outline_24))
//                        mMap.addMarker(markerOptions)
//                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
//
//
//                }
//            }
//        }
//    }
//
//    private fun startLocationUpdates() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            getLocationAccess()
//        }
//        fusedLocClient.requestLocationUpdates(
//            locationRequest,
//            locationCallback,
//            null
//        )
//    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    // This method is called when we need to initialize the map. By default, it creates a marker with coordinates near Sydney and adds it to the map.
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap    //map initialization
//        getLocationAccess()

        // Add a marker in Sydney and move the camera
//        val benin = LatLng(6.339185, 5.617447)
//        mMap.addMarker(MarkerOptions().position(benin).title("Marker in Benin"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(benin))

    }

    private fun setUpLocClient() {
        fusedLocClient = LocationServices.getFusedLocationProviderClient(this)
    }

    // prompt the user to grant/deny access
    private fun requestLocPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), //permission in the manifest
            REQUEST_LOCATION)
    }

    //ask permission from user
    private fun getCurrentLocation() {
        // Check if the ACCESS_FINE_LOCATION permission was granted before requesting a location
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED )
            {
            requestLocPermissions()
        }else {
        fusedLocClient.lastLocation.addOnCompleteListener {
            // lastLocation is a task running in the background
            val location = it.result //obtain location
            //reference to the database
            database = FirebaseDatabase.getInstance()
            ref = database.getReference("test")
            if (location != null) {

                val zoom = 15f          //1-world. 2-landmass/continent. 10-city. 15-streets. 20-buildings 8-country.
                val latLng = LatLng(location.latitude, location.longitude)
                // create a marker at the exact location
                mMap.addMarker(                                        // icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                    MarkerOptions().position(latLng)
                        .title("You are currently here!").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon))
                )
                // create an object that will specify how the camera will be updated
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom)
                //Save the location data to the database
                ref.setValue(location)

            }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //check if the request code matches the REQUEST_LOCATION
        if (requestCode == REQUEST_LOCATION) {
            //check if grantResults contains PERMISSION_GRANTED.If it does, call getCurrentLocation()
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                getCurrentLocation()
            } else {
                //if it doesn't log an error message
                Log.e(TAG, "Location permission has been denied")
            }
        }
    }
}