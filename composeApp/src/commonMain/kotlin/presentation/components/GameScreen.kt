package presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import model.GameState

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    rows: Int = 4,
    columns: Int = 3,
    gameState: GameState,
    onCardFlipped: (Int) -> Unit,
    onRestart: () -> Unit,
    onReturnToMenu: () -> Unit
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        GameDetailsBar(modifier = Modifier.weight(0.1f), gameState = gameState)

        repeat(rows) { row ->
            Row(
                modifier = Modifier.weight(0.25f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(columns) { column ->

                    val index = row * columns + column
                    val card = gameState.cards[index]

                    key(card.cardNumber) {
                        GameCard(
                            card = card,
                            onFlipped = { onCardFlipped(index) },
                            modifier = Modifier.weight(0.2f)
                        )
                    }
                }
            }
        }
    }

    AnimatedVisibility(
        visible = gameState.gameWon,
        enter = fadeIn(),
        exit = fadeOut()
    ){
        GameWonScreen(
            modifier = modifier,
            gameState = gameState,
            onRestart = { onRestart() },
            onReturnToMenu = { onReturnToMenu() }
        )
    }

    AnimatedVisibility(
        visible = gameState.gameLost,
        enter = fadeIn(),
        exit = fadeOut()
    ){
        GameLostScreen(
            modifier = modifier,
            gameState = gameState,
            onRestart = { onRestart() },
            onReturnToMenu = { onReturnToMenu() }
        )
    }





}

@Composable
fun GameLostScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    gameState: GameState = GameState(),
    onRestart: () -> Unit,
    onReturnToMenu: () -> Unit
){
    Column(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.75f))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Game Over")
        Text(text = "Three strikes and you're out...")
        Text(text = "Points: ${gameState.points}")
        Button(
            onClick = onRestart
        ) {
            Text(text = "Restart")
        }
        Button(
            onClick = onReturnToMenu
        ) {
            Text(text = "Return To Menu")
        }
    }
}

@Composable
fun GameWonScreen(
    modifier: Modifier = Modifier.wrapContentSize(),
    gameState: GameState = GameState(),
    onRestart: () -> Unit,
    onReturnToMenu: () -> Unit

){
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Game Over")
        Text(text = "You Won!")
        Text(text = "Points: ${gameState.points}")
        Button(onClick = onRestart) {
            Text(text = "Restart")
        }
        Button(onClick = onReturnToMenu) {
            Text(text = "Return To Menu")
        }
    }
}