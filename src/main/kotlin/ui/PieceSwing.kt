package ui

import game.BoardPosition
import game.Piece
import game.exceptions.PieceNotFoundException
import java.awt.Color
import java.awt.event.ActionEvent
import java.util.HashMap
import javax.swing.JButton

class PieceSwing(
    private val position: BoardPosition,
    private val onClick: ((event: ActionEvent, position: BoardPosition) -> Unit)
): JButton() {

    private var piece: Piece = Piece.EMPTY

    init {
        updateBackground(piece)
        addActionListener { actionEvent -> onClick(actionEvent, position) }
    }

    override fun setActionCommand(actionCommand: String?) {
        super.setActionCommand(actionCommand)
    }

    fun setPiece(piece: Piece) {
        this.piece = piece
        updateBackground(piece)
    }

    private fun updateBackground(piece: Piece){
        background = when (piece) {
            Piece.RED -> Color.RED
            Piece.BLUE -> Color.BLUE
            Piece.EMPTY -> Color.WHITE
        }
    }

    fun updateFromBoard(board: HashMap<String, Piece>) {
        val piece: Piece = board[position.coordinates] ?: throw PieceNotFoundException(position)
        setPiece(piece)
    }
}