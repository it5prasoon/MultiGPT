package com.matrix.multigpt.presentation.ui.tour

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matrix.multigpt.R
import com.matrix.multigpt.data.model.ApiType
import com.matrix.multigpt.presentation.common.PrimaryLongButton
import com.matrix.multigpt.presentation.ui.chat.OpponentChatBubble
import com.matrix.multigpt.presentation.ui.chat.UserChatBubble

@Composable
fun FeatureTourScreen(
    onSetupNow: () -> Unit,
    onSkipSetup: () -> Unit
) {
    var currentStep by remember { mutableIntStateOf(0) }
    val totalSteps = 4
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DemoModeBanner(
                onSetupClick = onSetupNow
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when (currentStep) {
                0 -> MultiAiChatDemo()
                1 -> HomeScreenDemo()
                2 -> PrivacyFeaturesDemo()
                3 -> ReadyToStartDemo(
                    onSetupNow = onSetupNow,
                    onSkipSetup = onSkipSetup
                )
            }
            
            if (currentStep < totalSteps - 1) {
                Spacer(modifier = Modifier.weight(1f))
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onSkipSetup) {
                        Text("Skip tour")
                    }
                    
                    Text(
                        text = "${currentStep + 1} of $totalSteps",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    TextButton(
                        onClick = { currentStep++ }
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoModeBanner(
    onSetupClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(TopAppBarDefaults.windowInsets),
        color = MaterialTheme.colorScheme.primaryContainer,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🔑 Demo Mode - Add API keys to start chatting",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            TextButton(onClick = onSetupClick) {
                Text("Setup")
            }
        }
    }
}

@Preview
@Composable
fun MultiAiChatDemo(modifier: Modifier = Modifier) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val maximumChatBubbleWidth = screenWidth - 48.dp - 32.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            modifier = Modifier.semantics { heading() },
            text = "Chat with Multiple AI Models",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Ask one question and get responses from all AI models simultaneously.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // User message using real chat bubble
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            UserChatBubble(
                modifier = Modifier.widthIn(max = maximumChatBubbleWidth),
                text = "Explain quantum computing in simple terms",
                isLoading = false,
                onCopyClick = {},
                onEditClick = {}
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // AI responses using real horizontal scrolling layout
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            
            OpponentChatBubble(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .widthIn(max = maximumChatBubbleWidth),
                canRetry = false,
                isLoading = false,
                apiType = ApiType.OPENAI,
                text = "Quantum computing uses quantum bits (qubits) that can exist in **multiple states simultaneously**, allowing for exponentially faster processing of certain problems...",
                onCopyClick = {},
                onRetryClick = {}
            )
            
            OpponentChatBubble(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .widthIn(max = maximumChatBubbleWidth),
                canRetry = false,
                isLoading = false,
                apiType = ApiType.ANTHROPIC,
                text = "Think of quantum computing like a **magical library**. While traditional computers read books one page at a time, quantum computers can read all pages simultaneously...",
                onCopyClick = {},
                onRetryClick = {}
            )
            
            OpponentChatBubble(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .widthIn(max = maximumChatBubbleWidth),
                canRetry = false,
                isLoading = false,
                apiType = ApiType.GOOGLE,
                text = "Quantum computing harnesses quantum mechanics principles like **superposition and entanglement** to perform calculations impossible for classical computers...",
                onCopyClick = {},
                onRetryClick = {}
            )
            
            Spacer(modifier = Modifier.width(32.dp))
        }
    }
}

@Preview
@Composable
fun HomeScreenDemo(modifier: Modifier = Modifier) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val maximumChatBubbleWidth = screenWidth - 48.dp - 32.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            modifier = Modifier.semantics { heading() },
            text = "Home Screen - Your Chats",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Manage your conversations and create new multi-AI chats.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Simulate the real home screen with TopAppBar
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column {
                // Simulate TopAppBar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.chats),
                        style = MaterialTheme.typography.titleLarge
                    )
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Settings, contentDescription = stringResource(R.string.settings))
                    }
                }
                
                // Demo chat list items using real ListItem
                ListItem(
                    headlineContent = { Text("Quantum Computing Discussion") },
                    supportingContent = { Text("Using OpenAI, Claude, Gemini") },
                    leadingContent = {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.ic_rounded_chat),
                            contentDescription = stringResource(R.string.chat_icon)
                        )
                    }
                )
                
                ListItem(
                    headlineContent = { Text("Python Programming Help") },
                    supportingContent = { Text("Using OpenAI, Groq") },
                    leadingContent = {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.ic_rounded_chat),
                            contentDescription = stringResource(R.string.chat_icon)
                        )
                    }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // New Chat FAB positioned above navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ExtendedFloatingActionButton(
                onClick = {},
                icon = { Icon(Icons.Filled.Add, stringResource(R.string.new_chat)) },
                text = { Text(text = stringResource(R.string.new_chat)) }
            )
        }
    }
}

@Preview
@Composable
fun PrivacyFeaturesDemo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            modifier = Modifier.semantics { heading() },
            text = "100% Private & Secure",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Your conversations stay on your device. No data collection, no cloud storage, no tracking.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        PrivacyFeatureCard(
            title = "Local Storage Only",
            description = "All conversations saved locally on your device"
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        PrivacyFeatureCard(
            title = "Direct API Communication",
            description = "Your data goes directly to AI providers, not through our servers"
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        PrivacyFeatureCard(
            title = "No Data Collection",
            description = "We don't track, analyze, or store your conversations"
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        PrivacyFeatureCard(
            title = "Your API Keys",
            description = "Use your own API keys, encrypted and stored securely"
        )
    }
}

@Preview
@Composable
fun ReadyToStartDemo(
    onSetupNow: () -> Unit = {},
    onSkipSetup: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.semantics { heading() },
            text = "Ready to Experience MultiGPT?",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Set up your API keys to start chatting with multiple AI models and comparing their responses.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "✨ What you'll get:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                BenefitItem("Chat with ChatGPT, Claude, Gemini & more")
                BenefitItem("Compare AI responses side-by-side")
                BenefitItem("Advanced AI controls & customization")
                BenefitItem("100% private conversations")
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        PrimaryLongButton(
            onClick = onSetupNow,
            text = "Set up API keys"
        )

        TextButton(
            onClick = onSkipSetup,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Maybe later")
        }
    }
}

@Composable
fun DemoMessageCard(
    title: String,
    message: String,
    isUser: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isUser) 
                MaterialTheme.colorScheme.primary 
            else 
                MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = if (isUser) 
                    MaterialTheme.colorScheme.onPrimary
                else 
                    MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isUser) 
                    MaterialTheme.colorScheme.onPrimary
                else 
                    MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun DemoResponseCard(
    provider: String,
    message: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = provider,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ComparisonCard(
    leftProvider: String,
    leftResponse: String,
    rightProvider: String,
    rightResponse: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = leftProvider,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(6.dp))
                
                Text(
                    text = leftResponse,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = rightProvider,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(6.dp))
                
                Text(
                    text = rightResponse,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun PrivacyFeatureCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🛡️",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(end = 12.dp)
            )
            
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun BenefitItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "✓",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(end = 8.dp)
        )
        
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
