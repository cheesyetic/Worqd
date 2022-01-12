package com.coding.projectkuliah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var btnPindah : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val DB: DBHelper = DBHelper(this)
        val akun: Int = DB.checkAccount()
        if (akun >= 0) {
            val intent = Intent(this, NavigationActivity::class.java)
            intent.putExtra("akun_id", akun);
            Log.d("CREATION", "Login logged id:$akun")
            startActivity(intent)
        } else {

        //hooks
        btnPindah = findViewById(R.id.btnPindah)

        //intent
        btnPindah.setOnClickListener(View.OnClickListener {
            val pindah = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(pindah)
            })
        }
    }
}