package com.example.omni_ofertas.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.omni_ofertas.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registro.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btRegistro_login.setOnClickListener(this)
        btEntrar_login.setOnClickListener(this)


        //Google analytics
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle();
        bundle.putString("message","Integración de Firebbase completa")
        analytics.logEvent("InitScreen",bundle)



    }

    override fun onClick(view: View?) {
        when(view){
            btRegistro_login ->accederRegistro()
            btEntrar_login -> registrarUsuario()
        }
    }

    private fun registrarUsuario(){

        if (edEmail_login.text.isNotEmpty() && edPass_login.text.isNotEmpty()){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(edEmail_login.text.toString()
                ,edPass_login.text.toString()).addOnCompleteListener{

                if (it.isSuccessful){
                    autentificaionCompletada(it.result?.user?.email?:"",ProviderType.BASIC)
                }else{
                    errorAutentificacion()
                }

            }
        }

    }


    private fun errorAutentificacion(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autentificando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun autentificaionCompletada(email:String,provider:ProviderType){

        val homeIntent = Intent(this,MenuInicio::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }

        startActivity(homeIntent)

    }


    private fun accederRegistro(){

        val intent = Intent(this,RegistroActivity::class.java)
        startActivity(intent)
    }




}