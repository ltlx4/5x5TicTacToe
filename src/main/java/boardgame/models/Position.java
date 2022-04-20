package boardgame.models;

public record Position(int row, int col) {

    public Position moveTo(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }

    public Position getOpposite() {
        return new Position(row * -1, col * -1);
    }

    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

}