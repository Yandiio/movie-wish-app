package com.wish.movieapp.ui.favorite.movie

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.wish.movieapp.R
import com.wish.movieapp.databinding.FavoriteMovieFragmentBinding
import com.wish.movieapp.utils.MarginItemDecoration
import com.wish.movieapp.viewmodel.ViewModelFactory

class FavoriteMovieFragment : Fragment(), FavoriteMovieAdapter.OnItemClickCallback {

    private var _fragmentMovieFavoriteBinding: FavoriteMovieFragmentBinding? = null
    private val binding get() = _fragmentMovieFavoriteBinding

    private lateinit var viewModel: FavoriteMovieViewModel
    private lateinit var adapter: FavoriteMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_movie_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavMovies().observe(viewLifecycleOwner) { favMovies ->
            if (favMovies != null) {
                adapter.submitList(favMovies)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemTouchHelper.attachToRecyclerView(binding?.rvFavMovies)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]

            adapter = FavoriteMovieAdapter()
            adapter.setOnItemClickCallback(this)

            viewModel.getFavMovies().observe(viewLifecycleOwner) { favMovies ->
                if (favMovies != null) {
                    binding?.tvNotFound?.isInvisible = true
                    adapter.submitList(favMovies)
                } else {
                    binding?.tvNotFound?.isVisible = true
                    binding?.rvFavMovies?.isInvisible = true
                }
            }

            val marginVertical = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)

            with(binding?.rvFavMovies) {
                this?.addItemDecoration(MarginItemDecoration(marginVertical.toInt()))
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = adapter
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieEntity = adapter.getSwipedData(swipedPosition)
                movieEntity?.let { viewModel.setFavMovie(it) }

                val snackBar = Snackbar.make(requireView(), R.string.undo, Snackbar.LENGTH_LONG)
                snackBar.setAction(R.string.ok) { _ ->
                    movieEntity?.let { viewModel.setFavMovie(it) }
                }
                snackBar.show()
            }
        }
    })

    override fun onItemClicked(id: String) {
        TODO("Not yet implemented")
    }

}