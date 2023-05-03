public class Position {
    
    private int row;
    private int col;
    private int count;
    
    public Position(int count, int row, int col) {
        this.row = row;
        this.col = col;
        this.count = count;
    }
    
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
