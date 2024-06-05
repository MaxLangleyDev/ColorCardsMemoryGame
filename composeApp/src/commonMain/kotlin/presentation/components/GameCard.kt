package presentation.components

import model.CardState
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    card: CardState = CardState(),
    showColorOnBack: Boolean = false,
    onFlipped: () -> Unit
) {

    val density = LocalDensity.current.density
    var shakeCard by remember { mutableStateOf(false) }
    val cameraDistance by remember { mutableStateOf(8 * density) }

    val rotationY by animateFloatAsState(
        targetValue = if (card.isFlippedDown) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
    )

    val infiniteTransition = rememberInfiniteTransition()
    val rotationCoefficient = infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 150, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val shadowElevation by animateFloatAsState(100f)

    Box(
        modifier = modifier
            .padding(4.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onFlipped() },
        contentAlignment = Alignment.Center
    ) {
        if (rotationY <= 90f) {
            shakeCard = card.isSelected && card.isCorrect
            FrontSide(
//                modifier = modifier,
                card = card,
                rotationY = rotationY,
                rotationZ = if (shakeCard && rotationY != 0f) rotationCoefficient.value * 30f else 0f,
                cameraDistance = cameraDistance,
                shadowElevation = shadowElevation
            )

        } else {
            BackSide(
//                modifier = modifier,
                card = card,
                showColorOnBack = showColorOnBack,
                rotationY = rotationY,
                density =  density
            )
        }
    }
}


@Composable
fun FrontSide(
    modifier: Modifier = Modifier,
    card: CardState,
    rotationY: Float,
    rotationZ: Float,
    cameraDistance: Float,
    shadowElevation: Float
) {
    val animatedBorderColor by animateColorAsState(
        targetValue = if (card.isCorrect && card.isSelected) Color.Magenta else card.color,
        animationSpec = tween(durationMillis = 600)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer(
                rotationY = rotationY,
                rotationZ = rotationZ,
                cameraDistance = cameraDistance,
                shadowElevation = shadowElevation
            )
            .clip(RoundedCornerShape(8.dp))
            .background(card.color)
            .border(
                width = 4.dp,
                color = animatedBorderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .alpha(if (rotationY <= 90f) 1f else 0f),
        contentAlignment = Alignment.Center
    ) {
        if (card.isCorrect) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star Icon",
                tint = Color.White
            )
        }
    }
}

@Composable
fun BackSide(
    modifier: Modifier = Modifier,
    card: CardState = CardState(),
    showColorOnBack: Boolean = false,
    rotationY: Float,
    density: Float
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer(
                rotationY = rotationY - 180f,
                cameraDistance = 8 * density
            )
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray)
            .alpha(if (rotationY > 90f) 1f else 0f),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "?", fontWeight = FontWeight.Bold, fontSize = 32.sp)

        if (showColorOnBack){
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(card.color)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}