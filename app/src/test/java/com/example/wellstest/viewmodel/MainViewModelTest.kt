package com.example.wellstest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.wellstest.MockRepo
import com.example.wellstest.model.NewsResponse
import com.example.wellstest.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var dataObserver: Observer<NewsResponse>
    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<NewsResponse>

    @Mock
    private lateinit var onStateObserver: Observer<State>
    @Captor
    private lateinit var onStateCaptor: ArgumentCaptor<State>

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mainViewModel = MainViewModel(MockRepo())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `success status after calling the get news`() {
        mainViewModel.newsResponse.observeForever(dataObserver)
        mainViewModel.getNews()

        Mockito.verify(dataObserver, Mockito.times(1))
            .onChanged(argumentCaptor.capture())
        Assert.assertEquals("Success", argumentCaptor.value.status)
    }

    @Test
    fun `checking all states in order after api call`() {
        mainViewModel.apiStateEvent.observeForever(onStateObserver)
        mainViewModel.getNews()

        Mockito.verify(onStateObserver, Mockito.times(3))
            .onChanged(onStateCaptor.capture())

        val values = onStateCaptor.allValues;
        Assert.assertTrue(values[0] is State.Loading)
        Assert.assertTrue(values[1] is State.Success)
        Assert.assertTrue(values[2] is State.Loading)
    }

    @Test
    fun `checking the api is triggered on lifecycle method - onStart`() {
        mainViewModel.newsResponse.observeForever(dataObserver)
        mainViewModel.onLifeCycleStart()

        Mockito.verify(dataObserver, Mockito.times(1))
            .onChanged(argumentCaptor.capture())
        Assert.assertEquals("Success", argumentCaptor.value.status)
    }

}