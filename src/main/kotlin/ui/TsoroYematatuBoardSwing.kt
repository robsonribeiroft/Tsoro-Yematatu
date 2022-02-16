package ui

import game.Board
import game.BoardPosition
import game.GameStage
import game.Piece
import java.awt.event.ActionEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane

class TsoroYematatuBoardSwing(
    title: String = "Tsoro Yematatu",
): JFrame(title) {

    private val board = Board(
        ::inVictory,
        ::drawRequested,
        ::onBoardUpdated,
        ::onGameStageUpdate,
        ::onBoardError
    )

    private var movingOriginPosition: BoardPosition? = null

    private val btnLine0Column0 = PieceSwing(BoardPosition.LINE_0_COLUMN_0, ::onBtnClicked).apply {
        setBounds(PIECE_LINE_0_COLUMN_0_BOARD_X, PIECE_LINE_0_Y, PIECE_SIZE, PIECE_SIZE)
    }
    private val btnLine1Column0 = PieceSwing(BoardPosition.LINE_1_COLUMN_0, ::onBtnClicked).apply {
        setBounds(PIECE_LINE_1_COLUMN_0_BOARD_X, PIECE_LINE_1_Y, PIECE_SIZE, PIECE_SIZE)
    }
    private val btnLine1Column1 = PieceSwing(BoardPosition.LINE_1_COLUMN_1, ::onBtnClicked).apply {
        setBounds(PIECE_LINE_1_COLUMN_1_BOARD_X, PIECE_LINE_1_Y, PIECE_SIZE, PIECE_SIZE)
    }
    private val btnLine1Column2 = PieceSwing(BoardPosition.LINE_1_COLUMN_2, ::onBtnClicked).apply {
        setBounds(PIECE_LINE_1_COLUMN_2_BOARD_X, PIECE_LINE_1_Y, PIECE_SIZE, PIECE_SIZE)
    }
    private val btnLine2Column0 = PieceSwing(BoardPosition.LINE_2_COLUMN_0, ::onBtnClicked).apply {
        setBounds(PIECE_LINE_2_COLUMN_0_BOARD_X, PIECE_LINE_2_Y, PIECE_SIZE, PIECE_SIZE)
    }
    private val btnLine2Column1 = PieceSwing(BoardPosition.LINE_2_COLUMN_1, ::onBtnClicked).apply {
        setBounds(PIECE_LINE_2_COLUMN_1_BOARD_X, PIECE_LINE_2_Y, PIECE_SIZE, PIECE_SIZE)
    }
    private val btnLine2Column2 = PieceSwing(BoardPosition.LINE_2_COLUMN_2, ::onBtnClicked).apply {
        setBounds(PIECE_LINE_2_COLUMN_2_BOARD_X, PIECE_LINE_2_Y, PIECE_SIZE, PIECE_SIZE)
    }

    init {

        setSize(610, 800)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        isVisible = true
        val background = JLabel(ImageIcon(ImageIO.read(File("/home/robson/PPD/TsoroYematatu/src/main/resources/table.png"))))
        contentPane = background

        add(btnLine0Column0)
        add(btnLine1Column0)
        add(btnLine1Column1)
        add(btnLine1Column2)
        add(btnLine2Column0)
        add(btnLine2Column1)
        add(btnLine2Column2)

    }

    private fun onBtnClicked(actionEvent: ActionEvent, position: BoardPosition){
        when (board.gameStage) {
            GameStage.SET_RED, GameStage.SET_BLUE -> {
                val piece = if (board.gameStage == GameStage.SET_RED) Piece.RED else Piece.BLUE
                board.setPiece(position, piece)
            }
            GameStage.MOVING_RED, GameStage.MOVING_BLUE -> {
                movingOriginPosition?.let { originPosition ->
                    board.movePiece(originPosition, position)
                    movingOriginPosition = null
                } ?: run {
                    movingOriginPosition = position
                }
            }
        }
    }

    private fun inVictory(piece: Piece){
        showMessage("The $piece Wins!!!")
    }

    private fun drawRequested(piece: Piece){
        showMessage("${piece.name} has requested a draw!")
    }

    private fun onBoardUpdated(board: HashMap<String, Piece>) {
        btnLine0Column0.updateFromBoard(board)
        btnLine1Column0.updateFromBoard(board)
        btnLine1Column1.updateFromBoard(board)
        btnLine1Column2.updateFromBoard(board)
        btnLine2Column0.updateFromBoard(board)
        btnLine2Column1.updateFromBoard(board)
        btnLine2Column2.updateFromBoard(board)
    }

    private fun onBoardError(throwable: Throwable){
        movingOriginPosition = null
        showError(throwable)
    }

    private fun onGameStageUpdate(gameStage: GameStage){
        println("onGameStageUpdate: $gameStage")
    }

    fun initListeners(){
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                println("Closed")
                e.window.dispose()
            }
        })
    }

    private fun showMessage(message: String){
        JOptionPane.showMessageDialog(null, message)
    }

    private fun showError(throwable: Throwable){
        JOptionPane.showMessageDialog(null, throwable.message)
    }
}