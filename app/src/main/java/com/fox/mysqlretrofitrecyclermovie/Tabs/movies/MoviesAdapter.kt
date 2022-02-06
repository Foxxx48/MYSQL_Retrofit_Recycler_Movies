package com.fox.mysqlretrofitrecyclermovie.Tabs.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mysqlretrofitrecyclermovie.R
import com.fox.mysqlretrofitrecyclermovie.api.models.MoviesApiModel
import com.example.mysqlretrofitrecyclermovie.databinding.ProductsItemBinding

class MoviesAdapter(private val moviesList : ArrayList<MoviesApiModel>,
                    private val deleteProduct:(Int)->Unit,
                    private val editProduct:(MoviesApiModel)->Unit): RecyclerView.Adapter<MoviesAdapter.ProductsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ProductsItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.movies_item, parent, false)
        return ProductsHolder(binding)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) {
        holder.bind(moviesList[position], deleteProduct, editProduct)
    }

    class ProductsHolder(val binding: ProductsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            movies: MoviesApiModel, deleteProduct: (Int) -> Unit, editProduct: (MoviesApiModel) -> Unit
        ) {

            val idProduct = movies.id

            binding.idProduct.text = idProduct.toString()

            binding.nameProduct.text = movies.name
            binding.categoryProduct.text = movies.category
            binding.priceProduct.text = movies.duration.toString()


            binding.editProduct.setOnClickListener(View.OnClickListener {
                editProduct(movies)
            })

            binding.deleteProduct.setOnClickListener(View.OnClickListener {
                deleteProduct(idProduct!!)
            })
        }

    }

}