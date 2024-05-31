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
import androidx.compose.runtime.LaunchedEffect
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
            
            if (gameState.gameStarted){
                CardGrid(
                    gameState = gameState,
                    onCardFlipped = viewModel::flipCard
                )
            }
            else {

                StartScreen(startEvent = viewModel::startGame)

            }

        }

    }
}

@Composable
fun StartScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    startEvent: (amountOfCards: Int) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = { startEvent(10) },
        ){
            Text(text = "Start Game")
        }

    }
}

@Composable
fun CardGrid(
    modifier: Modifier = Modifier,
    gameState: GameState = GameState(),
//    cards: List<GameCard> = listOf(),
    onCardFlipped: (index: Int) -> Unit = {}
){
    LazyVerticalGrid(
        modifier = modifier,
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


