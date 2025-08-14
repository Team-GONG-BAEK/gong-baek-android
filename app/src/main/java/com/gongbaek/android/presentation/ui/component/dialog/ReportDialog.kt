package com.gongbaek.android.presentation.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gongbaek.android.R
import com.gongbaek.android.ui.theme.GONGBAEKTheme
import com.gongbaek.android.ui.theme.GongBaekTheme

@Composable
fun ReportDialog(
    titleMassage: String,
    descriptionMassage: String,
    confirmOption: String,
    onConfirm: () -> Unit,
    dismissOption: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(GongBaekTheme.colors.black.copy(alpha = 0.3f))
                .padding(horizontal = 40.dp),
            contentAlignment = Alignment.Center,
            content = {
                Card(
                    modifier = Modifier.wrapContentHeight(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = GongBaekTheme.colors.white)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .padding(top = 28.dp, bottom = 18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Title
                        Text(
                            text = titleMassage,
                            color = GongBaekTheme.colors.gray10,
                            style = GongBaekTheme.typography.body1.sb16
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        // Description
                        Text(
                            text = descriptionMassage,
                            color = GongBaekTheme.colors.gray07,
                            textAlign = TextAlign.Center,
                            style = GongBaekTheme.typography.body2.r14
                        )
                        Spacer(modifier = Modifier.height(18.dp))

                        // Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            content = {
                                Button(
                                    onClick = onDismiss,
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            color = GongBaekTheme.colors.gray08,
                                            shape = RoundedCornerShape(6.dp)
                                        ),
                                    colors = ButtonDefaults.buttonColors(GongBaekTheme.colors.gray08)
                                ) {
                                    Text(
                                        text = dismissOption,
                                        color = GongBaekTheme.colors.white,
                                        style = GongBaekTheme.typography.body1.sb16
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Button(
                                    onClick = onConfirm,
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            color = GongBaekTheme.colors.mainOrange,
                                            shape = RoundedCornerShape(6.dp)
                                        ),
                                    colors = ButtonDefaults.buttonColors(GongBaekTheme.colors.mainOrange)
                                ) {
                                    Text(
                                        text = confirmOption,
                                        color = GongBaekTheme.colors.white,
                                        style = GongBaekTheme.typography.body1.sb16
                                    )
                                }
                            }
                        )
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGroupReportDialog() {
    GONGBAEKTheme {
        ReportDialog(
            titleMassage = stringResource(id = R.string.dialog_title_group_report),
            descriptionMassage = stringResource(id = R.string.dialog_description_group_report),
            confirmOption = "신고하기",
            onConfirm = {},
            dismissOption = "취소",
            onDismiss = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCommentReportDialog() {
    GONGBAEKTheme {
        ReportDialog(
            titleMassage = stringResource(id = R.string.dialog_title_comment_report),
            descriptionMassage = stringResource(id = R.string.dialog_description_comment_report),
            confirmOption = "신고하기",
            onConfirm = {},
            dismissOption = "취소",
            onDismiss = {}
        )
    }
}
