package com.matrix.multigpt.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.matrix.multigpt.R
import com.matrix.multigpt.data.dto.bedrock.BedrockAuthMethod
import com.matrix.multigpt.data.dto.bedrock.BedrockCredentials
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun BedrockCredentialsField(
    value: String,
    onValueChange: (String) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Parse existing JSON or use defaults
    val credentials = remember(value) {
        try {
            if (value.isNotBlank()) {
                Json.decodeFromString<BedrockCredentials>(value)
            } else {
                BedrockCredentials()
            }
        } catch (e: Exception) {
            BedrockCredentials()
        }
    }

    var apiKey by remember(value) { mutableStateOf(credentials.apiKey) }

    // Update parent when any field changes
    fun updateCredentials() {
        val newCredentials = BedrockCredentials(
            authMethod = BedrockAuthMethod.API_KEY,
            apiKey = apiKey
        )
        val json = Json.encodeToString(newCredentials)
        onValueChange(json)
    }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.bedrock_credentials_description),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = stringResource(R.string.bedrock_api_key_section_title),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = apiKey,
            onValueChange = { 
                apiKey = it
                updateCredentials()
            },
            label = { Text(stringResource(R.string.bedrock_api_key)) },
            placeholder = { Text("Enter your API key") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            singleLine = true,
            supportingText = {
                Text(
                    text = stringResource(R.string.bedrock_api_key_help),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        )

        // Clear button
        if (apiKey.isNotBlank()) {
            TextButton(
                modifier = Modifier.padding(top = 8.dp),
                onClick = {
                    apiKey = ""
                    onClearClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(stringResource(R.string.clear_token))
            }
        }
    }
}
