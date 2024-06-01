import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GameBoard(
    modifier: Modifier = Modifier,
    rows: Int = 4,
    columns: Int = 3,
    cardSize: Dp = 100.dp,
    gameState: GameState,
    onCardFlipped: (Int) -> Unit
){
    AnimatedVisibility(
        visible = gameState.gameStarted,
        enter = fadeIn(),
        exit = fadeOut()
    ){

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(rows) { row ->
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(columns) { column ->
                        val index = row * columns + column
                        CardFlippable(
                            card = gameState.cards[index],
                            onFlipped = { onCardFlipped(index) },
                            modifier = Modifier.weight(0.2f)
                        )
                    }
                }
            }
        }

    }

}