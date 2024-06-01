import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
            
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                StartScreen(
                    gameState = gameState,
                    setupGame = viewModel::setupGame,
                    startGame = viewModel::startGame
                )

                CardGrid(
                    gameState = gameState,
                    onCardFlipped = viewModel::flipCard
                )

            }

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
        visible = !gameState.gameStarted,
        enter = fadeIn(),
        exit = fadeOut()
    ){
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = {
                    setupGame(10)
                    startGame()
                },
            ){
                Text(text = "Start Game")
            }

        }
    }
}

@Composable
fun CardGrid(
    modifier: Modifier = Modifier,
    gameState: GameState = GameState(),
    onCardFlipped: (index: Int) -> Unit = {}
){
    AnimatedVisibility(
        visible = gameState.gameStarted,
        enter = fadeIn(),
        exit = fadeOut()
    ){
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
            ) {
                itemsIndexed(gameState.cards) { index, card ->
                    CardFlippable(
                        card = card,
                        onFlipped = {
                            if (gameState.cardsSelectable){
                                onCardFlipped(index)
                            }
                        }

                    )
                }
            }
        }
    }
}


