package com.wish.movieapp.ui.favorite.tvshow

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wish.movieapp.R

class FavoriteTvShowFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteTvShowFragment()
    }

    private lateinit var viewModel: FavoriteTvShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_tv_show_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteTvShowViewModel::class.java)
        // TODO: Use the ViewModel
    }

}