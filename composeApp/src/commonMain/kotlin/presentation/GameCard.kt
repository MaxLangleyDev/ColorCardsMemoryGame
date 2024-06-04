package presentation

import CardState
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    onFlipped: () -> Unit
) {
    val density = LocalDensity.current.density

    val rotationY by animateFloatAsState(
        targetValue = if (card.isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
    )

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
            FrontSide(modifier = modifier, card = card, rotationY = rotationY, density = density)
        } else {
            BackSide(modifier = modifier, rotationY = rotationY, density =  density)
        }
    }
}

@Composable
fun FrontSide(
    modifier: Modifier = Modifier,
    card: CardState,
    rotationY: Float,
    density: Float
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
                cameraDistance = 8 * density
            )
            .clip(RoundedCornerShape(8.dp))
            .background(card.color)
            .border(
                width = 4.dp,
                color = animatedBorderColor,
//                if (card.isCorrect && card.isSelected) Color.Magenta
//                else card.color,
                shape = RoundedCornerShape(8.dp))
            .alpha(if (rotationY <= 90f) 1f else 0f),
        contentAlignment = Alignment.Center
    ) {
        if (card.isCorrect){
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
    }
}