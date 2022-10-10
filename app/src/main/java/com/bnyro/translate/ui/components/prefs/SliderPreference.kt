package com.bnyro.translate.ui.components.prefs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bnyro.translate.R
import com.bnyro.translate.util.Preferences

@Composable
fun SliderPreference(
    preferenceKey: String,
    preferenceTitle: String,
    defaultValue: Float,
    minValue: Float,
    maxValue: Float,
    steps: Int
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .padding(
                top = 20.dp
            )
    ) {
        Card(
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .clickable {
                    showDialog = true
                }

        ) {
            Text(
                preferenceTitle,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }

    if (showDialog) {
        SliderPreferenceDialog(
            title = preferenceTitle,
            preferenceKey = preferenceKey,
            onDismissRequest = {
                showDialog = false
            },
            defaultValue = defaultValue,
            minValue = minValue,
            maxValue = maxValue,
            steps = steps
        )
    }
}

@Composable
fun SliderPreferenceDialog(
    title: String,
    preferenceKey: String,
    onDismissRequest: () -> Unit,
    defaultValue: Float,
    minValue: Float,
    maxValue: Float,
    steps: Int

) {
    var value by remember {
        mutableStateOf(
            Preferences.get(
                preferenceKey,
                defaultValue
            )
        )
    }

    AlertDialog(
        title = {
            Text(title)
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Slider(
                    value = value,
                    onValueChange = {
                        value = it
                    },
                    valueRange = minValue..maxValue,
                    steps = steps
                )

                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = minValue.toString()
                    )
                    Text(
                        text = value.toString(),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = maxValue.toString()
                    )
                }
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    Preferences.put(
                        preferenceKey,
                        value
                    )
                    onDismissRequest.invoke()
                }
            ) {
                Text(
                    stringResource(R.string.okay)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(
                    stringResource(R.string.cancel)
                )
            }
        }
    )
}