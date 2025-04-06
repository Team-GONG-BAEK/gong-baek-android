package com.sopt.gongbaek.presentation.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sopt.gongbaek.R
import com.sopt.gongbaek.presentation.type.GongBaekDialogType
import com.sopt.gongbaek.presentation.ui.component.button.GongBaekBasicButton
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun AccountDialog(
    gongBaekDialogType: GongBaekDialogType,
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 15.dp)
            .background(color = GongBaekTheme.colors.white, shape = RoundedCornerShape(20.dp))
            .padding(start = 20.dp, top = 28.dp, end = 20.dp, bottom = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(gongBaekDialogType.titleResId),
            color = GongBaekTheme.colors.gray10,
            style = GongBaekTheme.typography.title2.sb18
        )
        Spacer(modifier = Modifier.height(28.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GongBaekBasicButton(
                title = stringResource(R.string.dialog_button_cancel),
                onClick = onDismissButtonClick,
                verticalPadding = 10,
                modifier = Modifier.weight(1f),
                enabled = true,
                enableButtonColor = GongBaekTheme.colors.gray08
            )
            GongBaekBasicButton(
                title = stringResource(R.string.dialog_button_confirm),
                onClick = onConfirmButtonClick,
                verticalPadding = 10,
                enabled = true,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLogoutDialog() {
    Box(
        modifier = Modifier
            .background(color = GongBaekTheme.colors.white)
            .fillMaxSize()
    ) {
        Dialog(
            onDismissRequest = { }
        ) {
            AccountDialog(
                gongBaekDialogType = GongBaekDialogType.LOGOUT,
                onConfirmButtonClick = {},
                onDismissButtonClick = {}
            )
        }
    }
}
