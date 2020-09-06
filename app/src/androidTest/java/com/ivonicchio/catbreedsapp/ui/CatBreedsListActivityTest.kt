package com.ivonicchio.catbreedsapp.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.ivonicchio.catbreedsapp.R
import com.ivonicchio.catbreedsapp.core.network.Resource
import com.ivonicchio.catbreedsapp.data.catsRepository.CatsRepository
import com.ivonicchio.catbreedsapp.matchers.RecyclerViewMatcher
import com.ivonicchio.catbreedsapp.model.CatBreed
import com.ivonicchio.catbreedsapp.model.CatBreedImage
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

class CatBreedsListActivityTest : KoinTest {

    private val repository = mockk<CatsRepository>()

    @get:Rule
    var activityRule: ActivityTestRule<CatBreedsListActivity> =
        ActivityTestRule(CatBreedsListActivity::class.java, true, false)

    private val breedListResponse = listOf(
        CatBreed(
            "abys",
            "Abyssinian",
            "Abyssinian Origin",
            "Abyssinian Temperament",
            "Abyssinian Description"
        ),
        CatBreed(
            "aege",
            "Aegean",
            "Aegean Origin",
            "Aegean Temperament",
            "Aegean Description"
        ),
        CatBreed(
            "abob",
            "American Bobtail",
            "origin",
            "temperament",
            "description"
        ),
        CatBreed(
            "acur",
            "American Curl",
            "origin",
            "temperament",
            "description"
        )
    )

    @Before
    fun init() {
        loadKoinModules(module {
            factory(override = true) { repository }
        })
    }

    private fun mockSuccessfulBreedListResponse() {
        every { runBlocking { repository.getCatBreedsList() } } answers {
            Resource.success(breedListResponse)
        }
    }

    @Test
    fun whenRequestIsSuccessful_checkIfListIsDisplayingItemsCorrectly() {
        // Assert
        mockSuccessfulBreedListResponse()

        // Act
        activityRule.launchActivity(null)

        // Assert
        onView(withId(R.id.rv_cat_breeds_name_list))
            .check(matches(isDisplayed()))

        onView(
            RecyclerViewMatcher(R.id.rv_cat_breeds_name_list)
                .atPositionOnView(0, R.id.tv_cat_breed_name)
        )
            .check(matches(isDisplayed()))
            .check(matches(withText(breedListResponse[0].name)))

        onView(
            RecyclerViewMatcher(R.id.rv_cat_breeds_name_list)
                .atPositionOnView(1, R.id.tv_cat_breed_name)
        )
            .check(matches(isDisplayed()))
            .check(matches(withText(breedListResponse[1].name)))

        onView(
            RecyclerViewMatcher(R.id.rv_cat_breeds_name_list)
                .atPositionOnView(2, R.id.tv_cat_breed_name)
        )
            .check(matches(isDisplayed()))
            .check(matches(withText(breedListResponse[2].name)))

        onView(
            RecyclerViewMatcher(R.id.rv_cat_breeds_name_list)
                .atPositionOnView(3, R.id.tv_cat_breed_name)
        )
            .check(matches(isDisplayed()))
            .check(matches(withText(breedListResponse[3].name)))

        activityRule.finishActivity()
    }

    @Test
    fun whenRequestIsSuccessful_checkListItemsClick() {
        // Arrange
        mockSuccessfulBreedListResponse()

        val breedImageListResponse = listOf(CatBreedImage("url"))

        every { runBlocking { repository.getCatBreedImage(breedListResponse[0].id) } } answers {
            Resource.success(breedImageListResponse)
        }

        every { runBlocking { repository.getCatBreedImage(breedListResponse[1].id) } } answers {
            Resource.success(breedImageListResponse)
        }

        // Act
        activityRule.launchActivity(null)

        //Assert
        onView(withId(R.id.rv_cat_breeds_name_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        onView(withId(R.id.tv_breed_name))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText(breedListResponse[0].name)))

        onView(withId(R.id.tv_breed_origin))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText(breedListResponse[0].origin)))

        onView(withId(R.id.tv_breed_temperament))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText(breedListResponse[0].temperament)))

        onView(withId(R.id.tv_breed_description))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(withText(breedListResponse[0].description)))

        activityRule.finishActivity()
    }

    @Test
    fun whenRequestFails_checkIfErrorMessageIsShowingCorrectly() {
        // Assert
        every { runBlocking { repository.getCatBreedsList() } } answers {
            Resource.error()
        }

        // Act
        activityRule.launchActivity(null)

        // Assert
        onView(withId(R.id.rv_cat_breeds_name_list))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.i_error_layout))
            .check(matches(isDisplayed()))

        activityRule.finishActivity()
    }
}