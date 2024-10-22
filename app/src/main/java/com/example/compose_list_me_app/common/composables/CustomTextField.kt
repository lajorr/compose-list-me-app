package com.example.compose_list_me_app.common.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose_list_me_app.comments.presentation.TextEditingController
import com.example.compose_list_me_app.ui.theme.ContainerColor2
import com.example.compose_list_me_app.ui.theme.PrimaryColor

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    textController: TextEditingController,
    onTextChange: (String) -> Unit,
    trailingIcon: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    hintText: String = "",
    elevation: Dp = 8.dp,
    maxLines: Int = 1,
    cornerRadius: Int = 50,
    errorMessage: String? = null
) {
    Column(modifier = modifier) {
        if (errorMessage != null)
            ErrorText(errorMessage)
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = elevation,
                    RoundedCornerShape(cornerRadius),
                ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = ContainerColor2,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = ContainerColor2,
                focusedBorderColor = PrimaryColor,
                focusedPlaceholderColor = PrimaryColor,
                focusedLeadingIconColor = PrimaryColor,
                errorContainerColor = ContainerColor2,
                errorTextColor = Color.Red,
                errorLeadingIconColor = Color.Red

            ),
            shape = RoundedCornerShape(cornerRadius),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = errorMessage != null,

            placeholder = { Text(text = hintText) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            maxLines = maxLines,
            value = textController,
            onValueChange = onTextChange
        )
    }
}
