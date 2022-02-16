package game.exceptions

class SetPieceOnMovingStageException(
    message: String = "It is not possible to add more pieces to the board. Move a PIECE."
): Exception(message)