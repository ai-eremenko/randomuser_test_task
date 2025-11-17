package com.example.randomuser_test_task

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.randomuser_test_task.data.repository.UsersRepository
import com.example.randomuser_test_task.domain.Gender
import com.example.randomuser_test_task.domain.Nationality
import com.example.randomuser_test_task.domain.UserFilter
import com.example.randomuser_test_task.feature.generateuser.GenerateUserEvent
import com.example.randomuser_test_task.feature.generateuser.GenerateUserSideEffect
import com.example.randomuser_test_task.feature.generateuser.GenerateUserViewModel
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
class GenerateUserViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var usersRepository: UsersRepository
    private lateinit var viewModel: GenerateUserViewModel

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
    fun `state should initialize with correct cameFromUserList value`() = runTest {
        viewModel = GenerateUserViewModel(usersRepository, cameFromUserList = true)
        advanceUntilIdle()

        assert(viewModel.state.value.isCameFromList == true)
    }

    @Test
    fun `onEvent with OnGenderSelected should toggle gender selection`() = runTest {
        viewModel = GenerateUserViewModel(usersRepository, cameFromUserList = false)
        advanceUntilIdle()

        viewModel.onEvent(GenerateUserEvent.OnGenderSelected(Gender.MALE))
        assert(viewModel.state.value.selectedGender == Gender.MALE)

        viewModel.onEvent(GenerateUserEvent.OnGenderSelected(Gender.MALE))
        assert(viewModel.state.value.selectedGender == null)

        viewModel.onEvent(GenerateUserEvent.OnGenderSelected(Gender.FEMALE))
        assert(viewModel.state.value.selectedGender == Gender.FEMALE)
    }

    @Test
    fun `onEvent with OnNationalitySelected should toggle nationality selection`() = runTest {
        viewModel = GenerateUserViewModel(usersRepository, cameFromUserList = false)
        advanceUntilIdle()

        viewModel.onEvent(GenerateUserEvent.OnNationalitySelected(Nationality.US))
        assert(viewModel.state.value.selectedNationalities.contains(Nationality.US))

        viewModel.onEvent(GenerateUserEvent.OnNationalitySelected(Nationality.US))
        assert(!viewModel.state.value.selectedNationalities.contains(Nationality.US))

        viewModel.onEvent(GenerateUserEvent.OnNationalitySelected(Nationality.US))
        viewModel.onEvent(GenerateUserEvent.OnNationalitySelected(Nationality.GB))
        assert(viewModel.state.value.selectedNationalities.contains(Nationality.US))
        assert(viewModel.state.value.selectedNationalities.contains(Nationality.GB))
    }

    @Test
    fun `onEvent with OnBackButtonClicked should emit Finish side effect`() = runTest {
        viewModel = GenerateUserViewModel(usersRepository, cameFromUserList = false)
        advanceUntilIdle()

        val sideEffects = mutableListOf<GenerateUserSideEffect>()
        val job = launch {
            viewModel.sideEffect.collect { sideEffect ->
                sideEffects.add(sideEffect)
            }
        }

        viewModel.onEvent(GenerateUserEvent.OnBackButtonClicked)
        advanceUntilIdle()

        job.cancel()

        assert(sideEffects.size == 1)
        assert(sideEffects[0] == GenerateUserSideEffect.Finish)
    }

    @Test
    fun `onEvent with OnDialogOkButtonClicked should emit NavigateToUserList side effect`() = runTest {
        viewModel = GenerateUserViewModel(usersRepository, cameFromUserList = true)
        advanceUntilIdle()

        val sideEffects = mutableListOf<GenerateUserSideEffect>()
        val job = launch {
            viewModel.sideEffect.collect { sideEffect ->
                sideEffects.add(sideEffect)
            }
        }

        viewModel.onEvent(GenerateUserEvent.OnDialogOkButtonClicked)
        advanceUntilIdle()

        job.cancel()

        assert(sideEffects.size == 1)
        assert(sideEffects[0] == GenerateUserSideEffect.NavigateToUserList(true))
    }

    @Test
    fun `onEvent with OnGenerateButtonClicked on success should emit NavigateToUserList side effect`() = runTest {
        `when`(usersRepository.generateUser(any())).thenReturn(Result.success(Unit))

        viewModel = GenerateUserViewModel(usersRepository, cameFromUserList = false)
        advanceUntilIdle()

        val sideEffects = mutableListOf<GenerateUserSideEffect>()
        val job = launch {
            viewModel.sideEffect.collect { sideEffect ->
                sideEffects.add(sideEffect)
            }
        }

        viewModel.onEvent(GenerateUserEvent.OnGenerateButtonClicked)
        advanceUntilIdle()

        job.cancel()

        assert(sideEffects.size == 1)
        assert(sideEffects[0] == GenerateUserSideEffect.NavigateToUserList(false))
    }

    @Test
    fun `onEvent with OnGenerateButtonClicked on failure should show error dialog`() = runTest {
        `when`(usersRepository.generateUser(any())).thenReturn(Result.failure(Exception("Test error")))

        viewModel = GenerateUserViewModel(usersRepository, cameFromUserList = false)
        advanceUntilIdle()

        viewModel.onEvent(GenerateUserEvent.OnGenerateButtonClicked)
        advanceUntilIdle()

        assert(viewModel.state.value.isErrorDialogShown == true)
    }

    @Test
    fun `onEvent with OnGenerateButtonClicked should update isLoading state`() = runTest {
        `when`(usersRepository.generateUser(any())).thenReturn(Result.success(Unit))

        viewModel = GenerateUserViewModel(usersRepository, cameFromUserList = false)
        advanceUntilIdle()

        viewModel.onEvent(GenerateUserEvent.OnGenerateButtonClicked)
        advanceUntilIdle()

        assert(!viewModel.state.value.isLoading)
    }

    @Test
    fun `onEvent with OnGenerateButtonClicked should call repository with correct filter`() = runTest {
        `when`(usersRepository.generateUser(any())).thenReturn(Result.success(Unit))

        viewModel = GenerateUserViewModel(usersRepository, cameFromUserList = false)
        advanceUntilIdle()

        viewModel.onEvent(GenerateUserEvent.OnGenderSelected(Gender.MALE))
        viewModel.onEvent(GenerateUserEvent.OnNationalitySelected(Nationality.US))
        viewModel.onEvent(GenerateUserEvent.OnNationalitySelected(Nationality.GB))

        viewModel.onEvent(GenerateUserEvent.OnGenerateButtonClicked)
        advanceUntilIdle()

        verify(usersRepository).generateUser(
            UserFilter(
                gender = Gender.MALE,
                nationalities = setOf(Nationality.US, Nationality.GB),
                resultsCount = 20
            )
        )
    }

    @Test
    fun `state should have correct initial values`() = runTest {
        viewModel = GenerateUserViewModel(usersRepository, cameFromUserList = false)
        advanceUntilIdle()

        val initialState = viewModel.state.value
        assert(!initialState.isCameFromList)
        assert(initialState.selectedGender == null)
        assert(initialState.selectedNationalities.isEmpty())
        assert(!initialState.isLoading)
        assert(!initialState.isErrorDialogShown)
    }
}