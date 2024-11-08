package com.example.horoscopo.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.horoscopo.R
import com.example.horoscopo.adapters.HoroscopeAdapter
import com.example.horoscopo.data.Horoscope
import com.example.horoscopo.data.HoroscopeProvider
import com.example.horoscopo.utils.SessionManager

class ListActivity : AppCompatActivity() {

    lateinit var horoscopeList: List<Horoscope>

    lateinit var recyclerView: RecyclerView


    lateinit var adapter: HoroscopeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list)

        recyclerView = findViewById(R.id.recyclerView)

        horoscopeList = HoroscopeProvider.findAll()



       adapter= HoroscopeAdapter(horoscopeList) { position ->
            val horoscope = horoscopeList[position]
            navigateToDetail(horoscope)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


    }

    override fun onResume() {


        super.onResume()
        //hay que crear una lista mutable
        horoscopeList = getSortedList()

        adapter.updateItems(horoscopeList)
    }

    fun getSortedList(): List<Horoscope> {
        //nos traemos la sesion donde tenemos el contexto
        val session = SessionManager(this)

        return HoroscopeProvider.findAll().sortedByDescending { session.isFavorite(it.id) }
    }
        //funcion para mostrar el menu...esto lo copiamos del otro qye hicimos para la lista

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_activity, menu)


        //ahora dentro de la lista buscamos la opcion en el menu

        val searchMenuItem = menu?.findItem(R.id.menu_search)!!//para decir que si o se tenemos algo en el menu

        // Obtengo la clase del ActionView asociada a esa opción del menú

        val searchView = searchMenuItem.actionView as SearchView //importante importar el searchview correcto dx!!

        //ahora la logica al listener para las teclas.


        //este object tiene asociado dos metodos, que se te añaden directamente
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //primer metodo para cuando se pulse el btn de buscar que lo dejamos en false, por el enter
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            //esta establece la logica de busqueda, basicamente el filtro
            override fun onQueryTextChange(newText: String): Boolean {
                horoscopeList = getSortedList().filter {
                    getString(it.name).startsWith(newText, true) ||
                    getString(it.dates).contains(newText, true)

                }
                adapter.updateItems(horoscopeList)
                return true

            }

        })



        return true

    }

    private fun navigateToDetail(horoscope: Horoscope) {


        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("horoscope_id", horoscope.id)
        startActivity(intent)
    }




}