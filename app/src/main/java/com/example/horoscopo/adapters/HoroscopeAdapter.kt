package com.example.horoscopo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.horoscopo.R
import com.example.horoscopo.data.Horoscope
import com.example.horoscopo.utils.SessionManager


class HoroscopeAdapter(private var items: List<Horoscope>, val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<HoroscopeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroscopeViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_horoscope, parent, false)
        return HoroscopeViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HoroscopeViewHolder, position: Int) {
        val horoscope = items[position]
        holder.render(horoscope)
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }
    //cualquier funcionalidad nueva hay qye decirselo al adapter
    //esta funcion es para ordenar la lista con el fav arriba de la lista.
    /*fun setNewItems(items: List<Horoscope>) {
        this.items = items
    }*/
    //esta funcion sirve para ir recargando la lista de coincidencias
    fun updateItems(items: List<Horoscope>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class HoroscopeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var nameTextView: TextView = view.findViewById(R.id.nameTextView)
    private var datesTextView: TextView = view.findViewById(R.id.datesTextView)
    private var symbolImageView: ImageView = view.findViewById(R.id.symbolImageView)
    private var favoriteImageView: ImageView = view.findViewById(R.id.favoriteImageView)

    fun render(horoscope: Horoscope) {
        //val context = itemView.context
        //nameTextView.text = context.getString(horoscope.name)
        //symbolImageView.setImageDrawable(context.getDrawable(horoscope.image))

        nameTextView.setText(horoscope.name)
        datesTextView.setText(horoscope.dates)
        symbolImageView.setImageResource(horoscope.image)


        if (SessionManager(itemView.context).isFavorite(horoscope.id)) {
            favoriteImageView.visibility = View.VISIBLE

        } else {
            favoriteImageView.visibility = View.GONE
        }
    }
}