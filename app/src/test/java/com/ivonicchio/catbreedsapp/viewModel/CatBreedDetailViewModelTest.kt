package com.ivonicchio.catbreedsapp.viewModel

import com.ivonicchio.catbreedsapp.InstantExecutorExtension
import com.ivonicchio.catbreedsapp.core.network.Resource
import com.ivonicchio.catbreedsapp.data.catsRepository.CatsRepositoryImpl
import com.ivonicchio.catbreedsapp.model.CatBreed
import com.ivonicchio.catbreedsapp.model.CatBreedImage
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
@ExperimentalCoroutinesApi
class CatBreedDetailViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val repository = mockk<CatsRepositoryImpl>()
    private val catBreed = CatBreed("aege", "Aegean", "origin", "temperament", "description")

    @BeforeEach
    fun setupBeforeEach() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `when the request is successful, returns a cat breed image list`() =
        testDispatcher.runBlockingTest {
            // Arrange
            val breedImageListResponse = listOf(CatBreedImage("url"))
            every { runBlocking { repository.getCatBreedImage(catBreed.id) } } answers {
                Resource.success(breedImageListResponse)
            }

            // Act
            val viewModel = CatBreedDetailViewModel(repository, catBreed, testDispatcher)

            // Assert
            assertEquals(breedImageListResponse, viewModel.catBreedsImage.value)
        }

    @Test
    fun `when the request fails, returns a request error event`() = testDispatcher.runBlockingTest {
        // Arrange
        every { runBlocking { repository.getCatBreedImage(catBreed.id) } } answers {
            Resource.error()
        }

        // Act
        val viewModel = CatBreedDetailViewModel(repository, catBreed, testDispatcher)

        // Assert
        assertEquals(true, viewModel.requestError.value)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}