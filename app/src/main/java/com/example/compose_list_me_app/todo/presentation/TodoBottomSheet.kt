package com.example.compose_list_me_app.todo.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose_list_me_app.R
import com.example.compose_list_me_app.common.composables.CustomTextField
import com.example.compose_list_me_app.ui.theme.BackgroundColor
import com.example.compose_list_me_app.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    inputText: String,
    onInputChange: (String) -> Unit,
    onSaved: () -> Unit,
    isEditMode: Boolean
) {

    ModalBottomSheet(
        containerColor = BackgroundColor, onDismissRequest = onDismiss,

        ) {
        Surface(
            color = BackgroundColor
        ) {
            Column(
                modifier = modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    textController = inputText,
                    onTextChange = onInputChange,
                    hintText = stringResource(R.string.todo),
                    cornerRadius = 12,
                    elevation = 2.dp
                )

                Spacer(modifier = Modifier.height(8.dp))
                ElevatedButton(
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                    shape = RoundedCornerShape(4.dp),
                    onClick = onSaved,
                ) {
                    Text(
                        text = if (isEditMode) stringResource(R.string.edit) else stringResource(R.string.add),
                        color = Color.White
                    )
                }
            }

        }

    }
}