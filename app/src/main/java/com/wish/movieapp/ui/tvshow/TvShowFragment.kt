package com.wish.movieapp.ui.tvshow

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wish.movieapp.utils.SortUtils.VOTE_BEST
import com.wish.movieapp.databinding.FragmentTvshowBinding
import com.wish.movieapp.ui.MainActivity
import com.wish.movieapp.ui.detail.DetailActivity
import com.wish.movieapp.ui.detail.DetailViewModel.Companion.TV_SHOW
import com.wish.movieapp.utils.MarginItemDecoration
import com.wish.movieapp.viewmodel.ViewModelFactory
import com.wish.movieapp.vo.Status

class TvShowFragment : Fragment(), TvShowAdapter.OnItemClickCallback {

    private lateinit var fragmentTvShowBinding: FragmentTvshowBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTvShowBinding = FragmentTvshowBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return fragmentTvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            (activity as MainActivity).setActionBarTitle("TV Shows List")

            showProgressBar(true)
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

            val tvShowAdapter = TvShowAdapter()
            viewModel.getTvShow(VOTE_BEST).observe(viewLifecycleOwner, { tvShows ->
                if (tvShows != null) {
                    when (tvShows.status) {
                        Status.LOADING -> showProgressBar(true)
                        Status.SUCCESS -> {
                            showProgressBar(false)
                            tvShowAdapter.submitList(tvShows.data)
                            tvShowAdapter.setOnItemClickCallback(this)
                            tvShowAdapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            showProgressBar(false)
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            val marginVertical = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)

            with(fragmentTvShowBinding.rvTvShow) {
                addItemDecoration(MarginItemDecoration(marginVertical.toInt()))
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = tvShowAdapter
            }
        }
    }

    private fun showProgressBar(state: Boolean) {
        fragmentTvShowBinding.progressTvshow.isVisible = state
        fragmentTvShowBinding.rvTvShow.isInvisible = state
    }

    override fun onItemClicked(id: String) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_FILM, id)
        intent.putExtra(DetailActivity.EXTRA_CATEGORY, TV_SHOW)

        context?.startActivity(intent)
    }
}