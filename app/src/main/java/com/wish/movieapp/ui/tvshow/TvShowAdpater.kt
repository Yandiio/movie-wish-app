package com.wish.movieapp.ui.tvshow

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
import com.bumptech.glide.request.transition.Transition
import com.wish.movieapp.BuildConfig.IMAGE_URL
import com.wish.movieapp.R
import com.wish.movieapp.data.local.entity.MovieEntity
import com.wish.movieapp.data.local.entity.TvShowEntity
import com.wish.movieapp.databinding.ItemMovieBinding
import com.wish.movieapp.ui.movie.MovieAdapter

class TvShowAdpater: PagingDataAdapter<TvShowEntity, TvShowAdpater.TvShowViewHolder>(DIFFTV_CALLBACK) {
    object DIFFTV_CALLBACK : DiffUtil.ItemCallback<TvShowEntity>() {
        override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
            return oldItem == newItem
        }
    }

    inner class TvShowViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            with(binding) {
                tvTitle.text = tvShow.name
                tvGenre.text = tvShow.voteAverage.toString()

                Glide.with(root.context)
                    .asBitmap()
                    .load(IMAGE_URL + tvShow.posterPath)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemMovieBinding)
    }


    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }
}