package presentation.components.gameScreen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import model.GameState

@Composable
fun GameBoard(
    gameState: GameState = GameState(),
    onCardFlipped: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    rows: Int = 4,
    columns: Int = 4
) {

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(columns),
        ) {

            itemsIndexed(gameState.cards) { index, card ->
                GameCard(
                    modifier = Modifier.size(
                        width = (maxWidth / columns) - 8.dp,
                        height = (maxHeight / rows) - 8.dp
                    )
                        .zIndex(if (index == gameState.currentCardIndex) 10f else 5f),
                    card = card,
                    showColorOnBack = gameState.showColorOnBack,
                    onFlipped = { onCardFlipped(index) },
                )
            }

        }
    }
}

