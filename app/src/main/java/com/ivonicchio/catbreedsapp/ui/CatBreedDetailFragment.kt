package com.ivonicchio.catbreedsapp.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ivonicchio.catbreedsapp.R
import com.ivonicchio.catbreedsapp.core.extensions.configFragmentToolbar
import com.ivonicchio.catbreedsapp.core.extensions.fadeInAnimation
import com.ivonicchio.catbreedsapp.core.extensions.gone
import com.ivonicchio.catbreedsapp.core.extensions.visible
import com.ivonicchio.catbreedsapp.databinding.CatBreedDetailBinding
import com.ivonicchio.catbreedsapp.model.CatBreed
import com.ivonicchio.catbreedsapp.model.CatBreedImage
import com.ivonicchio.catbreedsapp.viewModel.CatBreedDetailViewModel
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.getViewModel


class CatBreedDetailFragment : Fragment() {

    private lateinit var binding: CatBreedDetailBinding
    private var viewModel: CatBreedDetailViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        arguments?.getParcelable<CatBreed>(ARG_CAT_BREED)?.let {
            getKoin().setProperty(CatBreedDetailViewModel.CONST_CAT_BREED, it)
            viewModel = getViewModel()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.cat_breed_detail,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupToolbarForPhoneLayout()
        setupObservers()
        setupErrorClickListener()

        return binding.root
    }

    private fun setupToolbarForPhoneLayout() {
        binding.toolbar?.let{
            configFragmentToolbar(it)
        }
    }

    private fun setupObservers() {
        setupCatBreedImageObserver()
        setupRequestErrorObserver()
    }

    private fun setupErrorClickListener() {
        binding.iErrorLayout.setOnClickListener {
            binding.progressBar.visible()
            binding.iErrorLayout.gone()

            viewModel?.clearRequestErrorEvent()
            viewModel?.getCatBreedImage()
        }
    }

    private fun setupCatBreedImageObserver() {
        viewModel?.catBreedsImage?.observe(this, Observer {
            startLoadingImage(it)
        })
    }

    private fun startLoadingImage(it: List<CatBreedImage>) {
        val requestOptions = RequestOptions().transform(RoundedCorners(40))
        Glide.with(this)
            .load(it[0].url)
            .apply(requestOptions)
            .error(R.drawable.ic_baseline_error)
            .listener(object : RequestListener<Drawable> {

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    showCatBreedDetailLayout()
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    showCatBreedDetailLayout()
                    return false
                }
            })
            .into(binding.ivCatImage)
    }

    private fun showCatBreedDetailLayout() {
        binding.progressBar.gone()
        binding.clCatBreedDetailsLayout.fadeInAnimation()
    }

    private fun setupRequestErrorObserver() {
        viewModel?.requestError?.observe(this, Observer { error ->
            if (error) {
                showErrorMessage()
            }
        })
    }

    private fun showErrorMessage() {
        binding.progressBar.gone()
        binding.iErrorLayout.fadeInAnimation()
    }

    companion object {
        const val ARG_CAT_BREED = "cat_breed"
    }
}