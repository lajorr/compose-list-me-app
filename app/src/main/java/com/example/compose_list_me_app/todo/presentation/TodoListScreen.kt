package com.example.compose_list_me_app.todo.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_list_me_app.R
import com.example.compose_list_me_app.common.composables.ElevatedCard
import com.example.compose_list_me_app.common.composables.ErrorText
import com.example.compose_list_me_app.common.composables.MyAppBar
import com.example.compose_list_me_app.todo.domain.models.Todo
import com.example.compose_list_me_app.ui.theme.PrimaryColor
import kotlinx.serialization.Serializable

@Serializable
data class TodoListScreenObject(val userId: Int)

@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
    onPop: () -> Unit,
    callTodoApi: () -> Unit,
    uiState: TodoUiState,
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    inputValue: String,
    onInputChange: (String) -> Unit = {},
    onSaved: () -> Unit,
    isEditMode: Boolean = false,
    onToggleSheet: () -> Unit,

) {
    LaunchedEffect(Unit) {
        callTodoApi()
    }
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        MyAppBar(
            title = "Todos", navigateBack = onPop
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            onToggleSheet()
        }) {
            Icon(
                Icons.Default.Create, contentDescription = "Add Todo"
            )
        }
    }) { innerPaddings ->
        if (showBottomSheet) TodoBottomSheet(
            onDismiss = onDismiss,
            onInputChange = onInputChange,
            onSaved = onSaved,
            inputText = inputValue,
            isEditMode = false
        )
        Column(
            modifier = Modifier
                .padding(innerPaddings)
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState) {
                is TodoUiState.Error -> ErrorText(uiState.message)
                TodoUiState.Loading -> CircularProgressIndicator()
                is TodoUiState.Success -> SuccessUI(
                    remoteTodos = uiState.remoteTodos,
                    modifier = Modifier.weight(1f),
                    localTodos = uiState.localTodos
                )
            }
        }
    }
}

@Composable
fun SuccessUI(
    modifier: Modifier = Modifier, remoteTodos: List<Todo>, localTodos: List<Todo>
) {
    val scrollState = rememberScrollState()

    Column(modifier = modifier.verticalScroll(scrollState)) {
        if (remoteTodos.isEmpty() && localTodos.isEmpty()) {
            Text(
                text = stringResource(R.string.no_todos_found), fontSize = 16.sp
            )
            return
        }
        TodoList(
            todoList = localTodos,
            sourceName = stringResource(R.string.local_todos),
        )
        TodoList(
            todoList = remoteTodos,
            sourceName = stringResource(R.string.remote_todos),
        )
    }
}

@Composable
fun TodoList(modifier: Modifier = Modifier, todoList: List<Todo>, sourceName: String) {
    if (todoList.isNotEmpty()) Column(modifier = modifier) {
        Text(
            sourceName, fontSize = 16.sp, color = PrimaryColor, fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(4.dp))
        todoList.forEach { todo ->
            Column {
                ElevatedCard {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = todo.completed, onCheckedChange = {
                                Log.i("todoCheck", "SuccessUI: onChecked")
                            }, enabled = false, //todo: disable if remote list,
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = PrimaryColor,
                                checkedColor = PrimaryColor,
                                disabledCheckedColor = PrimaryColor,
                                disabledUncheckedColor = PrimaryColor
                            )
                        )
                        Text(
                            text = todo.title, fontSize = 16.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

}