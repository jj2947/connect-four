package src;

// Class for the position type
public class Position {
    
    private int row;
    private int col;
    private int count;
    
    // Constructor
    public Position(int count, int row, int col) {
        this.row = row;
        this.col = col;
        this.count = count;
    }
    
    // Getters
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }

    public int getCount() {
        return count;
    }
}
