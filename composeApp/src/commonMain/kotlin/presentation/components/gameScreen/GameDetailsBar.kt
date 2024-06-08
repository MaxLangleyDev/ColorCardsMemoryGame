package presentation.components.gameScreen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import model.GameState
import kotlin.math.absoluteValue

@Composable
fun GameDetailsBar(
    modifier: Modifier = Modifier,
    gameState: GameState = GameState()
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (gameState.pregamePhase){
            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Memorize Each Color's Location!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Time Remaining:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(){
                LinearProgressIndicator(
                    progress = { gameState.pregameCountdownProgress },
                )
            }
        }
        else if (gameState.gamePhase){

            // Heading Text
            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Find: ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                    )

                Spacer(modifier = Modifier.width(4.dp))

                Box(Modifier.size(32.dp).background(color = gameState.targetColor))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Time Remaining Text
            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Time Remaining:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Bar
            Row(){
                LinearProgressIndicator(
                    progress = { gameState.gameCountdownProgress },
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Points Row
            Row(){
                Text(
                    text = "Points: ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = gameState.points.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))

                AnimatedPointsText(gameState = gameState)
            }

        }

    }

}

@Composable
fun AnimatedPointsText(
    modifier: Modifier = Modifier,
    gameState: GameState = GameState()
){

    var targetValue by remember { mutableStateOf(0f) }

    LaunchedEffect(gameState.lastPointsChange){
        targetValue = 1f

        delay(30)

        targetValue = 0f
    }

    val alphaValue = animateFloatAsState(
        targetValue = targetValue,
        animationSpec = keyframes {
            durationMillis = 1000
            0f at 0
            1f at 500
            0f at 1000
        }
    )


    Text(
        modifier = Modifier.graphicsLayer(
            alpha = alphaValue.value,
            scaleX = 1 + (gameState.consecutiveMatches.toFloat() * 0.1f),
            scaleY = 1 + (gameState.consecutiveMatches.toFloat() * 0.1f),
        ),
        text =
        if (gameState.lastPointsChange > 0){" + " + gameState.lastPointsChange }
        else if (gameState.lastPointsChange < 0){" - " + gameState.lastPointsChange.absoluteValue }
        else "",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}