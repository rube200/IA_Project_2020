package warehouse;

import java.util.Objects;

public class Pair {
    private Cell cell1;
    private Cell cell2;
    private int value;

    public Pair(Cell cell1, Cell cell2) {
        this.cell1 = cell1;
        this.cell2 = cell2;
    }

    public Cell getCell1() {
        return cell1;
    }

    public Cell getCell2() {
        return cell2;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Pair pair = (Pair) o;
        return this.cell1.equals(pair.cell1) && this.cell2.equals(pair.cell2) || this.cell1.equals(pair.cell2) && this.cell2.equals(pair.cell1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cell1, cell2);
    }

    @Override
    public String toString() {
        return cell1.getLine() + "-" + cell1.getColumn() + " / " + cell2.getLine() + "-" + cell2.getColumn() + ": " + value + "\n";
    }
}
