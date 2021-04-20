package com.example.omni_ofertas.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.omni_ofertas.R
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btRegistro_login.setOnClickListener {
            val intent = Intent(this,RegistroActivity::class.java)
            startActivity(intent)

        }


    }





}