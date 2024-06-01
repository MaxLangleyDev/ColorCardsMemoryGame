import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun CardFlippable(
    modifier: Modifier = Modifier,
    card: GameCard = GameCard(),
    onFlipped: () -> Unit
) {
    val density = LocalDensity.current.density

    val rotationY by animateFloatAsState(
        targetValue = if (card.isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
    )

    Box(
        modifier = modifier
            .sizeIn(50.dp, 50.dp, 150.dp, 150.dp)
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
    card: GameCard,
    rotationY: Float,
    density: Float
) {
    Box(
        modifier = modifier
            .sizeIn(50.dp, 50.dp, 100.dp, 100.dp)
            .graphicsLayer(
                rotationY = rotationY,
                cameraDistance = 8 * density
            )
            .background(card.color)
            .alpha(if (rotationY <= 90f) 1f else 0f),
        contentAlignment = Alignment.Center
    ) {
        // Add your front side content here
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
            .sizeIn(50.dp, 50.dp, 150.dp, 150.dp)
            .graphicsLayer(
                rotationY = rotationY - 180f,
                cameraDistance = 8 * density
            )
            .background(Color.Gray)
            .alpha(if (rotationY > 90f) 1f else 0f),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Star Icon",
            tint = Color.White
        )
    }
}