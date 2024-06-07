package presentation.components.gameScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.zIndex
import model.GameState

@Composable
fun GameBoard(
    gameState: GameState = GameState(),
    onCardFlipped: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    rows: Int = 4,
    columns: Int = 4
){

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(columns),
    ){

        itemsIndexed(gameState.cards) { index, card ->
            key(card.cardNumber) {
                GameCard(
                    modifier = Modifier
                        .zIndex(if (gameState.animatingCards.contains(card)) 10f else 5f),
                    card = card,
                    showColorOnBack = gameState.showColorOnBack,
                    onFlipped = { onCardFlipped(index) },
                )
            }
        }

    }
//    Column(
//        modifier = modifier,
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//

//        repeat(rows) { row ->
//            Row(
//                modifier = Modifier.weight(0.2f),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {

//                repeat(columns) { column ->
//
//                    val index = row * columns + column
//                    val card = gameState.cards[index]
//
//                    key(card.cardNumber) {
//                        var coordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }
//                        GameCard(
//                            modifier = Modifier
//                                .weight(0.12f)
//                                .onGloballyPositioned { coordinates = it }
//                                .zIndex(if (gameState.animatingCards.contains(card)) 1f else 0f),
//                            card = card,
//                            showColorOnBack = gameState.showColorOnBack,
//                            onFlipped = { onCardFlipped(index, coordinates) },
//                        )
//                    }
//                }
//            }
//        }
//    }
}