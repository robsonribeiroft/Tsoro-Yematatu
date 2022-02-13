package game.exceptions

import game.BoardPosition

class MissingPieceOriginException(
    origin: BoardPosition,
    destiny: BoardPosition,
    message: String = "This move is not valid. There is  no PIECE in the location to move. (${origin} to ${destiny})."
): Exception(message)