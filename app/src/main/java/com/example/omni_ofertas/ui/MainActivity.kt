package com.example.omni_ofertas.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.omni_ofertas.R
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btRegistro_login.setOnClickListener(this)




    }

    override fun onClick(view: View?) {
        when(view){
            btRegistro_login ->accederRegistro()

        }
    }


    private fun accederRegistro(){

        val intent = Intent(this,RegistroActivity::class.java)
        startActivity(intent)
    }





}