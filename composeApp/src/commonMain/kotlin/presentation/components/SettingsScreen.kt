package presentation.components

import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import colorcardsmemorygame.composeapp.generated.resources.Res
import colorcardsmemorygame.composeapp.generated.resources.return_to_menu
import model.GameState
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    gameState: GameState = GameState(),
    onDismissSettings: () -> Unit,
    setPregameCountdown: (Int) -> Unit,
    setGameCountdown: (Int) -> Unit,
    setGameOverOnThreeStrikes: (Boolean) -> Unit
){

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {

        Text(text = "Settings")

        Spacer(modifier = Modifier.height(8.dp))

        // Game over on three strikes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Three Strikes: ")

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = gameState.gameOverOnThreeStrikes,
                onClick = {
                    setGameOverOnThreeStrikes(!gameState.gameOverOnThreeStrikes)
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Pregame Timer Duration
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Initial Countdown (Seconds): ")

            Spacer(modifier = Modifier.width(16.dp))

            var dropdownExpanded by remember { mutableStateOf(false) }

            Column {

                Text(
                    modifier = Modifier.clickable{
                        dropdownExpanded = !dropdownExpanded
                    },
                    text = gameState.pregameCountdownDuration.toString()
                )
                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ){
                    for (i in 1..60) {
                        DropdownMenuItem(
                            text = {Text(text = i.toString())},
                            onClick = {
                                setPregameCountdown(i)
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Game Timer Duration
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Game Timer (Seconds): ")

            Spacer(modifier = Modifier.width(16.dp))

            var dropdownExpanded by remember { mutableStateOf(false) }

            Column {

                Text(
                    modifier = Modifier.clickable{
                        dropdownExpanded = !dropdownExpanded
                    },
                    text = gameState.gameCountdownDuration.toString()
                )
                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ){
                    for (i in 1..60) {
                        DropdownMenuItem(
                            text = {Text(text = i.toString())},
                            onClick = {
                                setGameCountdown(i)
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(8.dp))



        Button(onClick = onDismissSettings) {
            Text(text = stringResource(Res.string.return_to_menu))
        }



    }

}