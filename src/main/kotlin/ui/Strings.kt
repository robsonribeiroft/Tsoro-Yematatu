package ui

import game.GameStage

const val GAME_STAGE_IDLE = "Waiting for the game to start!"
const val GAME_STAGE_SET_RED_PIECE = "RED player set a piece on the board."
const val GAME_STAGE_SET_BLUE_PIECE = "BLUE player set a piece on the board."
const val GAME_STAGE_MOVING_RED_PIECE = "RED player move a piece on the board."
const val GAME_STAGE_MOVING_BLUE_PIECE = "BLUE player move a piece on the board."

fun getMessageByGameStage(gameStage: GameStage): String {
    return when (gameStage) {
        GameStage.IDLE -> GAME_STAGE_IDLE
        GameStage.SET_RED -> GAME_STAGE_SET_RED_PIECE
        GameStage.SET_BLUE -> GAME_STAGE_SET_BLUE_PIECE
        GameStage.MOVING_RED -> GAME_STAGE_MOVING_RED_PIECE
        GameStage.MOVING_BLUE -> GAME_STAGE_MOVING_BLUE_PIECE
    }
}