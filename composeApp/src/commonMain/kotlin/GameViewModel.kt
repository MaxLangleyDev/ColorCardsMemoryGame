import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(
    initialGameState: GameState = GameState()
): ViewModel() {

    private val _gameState = MutableStateFlow(initialGameState)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    fun flipCard(cardIndex: Int) {
        val mutableCardsList = _gameState.value.cards.toMutableList()
        mutableCardsList[cardIndex] =
            mutableCardsList[cardIndex].copy(
                isFlipped = !mutableCardsList[cardIndex].isFlipped
            )
        _gameState.value = _gameState.value.copy(cards = mutableCardsList)
    }


}