package game

import game.BoardPosition.*
import game.Piece.EMPTY
import game.exceptions.*

class Board(
    val inVictory: ((piece: Piece) -> Unit),
    val drawRequested: ((piece: Piece) -> Unit),
    val onBoardUpdated: ((HashMap<String, Piece>) -> Unit),
    val onGameStageUpdate: ((gameStage: GameStage) ->  Unit),
    val onError: ((Throwable) -> Unit)
) {
    private var _board: HashMap<String, Piece>
    private var gameStage: GameStage = GameStage.IDLE
        set(value) {
            field = value
            onGameStageUpdate(value)
        }
    private var drawRequest: Piece? = null

    init {
        _board = INITIAL_BOARD
        gameStage = GameStage.SET_RED
        logBoard()
    }

    fun setPiece(position: BoardPosition, piece: Piece) = try {
        checkTurnOnSetStage(piece)
        checkValidSetPiece(position, piece)
        if (checkStageSetIsDone()){
            throw SetPieceOnMovingStageException()
        }
        _board[position.coordinates] = piece
        onBoardUpdated(_board)
        if (checkForWinner()){
            inVictory(piece)
        }
        updateGameStage(piece)
        logBoard()
    } catch (e: Exception) {
        onError(e)
    }

    fun movePiece(origin: BoardPosition, destiny: BoardPosition) = try {
        val originPiece = _board[origin.coordinates] ?: throw PieceNotFoundException(origin)
        checkTurnOnMovingStage(originPiece)
        checkValidMovement(origin, destiny)
        _board[destiny.coordinates] = originPiece
        _board[origin.coordinates] = EMPTY
        onBoardUpdated(_board)
        logBoard()
        if (checkForWinner()){
            inVictory(_board[destiny.coordinates] ?: throw PieceNotFoundException(destiny))
        }
        updateGameStage(_board[destiny.coordinates] ?: throw PieceNotFoundException(origin))

    } catch (e: Exception){
        onError(e)
    }

    private fun updateGameStage(piece: Piece) {
            val checkStageSetIsDone = checkStageSetIsDone()
        gameStage = when {
            piece == Piece.RED && !checkStageSetIsDone -> GameStage.SET_BLUE
            piece == Piece.BLUE && !checkStageSetIsDone -> GameStage.SET_RED
            piece == Piece.RED && checkStageSetIsDone -> GameStage.MOVING_BLUE
            piece == Piece.BLUE && checkStageSetIsDone -> GameStage.MOVING_RED
            else -> throw Exception("on updateGameStage: Empty piece found")
        }
    }

    private fun checkTurnOnMovingStage(piece: Piece){
        if (gameStage == GameStage.MOVING_RED && piece == Piece.BLUE)
            throw InvalidTurnException(piece)
        if (gameStage == GameStage.MOVING_BLUE && piece == Piece.RED)
            throw InvalidTurnException(piece)
    }

    private fun checkTurnOnSetStage(piece: Piece){
        if (gameStage == GameStage.SET_RED && piece == Piece.BLUE)
            throw InvalidTurnException(piece)
        if (gameStage == GameStage.SET_BLUE && piece == Piece.RED)
            throw InvalidTurnException(piece)
    }

    private fun checkValidMovement(origin: BoardPosition, destiny: BoardPosition) {
        if (_board[origin.coordinates] == EMPTY){
            throw MissingPieceOriginException(origin, destiny)
        }
        if (_board[destiny.coordinates] != EMPTY){
            throw InvalidMovimentException()
        }
    }

    fun requestDraw(piece: Piece){
        this.drawRequest = piece
        drawRequested(piece)
    }

    fun setBoard(board: HashMap<String, Piece>) {
        this._board = board
        onBoardUpdated(board)
    }
    private fun checkValidSetPiece(position: BoardPosition, piece: Piece) {
        if (_board[position.coordinates] != EMPTY){
            throw InvalidMovimentException()
        }
    }

    private fun checkForWinner(): Boolean{
        return ((_board[LINE_0_COLUMN_0.coordinates] == _board[LINE_1_COLUMN_0.coordinates])
                && (_board[LINE_0_COLUMN_0.coordinates] == _board[LINE_2_COLUMN_0.coordinates])
                && _board[LINE_0_COLUMN_0.coordinates] != EMPTY)
            || ((_board[LINE_0_COLUMN_0.coordinates] == _board[LINE_1_COLUMN_1.coordinates])
                && (_board[LINE_0_COLUMN_0.coordinates] == _board[LINE_2_COLUMN_1.coordinates])
                && _board[LINE_0_COLUMN_0.coordinates] != EMPTY)
            || ((_board[LINE_0_COLUMN_0.coordinates] == _board[LINE_1_COLUMN_2.coordinates])
                && (_board[LINE_0_COLUMN_0.coordinates] == _board[LINE_2_COLUMN_2.coordinates])
                && _board[LINE_0_COLUMN_0.coordinates] != EMPTY)
            || ((_board[LINE_1_COLUMN_0.coordinates] == _board[LINE_1_COLUMN_1.coordinates])
                && (_board[LINE_1_COLUMN_0.coordinates] == _board[LINE_1_COLUMN_2.coordinates])
                && _board[LINE_1_COLUMN_0.coordinates] != EMPTY)
            || ((_board[LINE_2_COLUMN_0.coordinates] == _board[LINE_2_COLUMN_1.coordinates])
                && (_board[LINE_2_COLUMN_0.coordinates] == _board[LINE_2_COLUMN_2.coordinates])
                && _board[LINE_2_COLUMN_0.coordinates] != EMPTY)
    }

    private fun checkStageSetIsDone(): Boolean {
        return _board.filterValues { piece -> piece == EMPTY }.size == 1
    }

    private fun logBoard(){
        println(
            "\t\t\t\t\t\t(${_board[LINE_0_COLUMN_0.coordinates]})\n" +
                    "\t\t(${_board[LINE_1_COLUMN_0.coordinates]}) -------- " +
                    "(${_board[LINE_1_COLUMN_1.coordinates]}) -------- " +
                    "(${_board[LINE_1_COLUMN_2.coordinates]})\n" +
                    "(${_board[LINE_2_COLUMN_0.coordinates]}) ---------------- " +
                    "(${_board[LINE_2_COLUMN_1.coordinates]}) ----------------- " +
                    "(${_board[LINE_2_COLUMN_2.coordinates]})\n"
        )
    }


    companion object {
        private val INITIAL_BOARD: HashMap<String, Piece> = hashMapOf(
            LINE_0_COLUMN_0.coordinates to EMPTY,
            LINE_1_COLUMN_0.coordinates to EMPTY,
            LINE_1_COLUMN_1.coordinates to EMPTY,
            LINE_1_COLUMN_2.coordinates to EMPTY,
            LINE_2_COLUMN_0.coordinates to EMPTY,
            LINE_2_COLUMN_1.coordinates to EMPTY,
            LINE_2_COLUMN_2.coordinates to EMPTY,
        )
    }
}