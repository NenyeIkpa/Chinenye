package com.example.mapsactivity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.mapsactivity.databinding.ActivityMapsBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private var marker1 : Marker? = null
    private var marker2 : Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true

        marker1 = mMap.addMarker(MarkerOptions().position(LatLng(0.0,0.0)).title("I am here!"))
        marker2 = mMap.addMarker(MarkerOptions().position(LatLng(0.0,0.0)).title("Kufre is here!"))

          //  .icon(BitmapDescriptorFactory.fromResource(R.drawable.kufre)))

        getCurrentLocation()
        getPartnerLocation()


        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(6.3532, 6.5579)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Your favourite city!"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f))


        /**enables the my-location layer which draws a light blue dot on the user's location.
         * it also adds a button to the map that when tapped, centres the map on the user's location
         */

    }

    // prompt the user to grant/deny access
    private fun requestLocPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), //permission in the manifest
            REQUEST_LOCATION)
    }
    private fun getCurrentLocation() {
        // Check if the ACCESS_FINE_LOCATION permission was granted before requesting a location
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {

            // call requestLocPermissions() if permission isn't granted
            requestLocPermissions()
        } else {

            locationRequest = LocationRequest.create()
            locationRequest.interval = 30000
            locationRequest.fastestInterval = 20000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    getCurrentLocation()
                }
            }

            // Request location update
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, mainLooper)


                    fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                        // lastLocation is a task running in the background
                        val location = it.result //obtain location

                        //reference to the database
                        databaseRef = database.getReference("Chinenye")

                        if (location != null) {

                            val latLng = LatLng(location.latitude, location.longitude)
                            // create a marker at the exact location
                           // marker1?.position = latLng
//                            mMap.addMarker(
//                                MarkerOptions().position(latLng)
//                                    .title("You are currently here!")
//                            )
                            // create an object that will specify how the camera will be updated
                            val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)

                            mMap.moveCamera(update)
                            //Save the location data to the database
                            databaseRef.setValue(latLng)
                        } else {
                            // if location is null , log an error message
                            Log.d("loc", "No location found")
                        }
                    }
                }
            }


        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            //check if the request code matches the REQUEST_LOCATION
            if (requestCode == REQUEST_LOCATION) {
                //check if grantResults contains PERMISSION_GRANTED.If it does, call getCurrentLocation()
                if (grantResults.size == 1 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    getCurrentLocation()
                } else {
                    //if it doesn't log an error message
                    Log.d(TAG, "Location permission has been denied")
                }
            }
        }

    private fun getPartnerLocation(){

        database = FirebaseDatabase.getInstance()
        database.getReference("Kufre").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val locationData = snapshot.getValue(LocationData::class.java)
                    Log.d("locdata", "Partner $locationData")
                    if (locationData != null) {
                    val partnerLatitudeLongitude = LatLng(locationData.latitude, locationData.longitude)
                        Log.d("partner", "Partner $partnerLatitudeLongitude")
                        marker2?.position = partnerLatitudeLongitude

//                        mMap.addMarker(
//                            MarkerOptions().position(partnerLatitudeLongitude)
//                                .title("Kufre is here!")
//                        )
                        val update =
                            CameraUpdateFactory.newLatLngZoom(partnerLatitudeLongitude, 16.0f)
                        mMap.moveCamera(update)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Could not read from database", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        TODO("Not yet implemented")
    }
}