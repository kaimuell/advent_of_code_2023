package utils;

import java.awt.*;

public enum Direction {
        NORTH(0, -1), SOUTH(0, 1), WEST(-1, 0), EAST(1, 0);

        private final int x;
        private final int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Direction getOppositeDirection() {
            switch (this) {
                case NORTH -> {
                    return SOUTH;
                }
                case SOUTH -> {
                    return NORTH;
                }
                case EAST -> {
                    return WEST;
                }
                case WEST -> {
                    return EAST;
                }
                default -> throw new IllegalStateException("Unexpected value: " + this);
            }
        }

        public Point addPoint(Point p) {
            return new Point(p.x + this.x, p.y + this.y);
        }

}
