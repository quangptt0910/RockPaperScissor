package com.example.rockpaperscissor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rockpaperscissor.ui.theme.RockPaperScissorTheme
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RockPaperScissorTheme {
                    RockPaperScissorGame()
            }
        }
    }
}


@Composable
fun RockPaperScissorGame() {
    var gameState by remember { mutableStateOf(GameState()) }

    val choices = listOf("Rock", "Paper", "Scissors", "Random")

    // determine winner
    fun determineWinner(user: String, computer: String): String {
        return when {
            user == computer -> "It's a tie!"
            user == "Rock" && computer == "Scissors" -> "You win!"
            user == "Paper" && computer == "Rock" -> "You win!"
            user == "Scissors" && computer == "Paper" -> "You win!"
            else -> "Computer wins!"
        }
    }

    // Columnn of game
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // User choice
        GameTitleCard()

            gameState.userChoice?.let { user ->
                gameState.computerChoice?.let { computer ->
                    CardShow("You chose $user")
                    CardShow("Computer chose $computer")

                    gameState.result?.let {
                        CardShow(it)

                    }

                }

            }
        Row(modifier = Modifier.padding(16.dp)) {

            ChoiceButtons(gameState = gameState, choices = choices,
                onChoiceSelected = { choice ->
                    val computerPick = choices[Random.nextInt(choices.size - 1)]
                    val userPick = if (choice == "Random") {
                        choices[Random.nextInt(choices.size - 1)] // Random choice for user
                    } else {
                        choice
                    }
                    gameState = gameState.copy(
                        userChoice = userPick,
                        computerChoice = computerPick,
                        result = determineWinner(userPick, computerPick)
                    )
                })
        }

    }
}

@Composable
fun GameTitleCard(){
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        border = BorderStroke(2.dp, Color.Black),
        modifier = Modifier.padding(8.dp), // Add some padding around the card
        //modifier = Modifier .size(width = 240.dp, height = 100.dp)
    ) {
        Text(
            text = "Rock Paper Scissors Game",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun ChoiceButtons(gameState: GameState,choices: List<String>, onChoiceSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp),
       // horizontalArrangement = Arrangement.SpaceAround // Distribute buttons evenly
    ) {
        choices.forEach { choice ->
            Button(
                onClick = { onChoiceSelected(choice) }, // Call onChoiceSelected when clicked
//                modifier = Modifier
//                    .padding(8.dp)
            ) {
                Text(choice)
            }
        }
    }
}

@Composable
private fun CardShow(name: String, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClick = {}
            )
            .padding(8.dp), // Add some padding around the card
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {
        Box(
            modifier = Modifier, // Remove fillMaxWidth
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp), // Reduced padding
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

data class GameState(
    val userChoice: String? = null,
    val computerChoice: String? = null,
    val result: String? = null
)

// PREVIEW
@Preview(showBackground = true)
@Composable
fun GameTitleCardPreview() {
    RockPaperScissorTheme {
        GameTitleCard()
    }
}

@Preview(showBackground = true)
@Composable
fun RockPaperScissorGamePreview() {
    RockPaperScissorTheme {
        RockPaperScissorGame()
    }
}

@Preview(showBackground = true)
@Composable
fun CardShowPreview() {
    RockPaperScissorTheme {
        CardShow("Example of card show text")
    }
}