package com.wish.movieapp.ui.movie

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import com.wish.movieapp.BuildConfig.IMAGE_URL
import com.bumptech.glide.request.transition.Transition
import com.wish.movieapp.R
import com.wish.movieapp.data.local.entity.MovieEntity
import com.wish.movieapp.databinding.ItemMovieBinding

class MovieAdapter: PagingDataAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {
    object DIFF_CALLBACK : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == newItem
        }
    }

    inner class MovieViewHolder(private val view: ItemMovieBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(movie: MovieEntity) {
            with(view) {
                tvTitle.text = movie.title
                tvGenre.text = movie.genres
                tvRating.text = movie.voteAverage.toString()

                Glide.with(itemView.context)
                    .asBitmap()
                    .load(IMAGE_URL + movie.posterPath)
//                    .apply(RequestOptions.placeholderOf(R.drawable.ic_movie_poster_placeholder))
                    .transform(RoundedCorners(28))
                    .into(object : CustomTarget<Bitmap>() {
                        @RequiresApi(Build.VERSION_CODES.M)
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            ivPoster.setImageBitmap(resource)

                            Palette.from(resource).generate { palette ->
                                val defValue = itemView.resources.getColor(R.color.black, itemView.context.theme)
                                cardItem.setCardBackgroundColor(palette?.getDarkMutedColor(defValue)
                                    ?: defValue)
                            }
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMovieBinding)
    }
}