package ui

import game.BoardPosition
import game.Piece
import java.awt.Color
import javax.swing.JButton

class PieceSwing(private var piece: Piece = Piece.EMPTY): JButton() {


    private var position: BoardPosition? = null

    init {
        setSize(50, 50)
        background = when (piece) {
            Piece.RED -> Color.RED
            Piece.BLUE -> Color.BLUE
            Piece.EMPTY -> Color.WHITE
        }
        addActionListener {

        }
    }

    fun setPosition(position: BoardPosition) {
        this.position = position
    }

    fun setPiece(piece: Piece) {
        this.piece = piece
    }
}