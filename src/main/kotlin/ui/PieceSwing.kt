package ui

import game.BoardPosition
import game.Piece
import java.awt.Color
import javax.swing.JButton

class PieceSwing(
    private var position: BoardPosition,
    private var piece: Piece = Piece.EMPTY,
    private val onClick: () -> Unit = {}
): JButton() {

    init {
        updateBackground(piece)
        addActionListener { onClick() }
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
}