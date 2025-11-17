package com.example.randomuser_test_task

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.randomuser_test_task.data.repository.UsersRepository
import com.example.randomuser_test_task.domain.Picture
import com.example.randomuser_test_task.domain.User
import com.example.randomuser_test_task.feature.userslist.UsersListEvent
import com.example.randomuser_test_task.feature.userslist.UsersListSideEffect
import com.example.randomuser_test_task.feature.userslist.UsersListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class UsersListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var usersRepository: UsersRepository
    private lateinit var viewModel: UsersListViewModel

    private val testUser = User(
        id = "test-user-id",
        firstName = "John",
        phone = "+1234567890",
        email = "john@example.com",
        latitude = 40.7128,
        longitude = -74.0060,
        gender = "male",
        title = "Mr",
        lastName = "Doe",
        cell = "+1234567890",
        picture = Picture(
            "https://example.com/photo.jpg",
            "https://example.com/photo.jpg",
            "https://example.com/photo.jpg"
        ),
        nationality = "US",
        location = "New York",
        street = "123 Main St",
        city = "New York",
        state = "NY",
        country = "USA",
        age = 30,
        dateOfBirth = "1993-05-15",
        registeredDate = "2020-01-01",
        username = "johndoe"
    )

    private val testUsersList = listOf(
        testUser,
        testUser.copy(id = "test-user-id-2", firstName = "Jane")
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        usersRepository = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onEvent with OnFetchData should load users from repository and update state`() = runTest {
        `when`(usersRepository.getUsers()).thenReturn(testUsersList)

        viewModel = UsersListViewModel(usersRepository)
        advanceUntilIdle()

        viewModel.onEvent(UsersListEvent.OnFetchData)
        advanceUntilIdle()

        verify(usersRepository).getUsers()
        val currentState = viewModel.state.value
        assert(currentState.listUsers == testUsersList)
        assert(!currentState.isEmpty)
        assert(!currentState.isLoading)
    }

    @Test
    fun `onEvent with OnFetchData should set isEmpty to true when users list is empty`() = runTest {
        `when`(usersRepository.getUsers()).thenReturn(emptyList())

        viewModel = UsersListViewModel(usersRepository)
        advanceUntilIdle()

        viewModel.onEvent(UsersListEvent.OnFetchData)
        advanceUntilIdle()

        val currentState = viewModel.state.value
        assert(currentState.listUsers.isEmpty())
        assert(currentState.isEmpty)
        assert(!currentState.isLoading)
    }

    @Test
    fun `onEvent with OnAddButtonClicked should emit NavigateToGenerateUser side effect`() = runTest {
        `when`(usersRepository.getUsers()).thenReturn(emptyList())

        viewModel = UsersListViewModel(usersRepository)
        advanceUntilIdle()

        val sideEffects = mutableListOf<UsersListSideEffect>()
        val job = launch {
            viewModel.sideEffect.collect { sideEffect ->
                sideEffects.add(sideEffect)
            }
        }

        viewModel.onEvent(UsersListEvent.OnAddButtonClicked)
        advanceUntilIdle()

        job.cancel()

        assert(sideEffects.size == 1)
        assert(sideEffects[0] == UsersListSideEffect.NavigateToGenerateUser)
    }

    @Test
    fun `onEvent with OnUserClicked should emit NavigateToUserDetail side effect`() = runTest {
        `when`(usersRepository.getUsers()).thenReturn(emptyList())

        viewModel = UsersListViewModel(usersRepository)
        advanceUntilIdle()

        val sideEffects = mutableListOf<UsersListSideEffect>()
        val job = launch {
            viewModel.sideEffect.collect { sideEffect ->
                sideEffects.add(sideEffect)
            }
        }

        viewModel.onEvent(UsersListEvent.OnUserClicked(testUser))
        advanceUntilIdle()

        job.cancel()

        assert(sideEffects.size == 1)
        assert(sideEffects[0] == UsersListSideEffect.NavigateToUserDetail(testUser))
    }

    @Test
    fun `state should have initial values`() = runTest {
        `when`(usersRepository.getUsers()).thenReturn(emptyList())

        viewModel = UsersListViewModel(usersRepository)
        advanceUntilIdle()

        val initialState = viewModel.state.value
        assert(initialState.listUsers.isEmpty())
        assert(!initialState.isEmpty)
        assert(!initialState.isLoading)
    }

    @Test
    fun `multiple side effects should be collected correctly`() = runTest {
        `when`(usersRepository.getUsers()).thenReturn(emptyList())

        viewModel = UsersListViewModel(usersRepository)
        advanceUntilIdle()

        val sideEffects = mutableListOf<UsersListSideEffect>()
        val job = launch {
            viewModel.sideEffect.collect { sideEffect ->
                sideEffects.add(sideEffect)
            }
        }

        viewModel.onEvent(UsersListEvent.OnAddButtonClicked)
        viewModel.onEvent(UsersListEvent.OnUserClicked(testUser))
        advanceUntilIdle()

        job.cancel()

        assert(sideEffects.size == 2)
        assert(sideEffects[0] == UsersListSideEffect.NavigateToGenerateUser)
        assert(sideEffects[1] == UsersListSideEffect.NavigateToUserDetail(testUser))
    }
}