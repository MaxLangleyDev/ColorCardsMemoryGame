import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {

        val viewModel = viewModel { GameViewModel() }

        val gameState by viewModel.gameState.collectAsState()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {

            StartScreen(
                gameState = gameState,
                setupGame = viewModel::setupGame,
                startGame = viewModel::startGame
            )

            GameBoard(
                modifier = Modifier.fillMaxSize(),
                gameState = gameState,
                onCardFlipped = viewModel::flipCard
            )

        }

    }
}

@Composable
fun StartScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    gameState: GameState,
    setupGame: (amountOfCards: Int) -> Unit = {},
    startGame: () -> Unit = {}
) {
    AnimatedVisibility(
        visible = gameState.showStartScreen,
        enter = slideInVertically(),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
        )
    ){
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = {
                    setupGame(12)
                    startGame()
                },
            ){
                Text(text = "Start Game")
            }

        }
    }
}


