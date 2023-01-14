package com.example.aplicacionsophos.UI.Main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.aplicacionsophos.R
import com.example.aplicacionsophos.UI.GetData.GetDataFragment
import com.example.aplicacionsophos.UI.Mytoolbar
import com.example.aplicacionsophos.ViewModel.AplicationViewModel
import java.util.*


class Main: AppCompatActivity() {
    private lateinit var viewModel: AplicationViewModel
    private lateinit var navController: NavController


    var nigthvariable=0
    var languagevarible=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController=navHostFragment.navController

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


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

    }
}


