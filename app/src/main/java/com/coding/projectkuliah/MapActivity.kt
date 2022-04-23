package com.coding.projectkuliah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gu.toolargetool.TooLargeTool;

class MapActivity : AppCompatActivity() {
    lateinit var mapFragment : SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        TooLargeTool.startLogging(application)
        val extras = intent.extras
        var rsX = extras?.getString("x")
        var rsY = extras?.getString("y")
        extras?.clear()

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it

            var location1 = LatLng(rsX!!.toDouble(), rsY!!.toDouble())
            googleMap.addMarker(MarkerOptions().position(location1).title("Alamat Jasa"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,15f))
        })
    }
}