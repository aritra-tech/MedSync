package com.aritra.medsync.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aritra.medsync.ui.theme.DMSansFontFamily
import com.aritra.medsync.ui.theme.OnPrimaryContainer
import com.aritra.medsync.ui.theme.OnSurface20
import com.aritra.medsync.ui.theme.OnSurface60
import com.aritra.medsync.ui.theme.normal16

@Composable
fun MedSyncTextField(
    modifier: Modifier = Modifier,
    headerText: String = "",
    hintText: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textColor: Color = OnSurface20,
    textStyle: TextStyle = normal16,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        if (headerText.isNotEmpty()) {
            TextHeader(text = headerText)
            Spacer(modifier = Modifier.height(16.dp))
        }

        TextEditField(
            hintText = hintText,
            value = value,
            keyboardType = keyboardType,
            keyboardActions = keyboardActions,
            textColor = textColor,
            textStyle = textStyle,
            enabled = enabled,
            readOnly = readOnly,
            interactionSource = interactionSource,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon
        ) { onValueChange(it) }
    }
}

@Composable
fun TextHeader(text: String) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontFamily = DMSansFontFamily,
            fontWeight = FontWeight(500),
            color = OnPrimaryContainer,
        )
    )
}

@Composable
fun TextEditField(
    hintText: String = "",
    value: String = "",
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textColor: Color = OnSurface20,
    keyboardType: KeyboardType = KeyboardType.Text,
    textStyle: TextStyle = normal16,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        onValueChange = {
            onValueChange(it)
        },
        keyboardActions = keyboardActions,
        colors = TextFieldDefaults.colors(
            focusedTextColor = textColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(text = hintText, color = OnSurface60, fontFamily = DMSansFontFamily)
        },
        textStyle = textStyle,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        readOnly = readOnly,
        interactionSource = interactionSource
    )
}