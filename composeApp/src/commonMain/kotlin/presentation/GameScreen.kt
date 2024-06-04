package presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import presentation.components.GameBoard
import presentation.components.StartScreen

@Composable
fun GameScreen(){

    val viewModel = viewModel { GameViewModel() }
    val gameState by viewModel.gameState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().weight(0.1f).background(Color.Red),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text("TOP BAR")
            }
            Box(
                modifier = Modifier.weight(0.9f),
                contentAlignment = Alignment.Center
            ){
                this@Column.AnimatedVisibility(
                    visible = gameState.showStartScreen,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { -it })

                ){
                    StartScreen(
                        gameState = gameState,
                        setupGame = viewModel::setupGame,
                        startGame = viewModel::startGame
                    )
                }

                this@Column.AnimatedVisibility(
                    visible = gameState.showGameScreen,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { -it })
                ){
                    GameBoard(
                        modifier = Modifier.fillMaxSize(),
                        gameState = gameState,
                        onCardFlipped = viewModel::userFlipCardUp
                    )
                }
            }
        }
    }
}