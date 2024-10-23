package com.example.compose_list_me_app.todo.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose_list_me_app.common.composables.ElevatedCard
import com.example.compose_list_me_app.common.composables.MyAppBar
import com.example.compose_list_me_app.ui.theme.PrimaryColor
import com.example.compose_list_me_app.ui.theme.Purple80
import kotlinx.serialization.Serializable

@Serializable
data class TodoListScreenObject(val userId: Int)

@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
    onPop: () -> Unit,
    callTodoApi: () -> Unit
) {

    LaunchedEffect(Unit) {
        callTodoApi()
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        MyAppBar(
            title = "Todos", navigateBack = onPop
        )
    }) { innerPaddings ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(2) {
                // todo: nope!!
                val isChecked = remember {
                    mutableStateOf(false)
                };
                ElevatedCard {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = { isChecked.value = it },
//                            enabled = false, todo: disable if remote list
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = PrimaryColor,
                                checkedColor = PrimaryColor,
                                disabledCheckedColor = Purple80,
                                disabledUncheckedColor = Purple80
                            )
                        )
                        Text(
                            text = "delectus aut autem", fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }

}