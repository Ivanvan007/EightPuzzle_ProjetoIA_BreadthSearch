import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.Double.MAX_VALUE;


public class EightPuzzle implements IState {
    private int board[][];
    private int hash = Integer.MAX_VALUE;
    private int[][] goalBoard = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8}
    };
    public static IState getInitialState() {
        int[][] iBoard = {
                {3, 0, 1},
                {6, 5, 2},
                {7, 4, 8}
        };
        return new EightPuzzle(iBoard);
    }

    public EightPuzzle(int[][] novo) {
        board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = novo[i][j];
            }
        }
    }

    public EightPuzzle() {
        board = new int[3][3];
    }

    // Distancia de Manhattan ou função heuristica
        public double h() {
            int h = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int value = board[i][j];
                    if (value != 0) {
                        int targetRow = value / 3;
                        int targetCol = value% 3;
                        h += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                    }
                }
            }
            return h;
        }

    public boolean goal() {
        return Arrays.deepEquals(board, goalBoard);
    }


    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o.getClass() != EightPuzzle.class) {
            return false;
        }

        if (this == o) {
            return true;
        }

        EightPuzzle oo = (EightPuzzle) o;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != oo.get(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }


    /**public int hashCode() {
        int result = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = board[i][j];
                if (value != 0) {
                    int targetRow = (value - 1) / 3;
                    int targetCol = (value - 1) % 3;
                    result += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
        return result;
    }
     */
    @Override
    public int hashCode()
    {
        int result = 17; // Inicialize um valor inicial arbitrário

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                int value = board[i][j];
                result = 13 * result + value; // Use uma combinação de operações para calcular o hashCode
            }
        }

        return result;
    }

    public ArrayList<Action> suc() {
        int indexL = -1;
        int indexC = -1;
        int[][] nBoard = new int[3][3];
        ArrayList<Action> s = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                nBoard[i][j] = board[i][j];
                if (board[i][j] == 0) {
                    indexL = i;
                    indexC = j;
                }
            }
        }

        // Verifique as posições acima, abaixo, à esquerda e à direita do zero

        double custo = MAX_VALUE ;
        EightPuzzle newBoard;
        if (indexL - 1 >= 0) {
            // Movimento para cima é possível
            int[][] upBoard = cloneMatrix(nBoard);
            upBoard[indexL][indexC] = upBoard[indexL - 1][indexC];
            upBoard[indexL - 1][indexC] = 0;
            newBoard = new EightPuzzle(upBoard);
            custo = newBoard.h();
            s.add(new Action(newBoard, custo));

        }

        if (indexL + 1 <= 2) {
            // Movimento para baixo é possível
            int[][] downBoard = cloneMatrix(nBoard);
            downBoard[indexL][indexC] = downBoard[indexL + 1][indexC];
            downBoard[indexL + 1][indexC] = 0;
            newBoard = new EightPuzzle(downBoard);
            if(newBoard.h()<custo)
            {
                custo = newBoard.h();
                s.add(new Action(newBoard, custo));
            }
        }

        if (indexC - 1 >= 0) {
            // Movimento para a esquerda é possível
            int[][] leftBoard = cloneMatrix(nBoard);
            leftBoard[indexL][indexC] = leftBoard[indexL][indexC - 1];
            leftBoard[indexL][indexC - 1] = 0;
            newBoard = new EightPuzzle(leftBoard);
            if(newBoard.h()<custo)
            {
                custo = newBoard.h();
                s.add(new Action(newBoard, custo));
            }
        }

        if (indexC + 1 <= 2) {
            // Movimento para a direita é possível
            int[][] rightBoard = cloneMatrix(nBoard);
            rightBoard[indexL][indexC] = rightBoard[indexL][indexC + 1];
            rightBoard[indexL][indexC + 1] = 0;
            newBoard = new EightPuzzle(rightBoard);
            if(newBoard.h()<custo)
            {
                custo = newBoard.h();
                s.add(new Action(newBoard, custo));
            }
        }
        return s;
    }

    public int [][] cloneMatrix(int [][] matriz)
    {
        int [][] newBoard = new int [3][3];
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0 ; j < 3 ; j++)
            {
                newBoard[i][j]=matriz[i][j];
            }
        }
        return newBoard;

    }

    public int get(int i, int j) {
        return board[i][j];
    }

    // Função para clonar um tabuleiro
  /**
   private int[][] cloneBoard(int[][] source)
    {
        int[][] clone = new int[3][3];
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                clone[i][j] = source[i][j];
            }
        }
        return clone;
    }
    */

    // toString
    public String toString()
    {
        StringBuilder boardString = new StringBuilder();
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                boardString.append(board[i][j]);
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }
}

