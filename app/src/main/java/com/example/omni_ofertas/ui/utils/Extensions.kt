package com.example.omni_ofertas.ui.utils

import java.io.InputStream
import  android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.example.omni_ofertas.ui.model.Oferta
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.nio.charset.Charset

private val db = FirebaseFirestore.getInstance()

fun getOfertaFromFirebase(){


    var ofertaList = listOf<Oferta>()

    db.collection("Oferta").document().get().addOnSuccessListener {
        ofertaList = it.get("Oferta") as List<Oferta>
    }
}


fun ImageView.loadImage(image:String){

    Glide.with(this)
        .load(image)
        .into(this)


}
fun ViewGroup.inflate(@LayoutRes layoutRes: Int,attachToRoot:Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes,this,attachToRoot)