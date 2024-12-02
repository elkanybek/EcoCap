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
fun SettingsScreen(
    darkIsEnabled: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    // Track changes to fields
//    var initialTheme by remember { mutableStateOf(darkIsEnabled) }
//
//    val isChanged = darkIsEnabled != initialTheme

    var tempDarkIsEnabled by remember { mutableStateOf(darkIsEnabled) }

    val isChanged = tempDarkIsEnabled != darkIsEnabled

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .background(Color(0xFF4DB6AC)) // Teal background
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(60.dp))


        // Title
        Text(
            text = "Settings",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(modifier = Modifier.height(32.dp))


        // Dark/Light Theme Toggle
        Box(
            modifier = Modifier
                .padding(8.dp)
                .shadow(8.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp)) // Apply rounded corners
                .background(MaterialTheme.colorScheme.surfaceContainerHighest) // Background after clip
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dark / Light Theme",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Switch(
                    checked = tempDarkIsEnabled,
                    onCheckedChange = { tempDarkIsEnabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Black,
                        uncheckedThumbColor = Color.Gray
                    )
                )
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        // Apply Button
        Button(
            onClick = {
                // Update initial values
//                initialTheme = darkIsEnabled
                onThemeToggle(tempDarkIsEnabled)
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

    }
}

