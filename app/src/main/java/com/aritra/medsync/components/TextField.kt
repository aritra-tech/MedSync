package com.aritra.medsync.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import com.aritra.medsync.ui.theme.SecondarySurface
import com.aritra.medsync.ui.theme.normal16


@Composable
fun TextField(
    modifier: Modifier = Modifier,
    headerText: String = "",
    hintText: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textColor: Color = OnSurface20,
    backgroundColor: Color = SecondarySurface,
    textStyle: TextStyle = normal16,
    enabled: Boolean = true,
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
            // Try renaming it to TextFieldHeader or Header
            TextHeader(text = headerText)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Try Renaming this too as CustomTextField
        TextEditField(
            hintText = hintText,
            value = value,
            keyboardActions = keyboardActions,
            textColor = textColor,
            backgroundColor = backgroundColor,
            textStyle = textStyle,
            enabled = enabled,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon
        ) { onValueChange(it) } // Use String instead of MutableState<String>
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
    backgroundColor: Color = SecondarySurface,
    textStyle: TextStyle = normal16,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        keyboardActions = keyboardActions,
        textStyle = textStyle,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled
    )
}