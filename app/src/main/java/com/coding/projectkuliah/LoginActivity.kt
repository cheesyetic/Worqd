package com.coding.projectkuliah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    lateinit var inputUsername : EditText
    lateinit var inputPassword : EditText
    lateinit var btnLogin : Button
    lateinit var tvSignup : TextView
    lateinit var DB : DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //hooks
        inputUsername = findViewById(R.id.inputUsername)
        inputPassword = findViewById(R.id.inputPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvSignup = findViewById(R.id.tvSignup)
        DB = DBHelper(this)

        //login
        btnLogin.setOnClickListener(View.OnClickListener {
            val username = inputUsername.text.toString()
            val password = inputPassword.text.toString()
            if (username == "" || password == "") Toast.makeText(
                this@LoginActivity,
                "Please fill all the fields",
                Toast.LENGTH_SHORT
            ).show() else {
                val checkuserpass: Boolean = DB.chekUsernamePassword(username, password)
                if (checkuserpass == true) {
                    Toast.makeText(this@LoginActivity, "Login Successful!", Toast.LENGTH_SHORT)
                        .show()
                    val logintodashboard = Intent(this@LoginActivity, NavigationActivity::class.java)
                    startActivity(logintodashboard)
                    finish()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Username/Password Not Found!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        //intent
        tvSignup.setOnClickListener(View.OnClickListener {
            val signuptologin = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(signuptologin)
        })
    }
}