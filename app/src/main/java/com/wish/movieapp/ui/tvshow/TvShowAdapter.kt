package com.wish.movieapp.ui.tvshow

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.wish.movieapp.BuildConfig.IMAGE_URL
import com.wish.movieapp.R
import com.wish.movieapp.data.local.entity.TvShowEntity
import com.wish.movieapp.databinding.ItemMovieBinding

class TvShowAdapter : PagedListAdapter<TvShowEntity, TvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class TvShowViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            with(binding) {
                tvTitle.text = tvShow.name
                tvReleaseDate.text = tvShow.releaseDate
                tvRating.text = tvShow.voteAverage.toString()

                Glide.with(root.context)
                    .asBitmap()
                    .load(IMAGE_URL + tvShow.posterPath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_movie_poster_placeholder))
                    .transform(RoundedCorners(28))
                    .into(object : CustomTarget<Bitmap>() {
                        @RequiresApi(Build.VERSION_CODES.M)
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            ivPoster.setImageBitmap(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
                itemView.setOnClickListener { onItemClickCallback.onItemClicked(tvShow.id.toString()) }
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

    interface OnItemClickCallback {
        fun onItemClicked(id: String)
    }
}