package game.exceptions

import game.BoardPosition

class PieceNotFoundException(
    position: BoardPosition,
    message: String = "Theres is no PIECE set in position ${position}."
): Exception(message)