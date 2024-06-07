package presentation.components.gameScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import model.CardState
import model.GameState
import presentation.components.TutorialScreen
import kotlin.math.roundToInt

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    rows: Int = 4,
    columns: Int = 3,
    gameState: GameState,
    onCardFlipped: (Int) -> Unit,
    onRestart: () -> Unit,
    onReturnToMenu: () -> Unit,
    dismissTutorial: () -> Unit,
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        GameDetailsBar(modifier = Modifier.weight(0.15f), gameState = gameState)

        GameBoard(
            modifier = Modifier.weight(0.85f),
            rows = rows,
            columns = columns,
            gameState = gameState,
            onCardFlipped = onCardFlipped
        )
    }

    AnimatedVisibility(
        visible = gameState.gameWon,
        enter = fadeIn(),
        exit = fadeOut()
    ){
        WinScreen(
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
        LoseScreen(
            modifier = modifier,
            gameState = gameState,
            onRestart = { onRestart() },
            onReturnToMenu = { onReturnToMenu() }
        )
    }

}

//@Composable
//fun AnimationBox(
//    modifier: Modifier = Modifier,
//    gameState: GameState = GameState(),
//){
//    Box(
//        modifier = modifier,
//    ){
//        for (card in gameState.animatingCards){
//            if (card.coordinates != null) {
//                println("Drawing in animation layer")
//
//                GameCard(
//                    modifier = Modifier
//                        .size(card.coordinates.size.width.dp, card.coordinates.size.height.dp)
//                        .offset { IntOffset(
//                        card.coordinates.positionInRoot().x.roundToInt(),
//                        card.coordinates.positionInRoot().y.roundToInt()
//                    ) },
//                    card = card,
//                )
//
//            }
//        }
//    }
//}