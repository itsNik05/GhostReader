package com.example.ghostreader.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen(onScanClick: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F172A),
                        Color(0xFF0B1120)
                    )
                )
            )
            .padding(16.dp)
    ) {

        Column {

            // Greeting Section
            Text(
                text = "Good Evening,",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Text(
                text = "Nitish 👋",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatCard("Scanned", "128")
                StatCard("PDFs", "47")
                StatCard("Saved MB", "2.4")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Quick Scan Button (Gradient Style)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF4F46E5),
                                Color(0xFF7C3AED)
                            )
                        )
                    )
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterStart
            ) {

                Column {
                    Text(
                        text = "Tap to start",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )

                    Text(
                        text = "Quick Scan",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String) {

    Card(
        modifier = Modifier
            .width(100.dp)
            .height(90.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E293B)
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = title,
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
