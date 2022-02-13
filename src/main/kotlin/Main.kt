import game.Board
import game.BoardPosition.*
import game.Piece
import game.Piece.RED
import ui.TsoroYematatuBoardSwing

fun main(){
//    Board(::inVictory, ::drawRequested, ::onBoardUpdated, ::onBoardError).run {
//        setPiece(position = LINE_0_COLUMN_0, piece = RED)
//        setPiece(position = LINE_1_COLUMN_0, piece = RED)
//        setPiece(position = LINE_0_COLUMN_0, piece = RED) // InvalidMovimentException
//        movePiece(origin = LINE_1_COLUMN_0, destiny = LINE_2_COLUMN_0)
//        movePiece(origin = LINE_1_COLUMN_0, destiny = LINE_2_COLUMN_0) // MissingPieceOriginException
//        setPiece(position = LINE_1_COLUMN_0, piece = RED)
//    }
    TsoroYematatuBoardSwing()
}

private fun inVictory(piece: Piece){
    printWinner(piece)
}

private fun drawRequested(piece: Piece){
    println("${piece.name} has requested a draw!")
}

private fun onBoardUpdated(board: HashMap<String, Piece>) {
    println("Board update!")
}

private fun onBoardError(throwable: Throwable){
    println(throwable)
}

private fun printWinner(piece: Piece){
    println("º\n" +
            "                  .       |         .    .\n" +
            "            .  *         -*-          *\n" +
            "                 \\        |         /   .\n" +
            "º    º            .      /^\\     .              º    º\n" +
            "   *    |\\   /\\    /\\  / / \\ \\  /\\    /\\   /|    *\n" +
            " º   º  |  \\ \\/ /\\ \\ / /     \\ \\ / /\\ \\/ /  | º     º\n" +
            "         \\ | _ _\\/_ _ \\_\\_ _ /_/_ _\\/_ _ \\_/\n" +
            "           \\  *  *  *   \\ \\/ /  *  *  *  /\n" +
            "            ` ~ ~ ~ ~ ~  ~\\/~ ~ ~ ~ ~ ~ '\n")
    println("\t\t\t\t\t   ${piece.name} wins! ")
}
