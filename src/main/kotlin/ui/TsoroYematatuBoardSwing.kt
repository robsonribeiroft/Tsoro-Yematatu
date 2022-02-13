package ui

import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel

class TsoroYematatuBoardSwing(title: String = "Tsoro Yematatu"): JFrame(title) {
    init {
        setSize(610, 800)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        isVisible = true
        val background = JLabel(ImageIcon(ImageIO.read(File("/home/robson/PPD/TsoroYematatu/src/main/resources/table.png"))))
        contentPane = background

        add(PieceSwing().apply { setBounds(PIECE_LINE_0_COLUMN_0_BOARD_X, PIECE_LINE_0_Y, PIECE_SIZE, PIECE_SIZE) })

        add(PieceSwing().apply { setBounds(PIECE_LINE_1_COLUMN_0_BOARD_X, PIECE_LINE_1_Y, PIECE_SIZE, PIECE_SIZE) })
        add(PieceSwing().apply { setBounds(PIECE_LINE_1_COLUMN_1_BOARD_X, PIECE_LINE_1_Y, PIECE_SIZE, PIECE_SIZE) })
        add(PieceSwing().apply { setBounds(PIECE_LINE_1_COLUMN_2_BOARD_X, PIECE_LINE_1_Y, PIECE_SIZE, PIECE_SIZE) })

        add(PieceSwing().apply { setBounds(PIECE_LINE_2_COLUMN_0_BOARD_X, PIECE_LINE_2_Y, PIECE_SIZE, PIECE_SIZE) })
        add(PieceSwing().apply { setBounds(PIECE_LINE_2_COLUMN_1_BOARD_X, PIECE_LINE_2_Y, PIECE_SIZE, PIECE_SIZE) })
        add(PieceSwing().apply { setBounds(PIECE_LINE_2_COLUMN_2_BOARD_X, PIECE_LINE_2_Y, PIECE_SIZE, PIECE_SIZE) })

    }

    fun initListeners(){
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                println("Closed")
                e.window.dispose()
            }
        })
    }

    companion object {
        private const val PIECE_SIZE = 75
        private const val PIECE_LINE_0_Y = 140
        private const val PIECE_LINE_0_COLUMN_0_BOARD_X = 275

        private const val PIECE_LINE_1_Y = 350
        private const val PIECE_LINE_1_COLUMN_0_BOARD_X = 120
        private const val PIECE_LINE_1_COLUMN_1_BOARD_X = 275
        private const val PIECE_LINE_1_COLUMN_2_BOARD_X = 435

        private const val PIECE_LINE_2_Y = 530
        private const val PIECE_LINE_2_COLUMN_0_BOARD_X = 15
        private const val PIECE_LINE_2_COLUMN_1_BOARD_X = 275
        private const val PIECE_LINE_2_COLUMN_2_BOARD_X = 510
    }
}