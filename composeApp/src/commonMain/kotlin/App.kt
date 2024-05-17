import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            CardFlip()
        }

    }
}
@Composable
fun CardFlip() {
    val density = LocalDensity.current.density
    var isFlipped by remember { mutableStateOf(false) }

    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 600)
    )

    Box(
        modifier = Modifier
            .size(200.dp)
            .clickable { isFlipped = !isFlipped },
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

//@Composable
//fun CardFlip() {
//    var isFlipped by remember { mutableStateOf(false) }
//
//    val rotation by animateFloatAsState(
//        targetValue = if (isFlipped) 180f else 0f
//    )
//
//    Box(
//        modifier = Modifier
//            .size(200.dp)
//            .clickable { isFlipped = !isFlipped },
//        contentAlignment = Alignment.Center
//    ) {
//        if (rotation <= 90f) {
//            FrontSide(rotation)
//        } else {
//            BackSide(rotation - 180f)
//        }
//    }
//}
//
//@Composable
//fun FrontSide(rotation: Float) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .rotate(rotation)
//            .background(Color.Red),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text = "FRONT")
//    }
//}
//
//@Composable
//fun BackSide(rotation: Float) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .rotate(rotation)
//            .background(Color.Gray),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text = "Back")
//    }
//}