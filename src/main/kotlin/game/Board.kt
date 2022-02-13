package game

import game.BoardPosition.*
import game.Piece.EMPTY
import game.exceptions.InvalidMovimentException
import game.exceptions.MissingPieceOriginException
import game.exceptions.PieceNotFoundException

class Board(
    val inVictory: ((piece: Piece) -> Unit),
    val drawRequested: ((piece: Piece) -> Unit),
    val onBoardUpdated: ((HashMap<String, Piece>) -> Unit),
    val onError: ((Throwable) -> Unit)
) {
    private var _board: HashMap<String, Piece>
    private var drawRequest: Piece? = null

    init {
        _board = INITIAL_BOARD
        logBoard()
    }

    fun setPiece(position: BoardPosition, piece: Piece) = try {
        checkValidSetPiece(position, piece)
        _board[position.coordinates] = piece
        onBoardUpdated(_board)
        if (checkForWinner()){
            inVictory(piece)
        }
        logBoard()
    } catch (e: Exception) {
        onError(e)
    }

    fun movePiece(origin: BoardPosition, destiny: BoardPosition) = try {
        checkValidMovement(origin, destiny)
        _board[destiny.coordinates] = _board[origin.coordinates] ?: throw PieceNotFoundException(origin)
        _board[origin.coordinates] = EMPTY
        onBoardUpdated(_board)
        if (checkForWinner()){
            inVictory(_board[origin.coordinates] ?: throw PieceNotFoundException(origin))
        }
        logBoard()
    } catch (e: Exception){
        onError(e)
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