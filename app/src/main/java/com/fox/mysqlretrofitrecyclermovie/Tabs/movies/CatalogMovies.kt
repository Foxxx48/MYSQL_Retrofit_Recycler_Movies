package com.fox.mysqlretrofitrecyclermovie.Tabs.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysqlretrofitrecyclermovie.R
import com.example.mysqlretrofitrecyclermovie.databinding.CatalogMoviesBinding
import com.fox.mysqlretrofitrecyclermovie.api.ApiClient
import com.fox.mysqlretrofitrecyclermovie.api.models.MoviesApiModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatalogMovies : Fragment(),View.OnClickListener {

    private var binding: CatalogMoviesBinding? = null
    private var moviesAdapter: MoviesAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.catalog_movies, container, false)

        loadMovies()

        binding?.deleteAllMovies?.setOnClickListener(this)

        return binding?.root
    }



    private fun loadMovies () {

        val callMovies = ApiClient.instance?.api?.getMovie()
        callMovies?.enqueue(object: Callback<ArrayList<MoviesApiModel>> {
            override fun onResponse(
                call: Call<ArrayList<MoviesApiModel>>,
                response: Response<ArrayList<MoviesApiModel>>
            ) {

                val loadMovies = response.body()

                binding?.recyclerMovies?.layoutManager = LinearLayoutManager(context)
                moviesAdapter = loadMovies?.let {
                    MoviesAdapter(
                        it, { idMovie:Int->deleteMovie(idMovie)},
                        { moviesApiModel: MoviesApiModel ->editMovie(moviesApiModel)})
                }
                binding?.recyclerMovies?.adapter = moviesAdapter

                Toast.makeText(context, "ЗАГРУЗКА", Toast.LENGTH_SHORT).show()


            }

            override fun onFailure(call: Call<ArrayList<MoviesApiModel>>, t: Throwable) {
                Toast.makeText(context, "ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!", Toast.LENGTH_SHORT).show()

            }
        })

    }

    override fun onClick(v: View?) {

        clearAllMovies()

    }

    private fun deleteMovie(id:Int) {

        val callDeleteMovie: Call<ResponseBody?>? = ApiClient.instance?.api?.deleteMovie(id)

        callDeleteMovie?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "ФИЛЬМ УДАЛЕН",
                    Toast.LENGTH_SHORT
                ).show()

                loadMovies()
            }



            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(
                    context,
                    "ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })

    }

    private fun editMovie(moviesApiModel: MoviesApiModel) {
        val panelEditMovie = PanelEditMovie()
        val parameters = Bundle()
        parameters.putString("idMovie", moviesApiModel.id.toString())
        parameters.putString("nameMovie", moviesApiModel.name)
        parameters.putString("categoryMovie", moviesApiModel.category)
        parameters.putString("durationMovie", moviesApiModel.duration)
        panelEditMovie.arguments = parameters

        panelEditMovie.show((context as FragmentActivity).supportFragmentManager, "editMovie")
    }

    private fun clearAllMovies() {

        val callClearAllMovies: Call<ResponseBody?>? = ApiClient.instance?.api?.clearMovies()

        callClearAllMovies?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "ФИЛЬМЫ УДАЛЕНЫ",
                    Toast.LENGTH_SHORT
                ).show()

                loadMovies()
            }



            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(
                    context,
                    "ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })

    }


}