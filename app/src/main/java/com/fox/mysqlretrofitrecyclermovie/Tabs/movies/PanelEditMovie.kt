package com.fox.mysqlretrofitrecyclermovie.Tabs.movies

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.mysqlretrofitrecyclermovie.R
import com.example.mysqlretrofitrecyclermovie.databinding.PanelEditMovieBinding
import com.fox.mysqlretrofitrecyclermovie.api.ApiClient
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class PanelEditMovie : BottomSheetDialogFragment(),View.OnKeyListener, View.OnClickListener {

    private var binding: PanelEditMovieBinding? = null
    private var idMovie:Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.panel_edit_movie, container, false)

        idMovie = arguments?.getString("idMovie")?.toInt()
        binding?.editNameMovie?.setText(arguments?.getString("nameMovie").toString())
        binding?.editCategoryMovie?.setText(arguments?.getString("categoryMovie").toString())
        binding?.editRatingMovie?.setText(arguments?.getString("ratingMovie").toString())

        binding?.editNameMovie?.setOnKeyListener(this)
        binding?.editCategoryMovie?.setOnKeyListener(this)
        binding?.editRatingMovie?.setOnKeyListener(this)

        binding?.buttonEditMovie?.setOnClickListener(this)


        return binding?.root
    }

    override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
        when (view.id) {


            R.id.editNameMovie -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {

                    binding?.resEditNameMovie?.text = binding?.editNameMovie?.text
                    binding?.editNameMovie?.setText("")

                    return true
                }

            }

            R.id.editCategoryMovie -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {

                    binding?.resEditCategoryMovie?.text = binding?.editCategoryMovie?.text
                    binding?.editCategoryMovie?.setText("")

                    return true
                }

            }

            R.id.edit_rating_movie -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {

                    binding?.resEditRatingMovie?.text = binding?.editRatingMovie?.text
                    binding?.editRatingMovie?.setText("")

                    return true
                }

            }
        }

        return false
    }

    override fun onClick(view: View) {

        updateMovie(binding?.resEditNameMovie?.text?.toString()!!, binding?.resEditCategoryMovie?.text?.toString()!!,
            binding?.resEditRatingMovie?.text?.toString()!!)
    }




    private fun updateMovie(name: String, category: String, rating: String) {
        val callUpdateMovies = ApiClient.instance?.api?.updateMovie(idMovie.toString().toInt(), name, category, rating)

        callUpdateMovies?.enqueue(object : retrofit2.Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "ФИЛЬМ ОБНОВЛЕН",
                    Toast.LENGTH_SHORT
                ).show()

                loadScreen()


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

    private fun loadScreen() {

        dismiss()

        (context as FragmentActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.content, CatalogMovies()).commit()

    }

}