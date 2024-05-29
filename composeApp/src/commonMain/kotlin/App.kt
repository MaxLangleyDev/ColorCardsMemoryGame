import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            
            CardGrid()

        }

    }
}

@Composable
fun CardGrid(
    modifier: Modifier = Modifier,
    cards: List<CardState> = listOf()
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ){
        items(10){
            val cardState = remember { mutableStateOf(CardState()) }
            CardFlippable(
                state = cardState.value,
                onFlipped = {
                    cardState.value =
                        cardState.value.copy(isFlipped = !cardState.value.isFlipped)
                }

            )
        }
    }
}


