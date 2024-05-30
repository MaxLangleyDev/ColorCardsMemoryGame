import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {

        val viewModel = viewModel { GameViewModel(
            initialGameState = GameState(
                cards = listOf(
                    GameCard(
                        cardNumber = 1,
                        isFlipped = false
                    ),
                    GameCard(
                        cardNumber = 2,
                        isFlipped = false
                    ),
                    GameCard(
                        cardNumber = 3,
                        isFlipped = false
                    ),
                    GameCard(
                        cardNumber = 4,
                        isFlipped = false
                    ),
                    GameCard(
                        cardNumber = 5,
                        isFlipped = false
                    ),
                    GameCard(
                        cardNumber = 6,
                        isFlipped = false
                    ),
                    GameCard(
                        cardNumber = 7,
                        isFlipped = false
                    ),
                    GameCard(
                        cardNumber = 8,
                        isFlipped = false
                    ),
                    GameCard(
                        cardNumber = 9,
                        isFlipped = false
                    ),
                    GameCard(
                        cardNumber = 10,
                        isFlipped = false
                    ),
                )
            )
        ) }
        val gameState = viewModel.gameState.collectAsState()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            
            CardGrid(
                cards = gameState.value.cards,
                onCardFlipped = viewModel::flipCard
            )

        }

    }
}

@Composable
fun CardGrid(
    modifier: Modifier = Modifier,
    cards: List<GameCard> = listOf(),
    onCardFlipped: (index: Int) -> Unit = {}
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(cards) { index, card ->
            CardFlippable(
                state = card,
                onFlipped = {
                    onCardFlipped(index)
                }

            )
        }
    }
}


