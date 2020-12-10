package com.ivonicchio.catbreedsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ivonicchio.catbreedsapp.R
import com.ivonicchio.catbreedsapp.databinding.ActivityCatBreedDetailBinding

class CatBreedDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatBreedDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cat_breed_detail)

        setupFragment(savedInstanceState)
    }

    private fun setupFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            var fragment = CatBreedDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(
                        CatBreedDetailFragment.ARG_CAT_BREED,
                        intent.getParcelableExtra(CatBreedDetailFragment.ARG_CAT_BREED)
                    )
                }
            }
            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
        }
    }
}


