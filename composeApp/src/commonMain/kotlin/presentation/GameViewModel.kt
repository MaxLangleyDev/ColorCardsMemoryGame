package presentation

import model.CardState
import model.GameState
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

    fun userFlipCardUp(cardIndex: Int) {
        val card = _gameState.value.cards[cardIndex]

        if (card.isSelectable && card.isFlippedDown){

            val mutableCardsList = _gameState.value.cards.toMutableList()

            mutableCardsList[cardIndex] =
                card.copy(
                    isFlippedDown = false,
                    isSelected = true
                )

            _gameState.value = _gameState.value.copy(cards = mutableCardsList)

        }
    }

    private fun flipAllCardsDown(isSelectable: Boolean = false) {
        val mutableCardsList = _gameState.value.cards.toMutableList()

        mutableCardsList.forEachIndexed { index, gameCard ->
            mutableCardsList[index] = gameCard.copy(
                isFlippedDown = true,
                isSelectable = isSelectable
            )
        }

        _gameState.update { gameState ->
            gameState.copy(
                cards = mutableCardsList
            )
        }

    }

    private fun flipAllCardsUp(isSelectable: Boolean = false) {
        val mutableCardsList = _gameState.value.cards.toMutableList()

        mutableCardsList.forEachIndexed { index, gameCard ->
            mutableCardsList[index] = gameCard.copy(
                isFlippedDown = false,
                isSelectable = isSelectable
            )
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
        val cards = mutableListOf<CardState>()
        val colors = _gameState.value.colors
        val colorToFind = colors.random()

        var currentColorIndex = 0

        repeat(amountOfCards) { index ->
            cards.add(
                CardState(
                    cardNumber = index + 1,
                    isFlippedDown = true,
                    color = colors[currentColorIndex],
                    isCorrect = colors[currentColorIndex] == colorToFind
                )
            )

            currentColorIndex = (currentColorIndex + 1) % colors.size
        }

        cards.shuffle()

        _gameState.update { gameState ->
            gameState.copy(
                cards = cards,
                colorToFind = colorToFind
            )
        }
    }

    private fun decrementCountdown(increment: Float = 0.1f){

        _gameState.update { gameState ->
            gameState.copy(
                pregameCountdown = gameState.pregameCountdown - increment,
            )
        }
    }

    private fun pregameCountdown(seconds: Long = 3){

        val interval = (seconds * 1000) / 10
        viewModelScope.launch {
            for (i in 1..10) {
                delay(interval)
                _gameState.update { gameState ->
                    gameState.copy(
                        pregameCountdown = gameState.pregameCountdown - 0.1f,
                    )
                }
            }
        }
    }

    private fun startGameTimer(seconds: Long = 10){
        val interval = (seconds * 1000) / 10
        viewModelScope.launch {

            _gameState.update { gameState ->
                gameState.copy(
                    pregamePhase = false,
                    gamePhase = true,
                )
            }

            for (i in 1..10) {
                delay(interval)
                _gameState.update { gameState ->
                    gameState.copy(
                        gameCountdown = gameState.gameCountdown - 0.1f,
                    )
                }
            }
        }
    }


    fun startGame() {
        viewModelScope.launch {
            _gameState.update { gameState ->
                gameState.copy(
                    showingStartScreen = false
                )
            }

            delay(320)

            _gameState.update { gameState ->
                gameState.copy(
                    showingGameScreen = true,
                    pregamePhase = true
                )
            }

            delay(300)

            flipAllCardsUp()

            pregameCountdown()

            delay(3000) // Wait 3s before flipping all cards down (matches countdown)

            flipAllCardsDown(isSelectable = true)

            startGameTimer()

        }

    }

    fun updateGame(){

    }


}