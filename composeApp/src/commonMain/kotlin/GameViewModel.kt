import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    initialGameState: GameState = GameState()
): ViewModel() {

    private val _gameState = MutableStateFlow(initialGameState)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    fun flipCard(cardIndex: Int) {
        if (_gameState.value.cardsSelectable){
            val mutableCardsList = _gameState.value.cards.toMutableList()
            mutableCardsList[cardIndex] =
                mutableCardsList[cardIndex].copy(
                    isFlipped = !mutableCardsList[cardIndex].isFlipped
                )
            _gameState.value = _gameState.value.copy(cards = mutableCardsList)
        }
    }
    fun checkForMatch() {

    }

    private fun flipAllCardsDown() {
        val mutableCardsList = _gameState.value.cards.toMutableList()

        mutableCardsList.forEachIndexed { index, gameCard ->
            mutableCardsList[index] = gameCard.copy(isFlipped = true)
        }

        _gameState.update { gameState ->
            gameState.copy(
                cards = mutableCardsList
            )
        }

    }

    private fun flipAllCardsUp() {
        val mutableCardsList = _gameState.value.cards.toMutableList()

        mutableCardsList.forEachIndexed { index, gameCard ->
            mutableCardsList[index] = gameCard.copy(isFlipped = false)
        }

        _gameState.update { gameState ->
            gameState.copy(
                cards = mutableCardsList
            )
        }

    }

    fun setupGame(
        amountOfCards: Int,
    ) {
        val cards = mutableListOf<GameCard>()
        val colors = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow)

        var currentColorIndex = 0

        repeat(amountOfCards) { index ->
            cards.add(
                GameCard(
                    cardNumber = index + 1,
                    isFlipped = true,
                    color = colors[currentColorIndex]
                )
            )

            currentColorIndex = (currentColorIndex + 1) % colors.size
        }

        cards.shuffle()

        _gameState.update { gameState ->
            gameState.copy(
                cards = cards,
                colorToFind = colors.random()
            )
        }
    }

    fun startGame() {
        _gameState.update { gameState ->
            gameState.copy(
                gameStarted = true
            )
        }

        viewModelScope.launch {

            flipAllCardsUp()

            delay(4000)

            flipAllCardsDown()

            _gameState.update { gameState ->
                gameState.copy(
                    cardsSelectable = true
                )
            }

        }

    }


}