package com.example.aplicacionsophos.UI

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.dynamicfeatures.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.aplicacionsophos.UI.MainFragment
import com.example.aplicacionsophos.R
import com.example.aplicacionsophos.data.model.Office
import com.example.aplicacionsophos.service.Api
import java.util.*


class Main: AppCompatActivity() {
    private lateinit var viewModel: AplicationViewModel
    private lateinit var navController: NavController
    //val mtoolbar:Toolbar = findViewById(R.id.toolbar)
   // private var officeList: List<Office> = emptyList<Office>()
   // private var cityname = ArrayList<String>()
  //  private val bundle= intent.extras
   // private var lat = ArrayList<Double>()
  //  private var long =ArrayList<Double>()
   // private var nombre=ArrayList<String>()

    var nigthvariable=0
    var languagevarible=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController=navHostFragment.navController
        //viewModel = ViewModelProvider(this).get(AplicationViewModel::class.java)

        //val buttonSendocument=findViewById<Button>(R.id.button_senddocument)
        //val buttonoffice=findViewById<Button>(R.id.button_sendoffice)
        //val buttonwritedocument=findViewById<Button>(R.id.button_second)
        //buttonSendocument.setOnClickListener { gotosenddocument()}
        //buttonoffice.setOnClickListener { gotomap() }
        //buttonwritedocument.setOnClickListener {getdocument()}

      /*  if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, MainFragment.newInstance())
                .commitNow()
        }*/
        Mytoolbar().show(this,"Jhon",false)
        setupActionBarWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.SendDocument -> navController.navigate(R.id.getDataFragment)
            R.id.WatchDocument ->  navController.navigate(R.id.sendDataFragment)
            R.id.office ->  navController.navigate(R.id.mapFragment)
            R.id.Nightmode ->  nightmode()
            R.id.Language ->  changelanguage()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun gotosenddocument(){
        //val i= Intent(this, Send_document::class.java)
        //val idregistro=bundle?.getString("idregistro")
       // val mail=bundle?.getString("mail")
        // startActivity(i)
        //i.putExtra("idregistro", idregistro)
        //i.putExtra("mail", mail)
        //supportFragmentManager.beginTransaction()
        //    .replace(R.id.nav_host_fragment_content_main, SendDataFragment.newInstance())
       //     .commitNow()
    }
    private fun gotomap(){
       // viewModel.getOficeList()
       //  val lista = viewModel.oficeList.value
        // val i= Intent(this, OficeMapsActivity::class.java)
       // i.putExtra("miLista", lista)
       // startActivity(i)
        /*supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, MapFragment.newInstance())
            .commitNow()*/
    }
    private fun nightmode(){

       if(nigthvariable==0){
           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
           nigthvariable=1
       }else{
           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
           nigthvariable=0
       }
    }
    private fun changelanguage(){
        if(languagevarible==0){
            updateresources("en")
            languagevarible=1
        }else{
            updateresources("es")
            languagevarible=0
        }
    }

    private fun updateresources(language: String) {
        val resource=resources
        val displaymetrics=resource.displayMetrics
        val configuration=resources.configuration
        configuration.setLocale(Locale(language))
        resource.updateConfiguration(configuration,displaymetrics)
        configuration.locale = Locale(language)
        resources.updateConfiguration(configuration, displaymetrics)

    }
    private fun getdocument(){
        // val z= Intent(this, GetDocumentActivity::class.java)
        // startActivity(z)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, GetDataFragment.newInstance())
            .commitNow()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}


