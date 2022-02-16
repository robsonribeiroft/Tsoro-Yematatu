package game.exceptions

import game.Piece

class InvalidTurnException(
    piece: Piece,
    message: String = "Cannot add Piece $piece. The turn belongs to the other player"
): Exception(message)