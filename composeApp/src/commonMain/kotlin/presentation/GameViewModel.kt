package presentation

import androidx.compose.ui.graphics.Color
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

    fun setupGame() {
        val cards = mutableListOf<CardState>()
        val colorsToFind = mutableListOf<Color>()
        val colors = _gameState.value.colors
        val targetColor = colors.random()

        var currentColorIndex = 0

        repeat(_gameState.value.totalCards) { index ->

            val currentColor = colors[currentColorIndex]
            colorsToFind.add(currentColor)

            cards.add(
                CardState(
                    cardNumber = index + 1,
                    isFlippedDown = true,
                    color = currentColor,
                    isCorrect = currentColor == targetColor
                )
            )

            currentColorIndex = (currentColorIndex + 1) % colors.size
        }

        cards.shuffle()

        _gameState.update { gameState ->
            gameState.copy(
                cards = cards,
                correctChoices = 0,
                targetColor = targetColor,
                colorsToFind = colorsToFind,
                points = 0,
                consecutiveMatches = 0,
                consecutiveFails = 0,
                gameLost = false,
                gameWon = false,
                gameCountdownProgress = 1f,
                pregameCountdownProgress = 1f,
                timedOut = false,
                threeStrikes = false
            )
        }
    }

    fun startGame() {
        viewModelScope.launch {
            _gameState.update { gameState ->
                gameState.copy(
                    showingStartScreen = false,
                    showingTransitionScreen = false,
                )
            }

            delay(320) // Wait for start screen to disappear

            _gameState.update { gameState ->
                gameState.copy(
                    showingGameScreen = true,
                    pregamePhase = true,
                )
            }

            delay(600) // Wait for game screen to appear

            flipAllCardsUp()

            pregameCountdown()

            delay((_gameState.value.pregameCountdownDuration * 1000).toLong()) // Delay before flipping all cards down (matches countdown)

            flipAllCardsDown(isSelectable = true)

            startGameTimer()

        }

    }

    fun playerFlipCardUp(
        cardIndex: Int,
        ) {

        val card = _gameState.value.cards[cardIndex]

        if (card.isSelectable && card.isFlippedDown){

            val cardsList = _gameState.value.cards.toMutableList()

            cardsList[cardIndex] =
                card.copy(
                    isFlippedDown = false,
                    isSelected = true,
                )

            _gameState.update { gameState ->
                gameState.copy(
                    cards = cardsList,
                    currentCardIndex = cardIndex
                )
            }

            checkForMatchAndUpdate(card)

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

    private fun pregameCountdown(){

        val interval = (_gameState.value.pregameCountdownDuration * 1000).toLong() / 20

        viewModelScope.launch {

            for (i in 1..20) {
                delay(interval)
                _gameState.update { gameState ->
                    gameState.copy(
                        pregameCountdownProgress = gameState.pregameCountdownProgress - 0.05f,
                    )
                }
            }

        }
    }

    private fun startGameTimer(){

        val interval = (_gameState.value.gameCountdownDuration * 1000).toLong() / 20

        viewModelScope.launch {

            _gameState.update { gameState ->
                gameState.copy(
                    pregamePhase = false,
                    gamePhase = true,
                )
            }

            for (i in 1..20) {

                if (gameState.value.gameLost || gameState.value.gameWon){
                    break
                }

                delay(interval)

                _gameState.update { gameState ->
                    gameState.copy(
                        gameCountdownProgress = gameState.gameCountdownProgress - 0.05f,
                    )
                }

            }

            if (!_gameState.value.gameWon) loseGame("timed_out")
        }
    }


    private fun updateConsecutiveMatches(){
        val consecutiveMatches = _gameState.value.consecutiveMatches + 1
        val pointsChange = 50 * consecutiveMatches

        _gameState.update { gameState ->
            gameState.copy(
                points = gameState.points + pointsChange,
                lastPointsChange = pointsChange,
                consecutiveMatches = consecutiveMatches,
                consecutiveFails = 0,
                correctChoices = gameState.correctChoices + 1,
            )
        }
    }

    private fun updateConsecutiveFails(){
        val consecutiveFails = _gameState.value.consecutiveFails + 1
        val pointsChange = 50 * consecutiveFails

        _gameState.update { gameState ->
            gameState.copy(
                perfectGame = false,
                consecutiveFails = consecutiveFails,
                consecutiveMatches = 0,
                points = gameState.points - pointsChange,
                lastPointsChange = -pointsChange
            )
        }
    }

    private fun updateColorsToFind(lastColorRevealed : Color){
        val mutableColorsToFind = _gameState.value.colorsToFind.toMutableList()
        val targetColor = _gameState.value.targetColor

        mutableColorsToFind.remove(lastColorRevealed)

        if (!mutableColorsToFind.contains(targetColor) && mutableColorsToFind.isNotEmpty()){
            setTargetColor(mutableColorsToFind.toSet().random())
        }

        _gameState.update { gameState ->
            gameState.copy(
                colorsToFind = mutableColorsToFind
            )
        }

    }

    private fun setTargetColor(targetColor: Color) {
        _gameState.update { gameState ->
            gameState.copy(
                targetColor = targetColor
            )
        }
    }

    private fun updateCorrectCards() {
        val mutableCardsList = _gameState.value.cards.toMutableList()

        mutableCardsList.forEachIndexed { index, gameCard ->
            if (gameCard.isFlippedDown){
                mutableCardsList[index] = gameCard.copy(
                    isCorrect = gameCard.color == _gameState.value.targetColor
                )
            }
        }

        _gameState.update { gameState ->
            gameState.copy(
                cards = mutableCardsList
            )
        }
    }

    private fun checkForMatchAndUpdate(lastFlippedCard : CardState){

        val mutableCardsList = _gameState.value.cards.toMutableList()

        if (lastFlippedCard.isCorrect){
            updateConsecutiveMatches()
        }
        else {
            updateConsecutiveFails()
            if (_gameState.value.consecutiveFails >= 3 && _gameState.value.gameOverOnThreeStrikes){
                loseGame("three_strikes")
            }
        }

        updateColorsToFind(lastColorRevealed = lastFlippedCard.color)

        if (_gameState.value.colorsToFind.isEmpty()){
            winGame()
        }
        else updateCorrectCards()

    }

    private fun loseGame(loseCondition: String = "timed_out"){
        viewModelScope.launch {
            flipAllCardsUp(isSelectable = false)

            delay(300)

            when (loseCondition){
                "timed_out" -> {
                    _gameState.update { gameState ->
                        gameState.copy(
                            perfectGame = false,
                            gameLost = true,
                            timedOut = true,
                            showingGameLostScreen = true
                        )
                    }
                }

                "three_strikes" -> {
                    _gameState.update { gameState ->
                        gameState.copy(
                            perfectGame = false,
                            gameLost = true,
                            threeStrikes = true,
                            showingGameLostScreen = true
                        )
                    }
                }

                else -> {
                    _gameState.update { gameState ->
                        gameState.copy(
                            perfectGame = false,
                            gameLost = true,
                            showingGameLostScreen = true
                        )
                    }
                }
            }
        }

    }

    private fun winGame() {
        _gameState.update { gameState ->
            gameState.copy(
                gameWon = true,
                showingGameWonScreen = true,
                points = if (gameState.perfectGame) {
                    gameState.points + 100
                } else {
                    gameState.points
                }
            )
        }
    }

    fun returnToMenu(){
        _gameState.update { gameState ->
            gameState.copy(
                showingStartScreen = true,
                showingGameScreen = false,
                showingGameLostScreen = false,
                showingGameWonScreen = false,
                showingTutorialScreen = false,
            )
        }
    }

    fun restartGame(){
        viewModelScope.launch {
            _gameState.update { gameState ->
                gameState.copy(
                    showingStartScreen = false,
                    showingGameScreen = false,
                    showingGameLostScreen = false,
                    showingGameWonScreen = false,
                    showingTransitionScreen = true,
                    )
            }

            delay(300)

            setupGame()

            startGame()
        }
    }

    fun showTutorial(){
        _gameState.update { gameState ->
            gameState.copy(
                showingTutorialScreen = true
            )
        }
    }

    fun dismissTutorial(){
        _gameState.update { gameState ->
            gameState.copy(
                showingTutorialScreen = false
            )
        }
    }

    fun showSettings(){
        _gameState.update { gameState ->
            gameState.copy(
                showingSettingsScreen = true
            )
        }
    }

    fun dismissSettings(){
        _gameState.update { gameState ->
            gameState.copy(
                showingSettingsScreen = false
            )
        }
    }

    fun setGameCountdownDuration(seconds: Int){

        _gameState.update { gameState ->
            gameState.copy(
                gameCountdownDuration = seconds
            )
        }

    }

    fun setPregameCountdownDuration(seconds: Int){

        _gameState.update { gameState ->
            gameState.copy(
                pregameCountdownDuration = seconds
            )
        }

    }

    fun setGameOverOnThreeStrikes(boolean: Boolean){
        _gameState.update { gameState ->
            gameState.copy(
                gameOverOnThreeStrikes = boolean
            )
        }
    }


}