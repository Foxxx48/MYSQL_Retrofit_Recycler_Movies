package com.fox.mysqlretrofitrecyclermovie.Tabs.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mysqlretrofitrecyclermovie.R
import com.example.mysqlretrofitrecyclermovie.databinding.MoviesItemBinding
import com.fox.mysqlretrofitrecyclermovie.api.models.MoviesApiModel

class MoviesAdapter(private val moviesList : ArrayList<MoviesApiModel>,
                    private val deleteMovie:(Int)->Unit,
                    private val editMovie:(MoviesApiModel)->Unit): RecyclerView.Adapter<MoviesAdapter.MoviesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: MoviesItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.movies_item, parent, false)
        return MoviesHolder(binding)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.bind(moviesList[position], deleteMovie, editMovie)
    }

    class MoviesHolder(val binding: MoviesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            movies: MoviesApiModel, deleteMovie: (Int) -> Unit, editMovie: (MoviesApiModel) -> Unit
        ) {

            val idMovie = movies.id

            binding.idMovie.text = idMovie.toString()

            binding.nameMovie.text = movies.name
            binding.categoryMovie.text = movies.category
            binding.ratingMovie.text = movies.rating.toString()


            binding.editMovie.setOnClickListener(View.OnClickListener {
                editMovie(movies)
            })

            binding.deleteMovie.setOnClickListener(View.OnClickListener {
                deleteMovie(idMovie!!)
            })
        }

    }

}