package com.ivonicchio.catbreedsapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.ivonicchio.catbreedsapp.R
import com.ivonicchio.catbreedsapp.core.extensions.fadeInAnimation
import com.ivonicchio.catbreedsapp.core.extensions.gone
import com.ivonicchio.catbreedsapp.core.extensions.visible
import com.ivonicchio.catbreedsapp.databinding.ActivityCatBreedsListBinding
import com.ivonicchio.catbreedsapp.model.CatBreed
import com.ivonicchio.catbreedsapp.ui.adapter.CatBreedsListAdapter
import com.ivonicchio.catbreedsapp.ui.adapter.ListItemClickListener
import com.ivonicchio.catbreedsapp.viewModel.CatBreedsListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class CatBreedsListActivity : AppCompatActivity(), ListItemClickListener {

    private lateinit var binding: ActivityCatBreedsListBinding
    private val viewModel: CatBreedsListViewModel by viewModel()

    private var isTabletLayout = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cat_breeds_list)
        binding.lifecycleOwner = this

        checkIfIsTabletLayout()
        setupToolbarForTabletLayout()
        setupObservables()
        setupErrorClickListener()
    }

    private fun checkIfIsTabletLayout() {
        isTabletLayout = binding.mainScreenLayout.itemDetailContainer != null
    }

    private fun setupToolbarForTabletLayout() {
        if(isTabletLayout) {
            setSupportActionBar(binding.mainScreenLayout.toolbar)
        }
    }

    private fun setupObservables() {
        setupCatBreedsListObserver()
        setupRequestErrorObserver()
    }

    private fun setupCatBreedsListObserver() {
        viewModel.catBreedsList.observe(this, Observer { catBreedsList ->
            catBreedsList?.let { list ->
                showLayout(list)
            }
        })
    }

    private fun setupRequestErrorObserver() {
        viewModel.requestError.observe(this, Observer { error ->
            if (error) {
                showErrorLayout()
            }
        })
    }

    private fun showLayout(list: List<CatBreed>) {
        setupRecyclerView(list)
        binding.progressBar.gone()
        binding.mainScreenLayout.clMainLayout.fadeInAnimation()
    }

    private fun showErrorLayout() {
        binding.progressBar.gone()
        binding.mainScreenLayout.iErrorLayout.fadeInAnimation()
    }

    private fun setupRecyclerView(catBreedsList: List<CatBreed>) {
        binding.mainScreenLayout.rvCatBreedsNameList.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        binding.mainScreenLayout.rvCatBreedsNameList.adapter =
            CatBreedsListAdapter(catBreedsList, this)
    }

    override fun onListItemClick(catBreed: CatBreed) {
        if (isTabletLayout) {
            openBreedDetailsOnTabletLayout(catBreed)
        } else {
            openBreedDetailsOnPhoneLayout(catBreed)
        }
    }

    private fun openBreedDetailsOnTabletLayout(item: CatBreed) {
        val fragment = CatBreedDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(CatBreedDetailFragment.ARG_CAT_BREED, item)
            }
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.item_detail_container, fragment)
            .commit()
    }

    private fun openBreedDetailsOnPhoneLayout(item: CatBreed) {
        val intent = Intent(this, CatBreedDetailActivity::class.java).apply {
            putExtra(CatBreedDetailFragment.ARG_CAT_BREED, item)
        }
        startActivity(intent)
    }

    private fun setupErrorClickListener() {
        binding.mainScreenLayout.iErrorLayout.setOnClickListener {
            binding.mainScreenLayout.iErrorLayout.gone()
            binding.progressBar.visible()

            viewModel.clearRequestErrorEvent()
            viewModel.getCatBreedsList()
        }
    }
}