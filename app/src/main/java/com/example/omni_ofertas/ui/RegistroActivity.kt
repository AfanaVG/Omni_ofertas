package com.example.omni_ofertas.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.omni_ofertas.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btConfirmar_regis.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            btConfirmar_regis -> registrarUsuario()
        }
    }

    private fun registrarUsuario(){



        if (edEmail_Regis.text.isNotEmpty() && edPass_Regis.text.isNotEmpty()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(edEmail_Regis.text.toString()
                ,edPass_Regis.text.toString()).addOnCompleteListener{


                if (it.isSuccessful){
                    autentificaionCompletada()
                }else{
                    errorAutentificacion()
                }

            }
        }else{

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error")
            builder.setMessage("Introduzca datos por favor")
            builder.setPositiveButton("Aceptar",null)
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

    }

    private fun mostar(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(edEmail_Regis.text.toString()+" : "+ edPass_Regis.text.toString())
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    private fun errorAutentificacion(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autentificando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun autentificaionCompletada(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Correcto")
        builder.setMessage("El usuario se ha registrado corectamente")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }


}