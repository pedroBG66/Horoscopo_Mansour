package com.example.horoscopo.activities

import android.content.Intent
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailActivity : AppCompatActivity() {

    private lateinit var horoscope: Horoscope

    var isFavorite = false


    lateinit var favoriteMenuItem: MenuItem

    lateinit var session: SessionManager
    lateinit var luckTextView: TextView
    lateinit var zodiacIcon: ImageView
    lateinit var previous_zodiac: Button
    lateinit var next_zodiac: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        val id = intent.getStringExtra("horoscope_id")!!

        loadData(id)

        // Habilitamos el boton de volver atras en el ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Instanciamos el objeto de la sesión
        session = SessionManager(this)
        //llamamos al texto de la suerte del dia
        luckTextView = findViewById(R.id.luckTextView)
        zodiacIcon = findViewById(R.id.zodiac_ic)
        previous_zodiac = findViewById(R.id.previous_zodiac)
        next_zodiac = findViewById(R.id.next_zodiac)

        // Revisamos si el horóscopo es favorito
        isFavorite = session.isFavorite(horoscope.id)




        zodiacIcon.setImageResource(horoscope.image)
        findViewById<TextView>(R.id.zodiacNameDetailsPage).setText(horoscope.name)
        findViewById<TextView>(R.id.zodiacDatesDetailsPage).setText(horoscope.dates)
        findViewById<TextView>(R.id.element).text = getString(R.string.element_label, getString(horoscope.getElementStringRes()))
        findViewById<TextView>(R.id.element).setTextColor(getColor(horoscope.getElementColorRes()))
        findViewById<TextView>(R.id.planet).text = getString(R.string.planet_label, getString(horoscope.planet))

        next_zodiac.setOnClickListener {
            navigateToZodiac(isNext = true)
        }

        previous_zodiac.setOnClickListener {
            navigateToZodiac(isNext = false)
        }


        getHoroscopeLuck()

    }

    private fun loadData(id: String) {

        horoscope = HoroscopeProvider.findById(id)

        // Modificamos el ActionBar para mostrar título y subtítulo
        supportActionBar?.title = getString(horoscope.name)
        supportActionBar?.subtitle = getString(horoscope.dates)
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

    //funcion para coger la API de la pagina
    fun getHoroscopeLuck() {
        var result = "Antes de hacer la llamada"

        CoroutineScope(Dispatchers.IO).launch {
            val url = URL("https://horoscope-app-api.vercel.app/api/v1/get-horoscope/daily?sign=${horoscope.id}&day=TODAY")
            val con = url.openConnection() as HttpsURLConnection
            con.requestMethod = "GET"
            val responseCode = con.responseCode
            println("Response Code :: $responseCode")
            if (responseCode == HttpsURLConnection.HTTP_OK) { // connection ok
                val jsonResponse = readStream(con.inputStream).toString()
                result = JSONObject(jsonResponse).getJSONObject("data").getString("horoscope_data")
            } else {
                result = "Hubo un error en la llamada"
            }

            //con esto podemos evitar tener que poner el codigo de abajo con el dispacher del main
            /*runOnUiThread {
                println(result)
            }*/
            CoroutineScope(Dispatchers.Main).launch {
                luckTextView.text = result
            }
        }
    }
//esta funcion parsea la informacion del json de la api
    private fun readStream (inputStream: InputStream) : StringBuilder {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val response = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            response.append(line)
        }
        reader.close()
        return response
    }

    override fun onBackPressed() {
        if (horoscope.id == "aries") {
            Toast.makeText(this, "No puedes volver", Toast.LENGTH_LONG).show()
        } else {
            super.onBackPressed()
        }
    }
    fun navigateToZodiac(isNext: Boolean) {
        val nextHoroscope = if (isNext) {
            HoroscopeProvider.getNextHoroscope(horoscope.id)
        } else {
            HoroscopeProvider.getPreviousHoroscope(horoscope.id)
        }

        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("horoscope_id", nextHoroscope.id)
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        }
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val id = intent.getStringExtra("horoscope_id")!!
        println("onNewIntent: $id")

        loadData(id)
    }


}

