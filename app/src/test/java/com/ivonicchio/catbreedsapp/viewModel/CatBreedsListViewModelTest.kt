package com.ivonicchio.catbreedsapp.viewModel

import com.ivonicchio.catbreedsapp.InstantExecutorExtension
import com.ivonicchio.catbreedsapp.core.network.Resource
import com.ivonicchio.catbreedsapp.data.catsRepository.CatsRepositoryImpl
import com.ivonicchio.catbreedsapp.model.CatBreed
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
class CatBreedsListViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val repository = mockk<CatsRepositoryImpl>()

    @BeforeEach
    fun setupBeforeEach() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `when the request is successful, returns a cat breed list`() =
        testDispatcher.runBlockingTest {
            // Arrange
            val breedListResponse =
                listOf(CatBreed("abys", "Abyssinian", "origin", "temperament", "description"))
            every { runBlocking { repository.getCatBreedsList() } } answers {
                Resource.success(breedListResponse)
            }

            // Act
            val viewModel = CatBreedsListViewModel(repository, testDispatcher)

            // Assert
            assertEquals(breedListResponse, viewModel.catBreedsList.value)
        }

    @Test
    fun `when the request fails, returns a request error event`() = testDispatcher.runBlockingTest {
        // Arrange
        every { runBlocking { repository.getCatBreedsList() } } answers {
            Resource.error()
        }

        // Act
        val viewModel = CatBreedsListViewModel(repository, testDispatcher)

        // Assert
        assertEquals(true, viewModel.requestError.value)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}