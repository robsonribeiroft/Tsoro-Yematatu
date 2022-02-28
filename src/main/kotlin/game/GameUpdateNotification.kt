package game

interface GameUpdateNotification {
    fun inVictory(piece: Piece)
    fun drawRequested(piece: Piece)
    fun onBoardUpdated(board: HashMap<String, Piece>)
    fun onGameStageUpdate(gameStage: GameStage)
    fun onError(throwable: Throwable)
}