package com.example.omni_ofertas.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.omni_ofertas.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity(), View.OnClickListener {

    private val GOOGLE_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btConfirmar_regis.setOnClickListener(this)
        btRegistroGmail.setOnClickListener(this)
        btLimpiar_regis.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            btConfirmar_regis -> registrarUsuario("simple")
            btRegistroGmail -> registrarUsuario("google")
            btLimpiar_regis -> limpiarCampos()
        }
    }

    private fun limpiarCampos(){

        edEmail_Regis.text.clear();
        edApellido_Regis.text.clear();
        edNombre_Regis.text.clear();
        edTelef_Regis.text.clear();
        edPass_Regis.text.clear();
        edPassR_Regis.text.clear();

    }

    private fun registrarUsuario(modo:String){


        when(modo) {
            "simple" -> {
                if (edEmail_Regis.text.isNotEmpty() && edPass_Regis.text.isNotEmpty()) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(edEmail_Regis.text.toString()
                            , edPass_Regis.text.toString()).addOnCompleteListener {


                        if (it.isSuccessful) {
                            autentificaionCompletada()
                        } else {
                            errorAutentificacion()
                        }

                    }
                } else {

                    camposVacios()

                }
            }

            "google" -> {


                    val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build()

                    val googleClient = GoogleSignIn.getClient(this, googleConf)
                    googleClient.signOut()

                    startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)


            }

        }

    }


    private fun camposVacios(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Introduzca datos por favor")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN){

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {

                val account = task.getResult(ApiException::class.java)

                if (account != null) {

                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {

                        if (it.isSuccessful) {
                            autentificaionCompletada()
                        } else {
                            errorAutentificacion()
                        }
                    }
                }

            }catch (e:ApiException){
                errorAutentificacion()
            }

        }
    }


}