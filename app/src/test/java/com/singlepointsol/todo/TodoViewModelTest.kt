package com.singlepointsol.todo

import com.singlepointsol.todo.domain.model.TodoItem
import com.singlepointsol.todo.domain.usecase.AddTodoUseCase
import com.singlepointsol.todo.domain.usecase.GetTodosUseCase
import com.singlepointsol.todo.presentation.viewmodel.TodoViewModel
import com.singlepointsol.todo.presentation.viewmodel.UiState
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TodoViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Mock dependencies
    private val getTodosUseCase: GetTodosUseCase = mockk()
    private val addTodoUseCase: AddTodoUseCase = mockk(relaxed = true)

    // ViewModel instance
    private lateinit var viewModel: TodoViewModel

    @Before
    fun setup() {
        every { getTodosUseCase.invoke() } returns flowOf(emptyList()) // Mock the use case to return empty list
        viewModel = TodoViewModel(getTodosUseCase, addTodoUseCase)
    }

    @Test
    fun `test initial state`() = runTest {
        assertEquals(UiState.Idle, viewModel.uiState.value)
        assertEquals("", viewModel.taskName.value)
        assertEquals(emptyList<TodoItem>(), viewModel.todos.value)
    }

    @Test
    fun `test onTaskNameChange updates state correctly`() = runTest {
        viewModel.onTaskNameChange("New Task")
        assertEquals("New Task", viewModel.taskName.value)
        assertTrue(viewModel.isTaskValid.value)
    }

    @Test
    fun `test onTaskNameChange sets isTaskValid to false when empty`() = runTest {
        viewModel.onTaskNameChange("")
        assertFalse(viewModel.isTaskValid.value)
    }

    @Test
    fun `test addTodo success`() = runTest {
        coEvery { addTodoUseCase(any()) } just Runs

        viewModel.onTaskNameChange("Test Task")
        viewModel.addTodo()

        // Advance time to let delay(3000) complete
        advanceTimeBy(3000)
        advanceUntilIdle()

        assertEquals(UiState.Success, viewModel.uiState.value)
    }
    @Test
    fun `test addTodo failure`() = runTest {
        val exception = Exception("Failed to add TODO")
        coEvery { addTodoUseCase(any()) } throws exception

        viewModel.onTaskNameChange("Test Task")
        viewModel.addTodo()

        // Advance time to let delay(3000) complete
        advanceTimeBy(3000)
        advanceUntilIdle()

        assertTrue(viewModel.showErrorDialog.value) // Error dialog should be shown
        assertEquals(UiState.Error("Failed to add TODO"), viewModel.uiState.value)
    }



}
