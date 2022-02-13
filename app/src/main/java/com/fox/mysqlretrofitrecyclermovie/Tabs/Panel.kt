package com.fox.mysqlretrofitrecyclermovie.Tabs

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mysqlretrofitrecyclermovie.R
import com.fox.mysqlretrofitrecyclermovie.api.ApiClient
import com.example.mysqlretrofitrecyclermovie.databinding.PanelBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Panel : Fragment(),View.OnKeyListener,View.OnClickListener {

    private var binding: PanelBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.panel, container, false)


        binding?.enterNameMovie?.setOnKeyListener(this)
        binding?.enterCategoryMovie?.setOnKeyListener(this)
        binding?.enterRatingMovie?.setOnKeyListener(this)

        binding?.buttonAddCategoryFilms?.setOnClickListener(this)
        binding?.buttonAddCategoryFantastic?.setOnClickListener(this)
        binding?.buttonAddCategoryAnimation?.setOnClickListener(this)
        binding?.buttonAddMovie?.setOnClickListener(this)



        return binding?.root
    }

    override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
        when (view.id) {

            R.id.enter_name_movie -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEnterNameMovie?.text = binding?.enterNameMovie?.text
                    binding?.enterNameMovie?.setText("")
                    return true
                }

            }

            R.id.enter_category_movie -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEnterCategoryMovie?.text = binding?.enterCategoryMovie?.text
                    binding?.enterCategoryMovie?.setText("")
                    return true
                }

            }

            R.id.enter_rating_movie-> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEnterRatingMovie?.text = binding?.enterRatingMovie?.text
                    binding?.enterRatingMovie?.setText("")
                    return true
                }

            }
        }

        return false
    }

    override fun onClick(view: View) {

        when(view.id) {

            R.id.buttonAddCategoryFilms -> {

                insertCategory(binding?.buttonAddCategoryFilms?.text?.toString())

            }

            R.id.buttonAddCategoryFantastic -> {

                insertCategory(binding?.buttonAddCategoryFantastic?.text?.toString())

            }

            R.id.buttonAddCategoryAnimation -> {

                insertCategory(binding?.buttonAddCategoryAnimation?.text?.toString())

            }

            R.id.buttonAddMovie -> {

                insertMovie(binding?.resEnterNameMovie?.text?.toString(),
                     binding?.resEnterCategoryMovie?.text?.toString(),
                     binding?.resEnterRatingMovie?.text?.toString())

                clearResEnterMovie()

            }


        }

    }

    private fun clearResEnterMovie() {
        binding?.resEnterNameMovie?.setText("")
        binding?.resEnterCategoryMovie?.setText("")
        binding?.resEnterRatingMovie?.setText("")

    }


    private fun insertCategory(name: String?) {
        val callInsertCategory: Call<ResponseBody?>? = ApiClient.instance?.api?.insertCategory(name)
        callInsertCategory?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(context, "ДОБАВЛЕНА КАТЕГОРИЯ", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
               Toast.makeText(context, "ОШИБКА", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun insertMovie(name: String?, category: String?, rating: String?) {
        val callInsertMovie: Call<ResponseBody?>? = ApiClient.instance?.api?.insertMovie(name, category, rating)
        callInsertMovie?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(context, "ФИЛЬМ ДОБАВЛЕН", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(context, "ОШИБКА", Toast.LENGTH_SHORT).show()
            }
        })
    }


}