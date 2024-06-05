package presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import model.GameState

@Composable
fun GameBoard(
    gameState: GameState = GameState(),
    onCardFlipped: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    rows: Int = 4,
    columns: Int = 4
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(rows) { row ->
            Row(
                modifier = Modifier.weight(0.2f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(columns) { column ->

                    val index = row * columns + column
                    val card = gameState.cards[index]

                    key(card.cardNumber) {
                        GameCard(
                            card = card,
                            showColorOnBack = gameState.showColorOnBack,
                            onFlipped = { onCardFlipped(index) },
                            modifier = Modifier.weight(0.12f)
                        )
                    }
                }
            }
        }
    }
}