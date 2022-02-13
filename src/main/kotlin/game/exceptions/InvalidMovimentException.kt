package game.exceptions

class InvalidMovimentException(
    message: String = "This move is not valid. There is already a PIECE in the intended location."
): Exception(message)