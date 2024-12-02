package com.example.ecocap.ui.Screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecocap.R

@Composable
fun ProfileScreen() {
    var username by remember { mutableStateOf("DavyDav") }
    var password by remember { mutableStateOf("********") }
    var confirmPassword by remember { mutableStateOf("********") }

    // Track changes to fields
    var initialUsername by remember { mutableStateOf(username) }
    var initialPassword by remember { mutableStateOf(password) }
    var initialConfirmPassword by remember { mutableStateOf(confirmPassword) }

    val isChanged = username != initialUsername || password != initialPassword || confirmPassword != initialConfirmPassword

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Title
        Text(
            text = "Profile",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Username Field
        EditableSettingsField(
            label = "Username",
            value = username,
            onValueChange = { username = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        EditableSettingsField(
            label = "Password",
            value = password,
            onValueChange = { password = it },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password Field
        EditableSettingsField(
            label = "Confirm Password",
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Apply Button
        Button(
            onClick = {
                // Update initial values
                initialUsername = username
                initialPassword = password
                initialConfirmPassword = confirmPassword
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            enabled = isChanged,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isChanged) Color(0xFF4CAF50) else Color.Gray,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(
                text = "Apply",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Logout Button
        Button(
            onClick = { /* TODO: Handle Logout */ },

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
            )
        ) {
            Text(
                text = "Logout",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableSettingsField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    Box(
        modifier = Modifier
        .padding(8.dp)
        .shadow(8.dp, RoundedCornerShape(8.dp))
        .clip(RoundedCornerShape(8.dp)) // Apply rounded corners
        .background(MaterialTheme.colorScheme.surfaceContainerHighest) // Background after clip
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerHighest, RoundedCornerShape(8.dp))
                .padding(16.dp)

        ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
//                textColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Gray
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
        )
    }
    }

}
