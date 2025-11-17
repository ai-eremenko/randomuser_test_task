package com.example.randomuser_test_task

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.randomuser_test_task.data.repository.UsersRepository
import com.example.randomuser_test_task.domain.Picture
import com.example.randomuser_test_task.domain.User
import com.example.randomuser_test_task.feature.userdetail.UserDetailEvent
import com.example.randomuser_test_task.feature.userdetail.UserDetailSideEffect
import com.example.randomuser_test_task.feature.userdetail.UserDetailViewModel
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
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
class UserDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var usersRepository: UsersRepository
    private lateinit var viewModel: UserDetailViewModel

    private val testUserId = "test-user-id"
    private val testUser = User(
        id = testUserId,
        firstName = "John Doe",
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
    fun `init should load user from repository`() = runTest {
        `when`(usersRepository.getUserById(testUserId)).thenReturn(testUser)

        viewModel = UserDetailViewModel(usersRepository, testUserId)
        advanceUntilIdle()

        verify(usersRepository).getUserById(testUserId)
        assert(viewModel.state.value.user == testUser)
    }

    @Test
    fun `onEvent with OnBackButtonClicked should emit Finish side effect`() = runTest {
        `when`(usersRepository.getUserById(any())).thenReturn(null)

        viewModel = UserDetailViewModel(usersRepository, testUserId)
        advanceUntilIdle()

        val sideEffects = mutableListOf<UserDetailSideEffect>()
        val job = launch {
            viewModel.sideEffect.collect { sideEffect ->
                sideEffects.add(sideEffect)
            }
        }

        viewModel.onEvent(UserDetailEvent.OnBackButtonClicked)
        advanceUntilIdle()
        job.cancel()

        assert(sideEffects.size == 1)
        assert(sideEffects[0] == UserDetailSideEffect.Finish)
    }

    @Test
    fun `onEvent with OnTabSelected should update selected tab index`() = runTest {
        `when`(usersRepository.getUserById(any())).thenReturn(null)

        viewModel = UserDetailViewModel(usersRepository, testUserId)
        advanceUntilIdle()

        val testTabIndex = 1
        viewModel.onEvent(UserDetailEvent.OnTabSelected(testTabIndex))

        assert(viewModel.state.value.selectedTabIndex == testTabIndex)
    }

    @Test
    fun `onEvent with OnPhoneClicked with valid phone should emit MakePhoneCall side effect`() = runTest {
        `when`(usersRepository.getUserById(testUserId)).thenReturn(testUser)

        viewModel = UserDetailViewModel(usersRepository, testUserId)
        advanceUntilIdle()

        val sideEffects = mutableListOf<UserDetailSideEffect>()
        val job = launch {
            viewModel.sideEffect.collect { sideEffect ->
                sideEffects.add(sideEffect)
            }
        }

        viewModel.onEvent(UserDetailEvent.OnPhoneClicked)
        advanceUntilIdle()

        job.cancel()

        assert(sideEffects.size == 1)
        assert(sideEffects[0] == UserDetailSideEffect.MakePhoneCall(testUser.phone))
    }

    @Test
    fun `onEvent with OnPhoneClicked with empty phone should not emit side effect`() = runTest {
        val userWithoutPhone = testUser.copy(phone = "")
        `when`(usersRepository.getUserById(testUserId)).thenReturn(userWithoutPhone)

        viewModel = UserDetailViewModel(usersRepository, testUserId)
        advanceUntilIdle()

        var sideEffectEmitted = false
        val job = launch {
            viewModel.sideEffect.collect {
                sideEffectEmitted = true
            }
        }

        viewModel.onEvent(UserDetailEvent.OnPhoneClicked)
        advanceUntilIdle()

        job.cancel()

        assert(!sideEffectEmitted)
    }

    @Test
    fun `onEvent with OnEmailClicked with valid email should emit SendEmail side effect`() = runTest {
        `when`(usersRepository.getUserById(testUserId)).thenReturn(testUser)

        viewModel = UserDetailViewModel(usersRepository, testUserId)
        advanceUntilIdle()

        val sideEffects = mutableListOf<UserDetailSideEffect>()
        val job = launch {
            viewModel.sideEffect.collect { sideEffect ->
                sideEffects.add(sideEffect)
            }
        }

        viewModel.onEvent(UserDetailEvent.OnEmailClicked)
        advanceUntilIdle()

        job.cancel()

        assert(sideEffects.size == 1)
        assert(sideEffects[0] == UserDetailSideEffect.SendEmail(testUser.email))
    }

    @Test
    fun `onEvent with OnEmailClicked with empty email should not emit side effect`() = runTest {
        val userWithoutEmail = testUser.copy(email = "")
        `when`(usersRepository.getUserById(testUserId)).thenReturn(userWithoutEmail)

        viewModel = UserDetailViewModel(usersRepository, testUserId)
        advanceUntilIdle()

        var sideEffectEmitted = false
        val job = launch {
            viewModel.sideEffect.collect {
                sideEffectEmitted = true
            }
        }

        viewModel.onEvent(UserDetailEvent.OnEmailClicked)
        advanceUntilIdle()

        job.cancel()

        assert(!sideEffectEmitted)
    }

    @Test
    fun `onEvent with OnLocationClicked with valid coordinates should emit OpenLocation side effect`() = runTest {
        `when`(usersRepository.getUserById(testUserId)).thenReturn(testUser)

        viewModel = UserDetailViewModel(usersRepository, testUserId)
        advanceUntilIdle()

        val sideEffects = mutableListOf<UserDetailSideEffect>()
        val job = launch {
            viewModel.sideEffect.collect { sideEffect ->
                sideEffects.add(sideEffect)
            }
        }

        viewModel.onEvent(UserDetailEvent.OnLocationClicked)
        advanceUntilIdle()

        job.cancel()

        assert(sideEffects.size == 1)
        assert(sideEffects[0] == UserDetailSideEffect.OpenLocation(testUser.latitude, testUser.longitude))
    }

    @Test
    fun `state should have initial values`() = runTest {
        `when`(usersRepository.getUserById(any())).thenReturn(null)

        viewModel = UserDetailViewModel(usersRepository, testUserId)
        advanceUntilIdle()

        val initialState = viewModel.state.value
        assert(initialState.user == null)
        assert(initialState.selectedTabIndex == 0)
    }
}