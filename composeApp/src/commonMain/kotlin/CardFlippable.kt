import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
    state: CardState = CardState(),
    onFlipped: () -> Unit
) {
    val density = LocalDensity.current.density

    val rotationY by animateFloatAsState(
        targetValue = if (state.isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
    )

    Box(
        modifier = Modifier
            .size(200.dp)
            .padding(4.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onFlipped() },
        contentAlignment = Alignment.Center
    ) {
        if (rotationY <= 90f) {
            FrontSide(rotationY, density)
        } else {
            BackSide(rotationY, density)
        }
    }
}

@Composable
fun FrontSide(rotationY: Float, density: Float) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .graphicsLayer(
                rotationY = rotationY,
                cameraDistance = 8 * density
            )
            .background(Color.Red)
            .alpha(if (rotationY <= 90f) 1f else 0f),
        contentAlignment = Alignment.Center
    ) {
        // Add your front side content here
    }
}

@Composable
fun BackSide(rotationY: Float, density: Float) {

    Box(
        modifier = Modifier
            .size(200.dp)
            .graphicsLayer(
                rotationY = rotationY - 180f,
                cameraDistance = 8 * density
            )
            .background(Color.Blue)
            .alpha(if (rotationY > 90f) 1f else 0f),
        contentAlignment = Alignment.Center
    ) {
        // Add your back side content here
    }
}