package com.example.deliveryprojectstructuredemo.ui.features.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun OtpBugView() {
    val (editValue, setEditValue) = remember { mutableStateOf("") }
    val otpLength = remember { 4 }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    AppOutlineTextField(
        value = editValue,
        onValueChange = {
            if (it.length <= otpLength) {
                setEditValue(it)
            }
        },
        modifier = Modifier
            .size(0.dp)
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        )
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        (0 until otpLength).map { index ->
            OtpCell(
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        focusRequester.requestFocus()
                        keyboard?.show()
                    }
                    .border(
                        width = 1.dp,
                        color = androidx.compose.ui.graphics.Color.LightGray,
                        RoundedCornerShape(4.dp)
                    )
                    .graphicsLayer {
                        shadowElevation = 0.dp.toPx()
                        shape = CutCornerShape(5.dp)
                        clip = true
                    }
                    .background(color = MaterialTheme.colors.background)
//                    .padding(32.dp)
                /* .clip(
                     RoundedCornerShape(5.dp)).background(androidx.compose.ui.graphics.Color.Red)*/
                /*.border(1.5.dp, androidx.compose.ui.graphics.Color.LightGray)*/,
                value = editValue.getOrNull(index)?.toString() ?: "",
                isCursorVisible = editValue.length == index
            )
            Spacer(modifier = Modifier.size(15.dp))
        }
    }

}

@Composable
fun OtpCell(
    modifier: Modifier,
    value: String,
    isCursorVisible: Boolean = false
) {
    val scope = rememberCoroutineScope()
    val (cursorSymbol, setCursorSymbol) = remember { mutableStateOf("") }

    LaunchedEffect(key1 = cursorSymbol, isCursorVisible) {
        if (isCursorVisible) {
            scope.launch {
                delay(350)
                setCursorSymbol(if (cursorSymbol.isEmpty()) "|" else "")
            }
        }
    }

    Box(
        modifier = modifier
    ) {

        AppText(
            text = if (isCursorVisible) cursorSymbol else value,
            style = MaterialTheme.typography.h1,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
