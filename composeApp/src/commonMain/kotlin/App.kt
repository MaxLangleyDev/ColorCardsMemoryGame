
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.GameContainer
import ui.theme.ColorCardsTheme


@Composable
@Preview
fun App() {
    ColorCardsTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            GameContainer()
        }
    }
}



