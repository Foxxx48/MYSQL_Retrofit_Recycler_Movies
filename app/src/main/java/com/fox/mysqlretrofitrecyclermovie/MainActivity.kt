package com.fox.mysqlretrofitrecyclermovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mysqlretrofitrecyclermovie.R
import com.fox.mysqlretrofitrecyclermovie.Tabs.Panel
import com.fox.mysqlretrofitrecyclermovie.Tabs.categories.CatalogCategories
import com.example.mysqlretrofitrecyclermovie.databinding.ActivityMainBinding
import com.fox.mysqlretrofitrecyclermovie.Tabs.movies.CatalogMovies

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        supportFragmentManager.beginTransaction().replace(R.id.content, Panel()).commit()

        binding?.bottomNav?.setOnItemSelectedListener { item ->

            when(item.itemId) {
                 R.id.panelItemBottomNav -> supportFragmentManager.beginTransaction().replace(R.id.content, Panel()).commit()

                 R.id.catalogMoviesItemBottomNav -> supportFragmentManager.beginTransaction().replace(
                     R.id.content, CatalogMovies()).commit()

                 R.id.catalogFilmsItemBottomNav -> supportFragmentManager.beginTransaction().replace(
                     R.id.content, com.fox.mysqlretrofitrecyclermovie.Tabs.CatalogFilms()
                 ).commit()

                 R.id.catalogCategoriesItemBottomNav -> supportFragmentManager.beginTransaction().replace(
                     R.id.content, CatalogCategories()
                 ).commit()
                }

            return@setOnItemSelectedListener true
        }

        binding?.bottomNav?.selectedItemId = R.id.panelItemBottomNav
    }
}