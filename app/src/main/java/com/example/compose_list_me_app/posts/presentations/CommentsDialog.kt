package com.example.compose_list_me_app.posts.presentations

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.compose_list_me_app.R
import com.example.compose_list_me_app.common.CustomTextField
import com.example.compose_list_me_app.ui.theme.BackgroundColor
import com.example.compose_list_me_app.ui.theme.PrimaryColor

@Composable
fun CommentsDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit = {},
) {

    Dialog(
        onDismissRequest = onDismiss, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape((15.dp)),
            modifier = Modifier.fillMaxWidth(0.90f),
            colors = CardDefaults.cardColors(
                containerColor = BackgroundColor
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomTextField(
                    cornerRadius = 8,
                    onTextChange = {},
                    inputValue = "",
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Face,
                            contentDescription = stringResource(R.string.name),
                            tint = PrimaryColor
                        )
                    },
                    hintText = stringResource(R.string.name)
                )
                CustomTextField(
                    cornerRadius = 8,
                    onTextChange = {},
                    inputValue = "",
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Email,
                            contentDescription = stringResource(R.string.email),
                            tint = PrimaryColor
                        )
                    },
                    hintText = stringResource(R.string.email)
                )
                CustomTextField(
                    cornerRadius = 8,
                    onTextChange = {},
                    inputValue = "",
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Create,
                            contentDescription = stringResource(R.string.comment),
                            tint = PrimaryColor
                        )
                    },
                    hintText = stringResource(R.string.comment)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onDismiss, colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = PrimaryColor,
                        ), border = BorderStroke(1.dp, PrimaryColor)
                    ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            Log.i("DialogBox", "CommentsDialog: Confirm")
                        }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
                    ) {
                        Text("Confirm")
                    }
                }

            }
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun CommentsDialogPrev() {
    CommentsDialog(onDismiss = {})
}