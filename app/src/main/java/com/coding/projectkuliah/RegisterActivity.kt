package com.coding.projectkuliah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    lateinit var btnRegister : Button
    lateinit var etEmail : EditText
    lateinit var etPassword : EditText
    lateinit var etUsername : EditText
    lateinit var tvLogin : TextView
    lateinit var DB : DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //hooks
        btnRegister = findViewById(R.id.btnRegister)
        etEmail = findViewById(R.id.etEmail)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        tvLogin = findViewById(R.id.tvLogin)
        DB = DBHelper(this)

        //regist sqlite
        btnRegister.setOnClickListener(View.OnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (username == "" || password == "" || email == "") {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                    val checkusername = DB.checkUsername(username)
                    if (checkusername == false) {
                        val insertdata = DB.insertData(username, email, password)
                        if (insertdata == true) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registered Successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registration Failed!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Username already exists!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        })

        //intent
        tvLogin.setOnClickListener(View.OnClickListener {
            val registtologin = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(registtologin)
        })
    }
}