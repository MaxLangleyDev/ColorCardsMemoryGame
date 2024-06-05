@file:OptIn(ExperimentalResourceApi::class)

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import colorcardsmemorygame.composeapp.generated.resources.Res
import colorcardsmemorygame.composeapp.generated.resources.game_over
import colorcardsmemorygame.composeapp.generated.resources.perfect_game
import colorcardsmemorygame.composeapp.generated.resources.points
import colorcardsmemorygame.composeapp.generated.resources.restart
import colorcardsmemorygame.composeapp.generated.resources.return_to_menu
import colorcardsmemorygame.composeapp.generated.resources.three_strikes
import colorcardsmemorygame.composeapp.generated.resources.timed_out
import colorcardsmemorygame.composeapp.generated.resources.you_got_x_out_of_y
import colorcardsmemorygame.composeapp.generated.resources.you_won
import model.GameState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource


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
                            showColorOnBack = gameState.showColorOnBack,
                            onFlipped = { onCardFlipped(index) },
                            modifier = Modifier.weight(0.12f)
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
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
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
            Text(text = stringResource(Res.string.game_over))

            if (gameState.threeStrikes) {
                Text(text = stringResource(Res.string.three_strikes))
            }
            else Text(text = stringResource(Res.string.timed_out))

            Text(
                text = stringResource(
                    Res.string.you_got_x_out_of_y,
                    gameState.correctChoices,
                    gameState.totalCards
                )
            )

            Text(text = stringResource(Res.string.points, gameState.points))

            Button(
                onClick = onRestart
            ) {
                Text(text = stringResource(Res.string.restart))
            }

            Button(
                onClick = onReturnToMenu
            ) {
                Text(text = stringResource(Res.string.return_to_menu))
            }
        }
    }
}

@Composable
fun GameWonScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    gameState: GameState = GameState(),
    onRestart: () -> Unit,
    onReturnToMenu: () -> Unit

){
    Box(modifier = modifier, contentAlignment = Alignment.Center){
        Column(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.75f))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (gameState.perfectGame){
                Text(text = stringResource(Res.string.perfect_game))
            }
            else Text(text = stringResource(Res.string.you_won))

            Text(
                text = stringResource(
                Res.string.you_got_x_out_of_y,
                gameState.correctChoices,
                gameState.totalCards
                )
            )

            Text(text = stringResource(Res.string.points, gameState.points))

            Button(onClick = onRestart) {
                Text(text = stringResource(Res.string.restart))
            }

            Button(onClick = onReturnToMenu) {
                Text(text = stringResource(Res.string.return_to_menu))
            }
        }
    }
}