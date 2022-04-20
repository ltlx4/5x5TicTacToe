package boardgame.models;


public enum PawnDirection implements Direction {

    UP_LEFT(-1, -1),
    UP_RIGHT(-1, 1),
    DOWN_RIGHT(1, 1),
    DOWN_LEFT(1, -1);

    private final int rowChange;
    private final int colChange;

    PawnDirection(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    public int getRowChange() {
        return rowChange;
    }

    public int getColChange() {
        return colChange;
    }

    public static PawnDirection of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        System.out.println(of(1, -1));
    }

}
