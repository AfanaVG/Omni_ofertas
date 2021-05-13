package com.example.videoclub.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.omni_ofertas.ui.model.Oferta
import com.example.omni_ofertas.ui.utils.loadImage

import kotlinx.android.synthetic.main.item_oferta.view.*

class OfertasViewHolder (view:View):RecyclerView.ViewHolder(view){
    fun bind(oferta: Oferta){
        itemView.nombreOferta.text = oferta.nombre
        oferta.foto?.let {oferta ->
            itemView.fotoOferta.loadImage(oferta)
        }
    }
}