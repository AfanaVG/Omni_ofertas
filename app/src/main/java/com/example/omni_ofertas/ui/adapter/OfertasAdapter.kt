

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.omni_ofertas.R
import com.example.omni_ofertas.ui.model.Oferta
import com.example.omni_ofertas.ui.utils.inflate
import com.example.videoclub.adapter.OfertasViewHolder

import kotlin.collections.ArrayList

class OfertasAdapter(private val listener:(Oferta)-> Unit) :RecyclerView.Adapter<OfertasViewHolder>(){
    private  val ofertaList = arrayListOf<Oferta>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfertasViewHolder {
        val view = parent.inflate(R.layout.item_oferta,false)
        return  OfertasViewHolder(view)
    }

    override fun getItemCount(): Int = ofertaList.size

    override fun onBindViewHolder(holder: OfertasViewHolder, position: Int) {
        val movie = ofertaList[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener{listener(movie)}
    }

    fun refreshList (movieList: ArrayList<Oferta>){
        this.ofertaList.addAll(movieList)
        notifyDataSetChanged()
    }

    fun filterByName(movies:List<Oferta>){

        ofertaList.clear()
        ofertaList.addAll(movies)
        notifyDataSetChanged()
    }

    fun orderByName(){

        val sortedList = ofertaList.sortedBy { it.nombre }
        //val reversedSortedList = movieList.reversed()
        ofertaList.clear()
        ofertaList.addAll(sortedList)
        notifyDataSetChanged()
    }

    fun insertObject(){

        //val item = Oferta(13,"Prueba","prueba","https://www.impawards.com/1999/posters/green_mile_ver3.jpg",1952,8)
        //ofertaList.add(0,item)
        //notifyItemInserted(0)

    }

    fun modifyObject(){

        ofertaList[0].nombre = "Prueba"
        notifyItemChanged(0)

    }

    fun deleteObject(){

        ofertaList.removeAt(0)
        notifyItemRemoved(0)

    }

}