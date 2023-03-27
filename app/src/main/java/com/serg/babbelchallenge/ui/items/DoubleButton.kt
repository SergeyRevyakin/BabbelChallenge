package com.serg.babbelchallenge.ui.items

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serg.babbelchallenge.R

@Composable
fun DoubleButton(
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(64.dp)
    ) {
        Button(
            onClick = onLeftButtonClick,
            modifier = Modifier
                .weight(0.5f)
                .padding(8.dp)
                .height(48.dp),
        ) {
            Text(text = stringResource(id = R.string.wrong_word))
        }
        Button(
            onClick = onRightButtonClick,
            modifier = Modifier
                .weight(0.5f)
                .padding(8.dp)
                .height(48.dp),
        ) {
            Text(text = stringResource(id = R.string.correct_word))
        }
    }
}

@Composable
@Preview
fun PreviewDoubleButton(){
    DoubleButton(onLeftButtonClick = {  }, onRightButtonClick = { })
}