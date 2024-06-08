@file:OptIn(ExperimentalResourceApi::class)

package presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import colorcardsmemorygame.composeapp.generated.resources.Res
import colorcardsmemorygame.composeapp.generated.resources.start_game
import colorcardsmemorygame.composeapp.generated.resources.tutorial_title
import model.GameState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@Composable
fun StartScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    gameState: GameState,
    setupGame: () -> Unit = {},
    startGame: () -> Unit = {},
    showTutorial: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                setupGame()
                startGame()
            },
        ){
            Text(text = stringResource(Res.string.start_game))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                showTutorial()
            },
        ){
            Text(text = stringResource(Res.string.tutorial_title))
        }

    }
}