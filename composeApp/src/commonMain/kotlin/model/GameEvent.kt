package model

sealed interface GameEvent {
    data object TimedOut : GameEvent
    data object ThreeStrikes : GameEvent
    data object FoundAllCorrectCards : GameEvent
}