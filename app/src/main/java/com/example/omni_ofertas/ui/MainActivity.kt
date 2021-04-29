package com.example.omni_ofertas.ui

import android.content.Context
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
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registro.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val GOOGLE_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btRegistro_login.setOnClickListener(this)
        btEntrar_login.setOnClickListener(this)
        btRegistroGmail_login.setOnClickListener(this)


        //Google analytics
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle();
        bundle.putString("message","Integración de Firebbase completa")
        analytics.logEvent("InitScreen",bundle)

        val prefs = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()

        FirebaseAuth.getInstance().signOut()





    }

    override fun onClick(view: View?) {
        when(view){
            btRegistro_login ->accederRegistro()
            btEntrar_login -> registrarUsuario("simple")
            btRegistroGmail_login -> registrarUsuario("google")
        }
    }

    private fun registrarUsuario(modo:String){

        when(modo) {
            "simple" -> {

            if (edEmail_login.text.isNotEmpty() && edPass_login.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    edEmail_login.text.toString()
                    , edPass_login.text.toString()
                ).addOnCompleteListener {

                    if (it.isSuccessful) {
                        autentificaionCompletada(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        errorAutentificacion()
                    }

                }
            }
        }
            "google"->{
                val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

                val googleClient = GoogleSignIn.getClient(this, googleConf)
                googleClient.signOut()

                startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
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
                            autentificaionCompletada(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            errorAutentificacion()
                        }
                    }
                }

            }catch (e: ApiException){
                errorAutentificacion()
            }

        }
    }




}