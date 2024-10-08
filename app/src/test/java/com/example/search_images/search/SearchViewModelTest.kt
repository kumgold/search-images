package com.example.search_images.search

import com.example.data.data.NetworkImage
import com.example.data.repository.ImageRepository
import com.example.data.repository.SearchRepository
import com.example.search_images.MainCoroutineRule
import com.example.search_images.data.FakeImageDao
import com.example.search_images.data.FakeImageRepository
import com.example.search_images.data.FakeSearchRepository
import com.example.search_images.ui.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel

    private lateinit var searchRepository: SearchRepository
    private lateinit var imageRepository: ImageRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val images = listOf(
        NetworkImage(
            width = 100,
            height = 100,
            imageUrl = "image url"
        ),
        NetworkImage(
            width = 100,
            height = 100,
            imageUrl = "image url"
        )
    )

    @Before
    fun setup() {
        val fakeImageDao = FakeImageDao()
        imageRepository = FakeImageRepository(fakeImageDao)
        searchRepository = FakeSearchRepository(images)

        viewModel = SearchViewModel(searchRepository, imageRepository)
    }

    @Test
    fun searchImages() = runTest {
        val fakeApiResult = searchRepository.searchImages("test", 1)

        Assert.assertEquals(images, fakeApiResult)
    }

    @Test
    fun insertImage() = runTest {
        viewModel.insertImage(
            NetworkImage(
                imageUrl = "test image url",
                width = 0,
                height = 0
            )
        )

        Assert.assertEquals("test image url", imageRepository.getAllImages().first()[0].imageUrl)
    }
}
