package com.example.horoscopo.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.horoscopo.R
import com.example.horoscopo.data.Horoscope
import com.example.horoscopo.data.HoroscopeProvider
import com.example.horoscopo.utils.SessionManager

class DetailActivity : AppCompatActivity() {

    private lateinit var horoscope: Horoscope

    var isFavorite = false


    lateinit var favoriteMenuItem: MenuItem

    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        val id = intent.getStringExtra("horoscope_id")!!

        horoscope = HoroscopeProvider.findById(id)

        // Modificamos el ActionBar para mostrar título y subtítulo
        supportActionBar?.title = getString(horoscope.name)
        supportActionBar?.subtitle = getString(horoscope.dates)
        // Habilitamos el boton de volver atras en el ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Instanciamos el objeto de la sesión
        session = SessionManager(this)

        // Revisamos si el horóscopo es favorito
        isFavorite = session.isFavorite(horoscope.id)


        findViewById<TextView>(R.id.tv).setText(horoscope.name)
        findViewById<ImageView>(R.id.iv).setImageResource(horoscope.image)
        findViewById<Button>(R.id.b).setOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_activity, menu)

        favoriteMenuItem = menu?.findItem(R.id.menu_favorite)!!

        setFavoriteIcon()
        return true

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_favorite -> {
                if (isFavorite) {
                    session.setFavorite("")
                } else {
                    session.setFavorite(horoscope.id)
                }
                isFavorite = !isFavorite
                setFavoriteIcon()
                return true
            }
            R.id.menu_share -> {
                println("Menu compartir")
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun setFavoriteIcon() {
        if(isFavorite) {
            favoriteMenuItem.setIcon(R.drawable.ic_favorite)
        } else {
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_border_24)
        }
    }

    override fun onBackPressed() {
        if (horoscope.id == "aries") {
            Toast.makeText(this, "No puedes volver", Toast.LENGTH_LONG).show()
        } else {
            super.onBackPressed()
        }
    }
}