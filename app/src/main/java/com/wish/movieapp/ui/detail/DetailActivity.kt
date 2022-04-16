package com.wish.movieapp.ui.detail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.appbar.AppBarLayout
import com.wish.movieapp.BuildConfig.IMAGE_URL
import com.wish.movieapp.R
import com.wish.movieapp.data.local.entity.MovieEntity
import com.wish.movieapp.data.local.entity.TvShowEntity
import com.wish.movieapp.databinding.ActivityDetailBinding
import com.wish.movieapp.ui.detail.DetailViewModel.Companion.MOVIE
import com.wish.movieapp.ui.detail.DetailViewModel.Companion.TV_SHOW
import com.wish.movieapp.viewmodel.ViewModelFactory
import com.wish.movieapp.vo.Status
import kotlin.math.abs

class DetailActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {
    companion object {
        const val EXTRA_FILM = "extra_film"
        const val EXTRA_CATEGORY = "extra_category"
    }

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var dataCategory: String? = null

    private val percentageToShowImage = 20
    private var mMaxScrollSize = 0
    private var mIsImageHidden = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        supportActionBar?.hide()

        showProgressBar(true)

        detailBinding.toolbar.setNavigationOnClickListener { onBackPressed() }
        detailBinding.appbar.addOnOffsetChangedListener(this)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            val dataId = extras.getString(EXTRA_FILM)
            dataCategory = extras.getString(EXTRA_CATEGORY)

            if (dataId != null && dataCategory != null) {
                viewModel.setDetail(dataId, dataCategory.toString())
                setupState()
                if (dataCategory == MOVIE) {
                    viewModel.getDetailMovie().observe(this, { detail ->
                        when (detail.status) {
                            Status.LOADING -> showProgressBar(true)
                            Status.SUCCESS -> {
                                if (detail.data != null) {
                                    showProgressBar(false)
                                    populateDataDetailForMovie(detail.data)
                                }
                            }
                            Status.ERROR -> {
                                showProgressBar(false)
                                Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                } else if (dataCategory == TV_SHOW) {
                    viewModel.getDetailTvShow().observe(this, { detail ->
                        when (detail.status) {
                            Status.LOADING -> showProgressBar(true)
                            Status.SUCCESS -> {
                                if (detail.data != null) {
                                    showProgressBar(false)
                                    populateDataDetailTvShow(detail.data)
                                }
                            }
                            Status.ERROR -> {
                                showProgressBar(false)
                                Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }
            }
        }
    }

    private fun populateDataDetailForMovie(movie: MovieEntity) {
        with(movie) {
            val genreDurationText = resources.getString(R.string.genre_duration_text, this.genres, this.runtime.toString())

            detailBinding.tvDetailGenreDuration.text = genreDurationText
            detailBinding.collapsing.title = this.title
            detailBinding.tvDetailOverview.text = this.overview

            Glide.with(this@DetailActivity)
                .asBitmap()
                .load(IMAGE_URL + this.posterPath)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        detailBinding.ivDetail.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

            Glide.with(this@DetailActivity)
                .load(IMAGE_URL + this.backdropPath)
                .into(detailBinding.ivBackdrop)

            detailBinding.ivDetail.tag = this.posterPath
            detailBinding.ivBackdrop.tag = this.backdropPath

            showProgressBar(false)
        }
    }


    private fun populateDataDetailTvShow(tvShow: TvShowEntity) {
        with(tvShow) {
            val genreDurationText = resources.getString(R.string.genre_duration_text, this.genres, this.runtime.toString())

            detailBinding.tvDetailGenreDuration.text = genreDurationText
            detailBinding.collapsing.title = this.name
            detailBinding.tvDetailOverview.text = this.overview

            Glide.with(this@DetailActivity)
                .asBitmap()
                .apply(RequestOptions.placeholderOf(R.drawable.ic_movie_poster_placeholder))
                .load(IMAGE_URL + this.posterPath)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        detailBinding.ivDetail.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

            Glide.with(this@DetailActivity)
                .load(IMAGE_URL + this.backdropPath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_movie_poster_placeholder))
                .into(detailBinding.ivBackdrop)

            detailBinding.ivDetail.tag = this.posterPath
            detailBinding.ivBackdrop.tag = this.backdropPath

            showProgressBar(false)
        }
    }


    private fun setupState() {
        if (dataCategory == MOVIE) {
            viewModel.getDetailMovie().observe(this, { movie ->
                when (movie.status) {
                    Status.LOADING -> showProgressBar(true)
                    Status.SUCCESS -> {
                        if (movie.data != null) {
                            showProgressBar(false)
                            val state = movie.data.isFav
                        }
                    }
                    Status.ERROR -> {
                        showProgressBar(false)
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else if (dataCategory == TV_SHOW) {
            viewModel.getDetailTvShow().observe(this, { tvShow ->
                when (tvShow.status) {
                    Status.LOADING -> showProgressBar(true)
                    Status.SUCCESS -> {
                        if (tvShow.data != null) {
                            showProgressBar(false)
                            val state = tvShow.data.isFav
                        }
                    }
                    Status.ERROR -> {
                        showProgressBar(false)
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun showProgressBar(state: Boolean) {
        detailBinding.progressBar.isVisible = state
        detailBinding.appbar.isInvisible = state
        detailBinding.nestedScrollView.isInvisible = state
        detailBinding.fabAddToFavorite.isInvisible = state
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (mMaxScrollSize == 0) mMaxScrollSize = appBarLayout?.totalScrollRange ?: 0

        val currentScrollPercentage: Int = (abs(verticalOffset) * 100 / mMaxScrollSize)

        if (currentScrollPercentage >= percentageToShowImage) {
            if (!mIsImageHidden) {
                mIsImageHidden = true
            }
        }

        if (currentScrollPercentage < percentageToShowImage) {
            if (mIsImageHidden) {
                mIsImageHidden = false
            }
        }
    }
}