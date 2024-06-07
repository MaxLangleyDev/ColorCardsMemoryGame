@file:OptIn(ExperimentalResourceApi::class)

package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import colorcardsmemorygame.composeapp.generated.resources.Res
import colorcardsmemorygame.composeapp.generated.resources.good_luck
import colorcardsmemorygame.composeapp.generated.resources.im_ready
import colorcardsmemorygame.composeapp.generated.resources.return_to_menu
import colorcardsmemorygame.composeapp.generated.resources.tutorial_message1
import colorcardsmemorygame.composeapp.generated.resources.tutorial_message2
import colorcardsmemorygame.composeapp.generated.resources.tutorial_message3
import colorcardsmemorygame.composeapp.generated.resources.tutorial_title
import model.GameState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@Composable
fun TutorialScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    gameState: GameState = GameState(),
    onRestart: () -> Unit,
    onReturnToMenu: () -> Unit,
    dismissTutorial: () -> Unit

){
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.background.copy(alpha = 0.75f)),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background.copy(alpha = 1f))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(Res.string.tutorial_title),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(Res.string.tutorial_message1),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(Res.string.tutorial_message2),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(Res.string.tutorial_message3),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(Res.string.good_luck),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))


//            Button(onClick = dismissTutorial ) {
//                Text(text = stringResource(Res.string.im_ready))
//            }

            Button(onClick = onReturnToMenu) {
                Text(text = stringResource(Res.string.return_to_menu))
            }
        }
    }
}