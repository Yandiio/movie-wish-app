package com.wish.movieapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.wish.movieapp.R
import com.wish.movieapp.databinding.FragmentFavoriteBinding
import com.wish.movieapp.ui.MainActivity
import com.wish.movieapp.ui.favorite.movie.FavoriteMovieFragment
import com.wish.movieapp.ui.favorite.tvshow.FavoriteTvShowFragment
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _favoriteFragmentBinding: FragmentFavoriteBinding? = null
    private val binding get() = _favoriteFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _favoriteFragmentBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            (activity as MainActivity).setActionBarTitle("Favorite Show")

            setViewPager()
        }
    }

    private fun setViewPager() {
        val fragmentList = listOf(FavoriteMovieFragment(), FavoriteTvShowFragment())
        val tabTitle = listOf(resources.getString(R.string.movie), resources.getString(R.string.tv_show))

        binding?.favViewPager?.adapter = ViewpagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        TabLayoutMediator(fav_tab_layout, fav_view_pager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _favoriteFragmentBinding = null
    }
}