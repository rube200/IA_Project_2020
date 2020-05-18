package warehouse;

import agentSearch.Action;
import agentSearch.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WarehouseState extends State implements Cloneable {

    //TODO this class might require the definition of additional methods and/or attributes

    //ref 8 puzzle state
    private int[][] matrix;
    private int lineAgent, columnAgent;
    private int lineExit;
    private int columnExit;
    private int steps;

    public WarehouseState(int[][] matrix) {
        this.matrix = new int[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                if (this.matrix[i][j] == 1) {
                    lineExit = lineAgent = i;
                    columnExit = columnAgent = j;
                }
            }
        }
    }
    // vai ser preciso outro construtor para search2
    public void executeAction(Action action) {
        action.execute(this);
    }

    public void executeActionSimulation(Action action) {
        action.execute(this);
        fireUpdatedEnvironment();
    }


    public boolean canMoveUp() {
        return lineAgent != 0 && lineAgent-1!= Properties.SHELF;
    }

    public boolean canMoveRight() {
        return columnAgent != matrix.length - 1 && columnAgent+1!= Properties.SHELF;
    }

    public boolean canMoveDown() {
        return lineAgent != matrix.length - 1 && lineAgent + 1 != Properties.SHELF;
    }

    public boolean canMoveLeft() {
        return columnAgent != 0 && columnAgent-1!= Properties.SHELF;}

    public void moveUp() {
        setCellAgent(lineAgent--, columnAgent);
    }

    public void moveRight() {
        setCellAgent(lineAgent, columnAgent++);
    }

    public void moveDown() {
        setCellAgent(lineAgent++, columnAgent);
    }

    public void moveLeft() {
        setCellAgent(lineAgent, columnAgent--);
    }

    public void setCellAgent(int line, int column) {
        matrix[lineAgent][columnAgent]=0;
        lineAgent=line;
        columnAgent=column;
        matrix[lineAgent][columnAgent]=1;
        }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getSize() {
        return matrix.length;
    }

    public Color getCellColor(int line, int column) {
        if (line == lineExit && column == columnExit && (line != lineAgent || column != columnAgent))
            return Properties.COLOREXIT;

        switch (matrix[line][column]) {
            case Properties.AGENT:
                return Properties.COLORAGENT;
            case Properties.SHELF:
                return Properties.COLORSHELF;
            default:
                return Properties.COLOREMPTY;
        }
    }

    public int getLineAgent() {
        return lineAgent;
    }

    public int getColumnAgent() {
        return columnAgent;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof WarehouseState)) {
            return false;
        }

        WarehouseState o = (WarehouseState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    @Override
    public WarehouseState clone() {
        WarehouseState clone = new WarehouseState(matrix);
        clone.columnAgent = this.columnAgent;
        clone.lineAgent = this.lineAgent;
        clone.columnExit = this.columnExit;
        clone.lineAgent = this.lineExit;

        return clone;
    }

    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }

}
