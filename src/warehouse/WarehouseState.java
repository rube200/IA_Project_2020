package warehouse;

import agentSearch.Action;
import agentSearch.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WarehouseState extends State implements Cloneable {
    private int[][] matrix;
    private int lineAgent, columnAgent;
    private int lineExit;
    private int columnExit;
    private int steps;

    public WarehouseState(int[][] matrix) {
        this.matrix = new int[matrix.length][matrix.length];
        this.steps = 0;

        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                if (this.matrix[i][j] == Properties.AGENT)
                {
                    this.lineExit = i;
                    this.columnExit = j;
                    this.lineAgent = i;
                    this.columnAgent = j;
                    break;
                }
            }
        }
    }

    public void executeAction(Action action) {
        action.execute(this);
    }

    public void executeActionSimulation(Action action) {
        action.execute(this);
        fireUpdatedEnvironment();
    }


    public boolean canMoveUp() {
        if (this.lineAgent <= 0)
            return false;

        if (this.matrix[this.lineAgent - 1][this.columnAgent] == Properties.SHELF)
            return false;

        return true;
    }

    public boolean canMoveRight() {
        if (this.columnAgent + 1 >= this.matrix.length)
            return false;

        if (this.matrix[this.lineAgent][this.columnAgent + 1] == Properties.SHELF)
            return false;

        return true;
    }

    public boolean canMoveDown() {
        if (this.lineAgent + 1 >= this.matrix.length)
            return false;

        if (this.matrix[this.lineAgent + 1][this.columnAgent] == Properties.SHELF)
            return false;

        return true;
    }

    public boolean canMoveLeft() {
        if (this.columnAgent <= 0)
            return false;

        if (this.matrix[this.lineAgent][this.columnAgent - 1] == Properties.SHELF)
            return false;

        return true;
    }

    public void moveUp() {
        this.setCellAgent(--this.lineAgent, this.columnAgent);
    }

    public void moveRight() {
        this.setCellAgent(this.lineAgent, ++this.columnAgent);
    }

    public void moveDown() {
        this.setCellAgent(++this.lineAgent, this.columnAgent);
    }

    public void moveLeft() {
        this.setCellAgent(this.lineAgent, --this.columnAgent);
    }

    public void setCellAgent(int line, int column) {
    	this.matrix[this.lineAgent][this.columnAgent] = Properties.EMPTY;
        this.lineAgent = line;
        this.columnAgent = column;
        this.matrix[line][column] = Properties.AGENT;
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
        return this.lineAgent;
    }

    public int getColumnAgent() {
        return this.columnAgent;
    }

    public int getLineExit() {
        return this.lineExit;
    }

    public int getColumnExit() {
        return this.columnExit;
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
    	clone.steps=this.steps;
        clone.columnAgent=this.columnAgent;
        clone.lineAgent=this.lineAgent;
        clone.columnExit=this.columnExit;
        clone.lineExit=this.lineExit;
        return  clone;
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