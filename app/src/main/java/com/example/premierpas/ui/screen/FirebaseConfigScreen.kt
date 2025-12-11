package com.example.premierpas.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Cloud
import androidx.compose.material.icons.rounded.CloudDone
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.premierpas.ui.viewmodel.FirebaseConfigViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseConfigScreen(
    navigateBack: () -> Unit
) {
    val viewModel: FirebaseConfigViewModel = viewModel()
    val config by viewModel.configState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Dynamic colors based on remote config
    val backgroundColor by animateColorAsState(
        targetValue = when {
            config.isDarkModeEnabled -> Color(0xFF1A1A2E)
            config.themeColor == "red" -> Color(0xFFFFEBEE)
            config.themeColor == "green" -> Color(0xFFE8F5E9)
            config.themeColor == "purple" -> Color(0xFFF3E5F5)
            else -> Color(0xFFE3F2FD) // blue
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "backgroundColor"
    )

    val cardColor by animateColorAsState(
        targetValue = when {
            config.isDarkModeEnabled -> Color(0xFF16213E)
            config.themeColor == "red" -> Color(0xFFFFCDD2)
            config.themeColor == "green" -> Color(0xFFC8E6C9)
            config.themeColor == "purple" -> Color(0xFFE1BEE7)
            else -> Color(0xFFBBDEFB) // blue
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardColor"
    )

    val textColor = if (config.isDarkModeEnabled) Color.White else Color.Black

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Firebase Remote Config",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = textColor
                        )
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = textColor
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
            ) {
                // Firebase Icon
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            color = cardColor,
                            shape = MaterialTheme.shapes.extraLarge
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (config.isDarkModeEnabled) Icons.Rounded.CloudDone else Icons.Rounded.Cloud,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        tint = textColor
                    )
                }

                // Title
                Text(
                    text = "Configuration Firebase",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = textColor
                )

                // Config Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = cardColor
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Paramètres actuels",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )

                        ConfigRow(
                            label = "Mode sombre",
                            value = if (config.isDarkModeEnabled) "Activé" else "Désactivé",
                            textColor = textColor
                        )


                        ConfigRow(
                            label = "Couleur du thème",
                            value = config.themeColor.uppercase(),
                            textColor = textColor
                        )
                    }
                }

                // Refresh Button
                Button(
                    onClick = { viewModel.fetchAndRefreshConfig() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = !isLoading,
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when {
                            config.themeColor == "red" -> Color(0xFFEF5350)
                            config.themeColor == "green" -> Color(0xFF66BB6A)
                            config.themeColor == "purple" -> Color(0xFFAB47BC)
                            else -> Color(0xFF42A5F5)
                        }
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.padding(4.dp))
                        Text(
                            text = "Actualiser la config",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                Text(
                    text = "Les changements d'interface sont visibles en temps réel",
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ConfigRow(label: String, value: String, textColor: Color) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor.copy(alpha = 0.7f)
        )
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = textColor.copy(alpha = 0.1f)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = textColor,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}
