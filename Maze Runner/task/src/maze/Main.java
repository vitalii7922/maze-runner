package maze;

import java.util.*;

public class Main {
    private static int[][] maze;
    private static int i = 1;
    private static int j = 1;
    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        maze = new int[n][m];
        buildPath(n, m);
    }

    private static void buildPath(int width, int height) {
        int steps = 0;
        List<Direction> directions = new ArrayList<>();
        Deque<List<Integer>> visitedCells = new LinkedList<>();
        while (steps < width * height) {
            visitedCells.addLast(Arrays.asList(i, j));
            steps++;
            maze[i][j] = -1;
            if ((i - 2 > 1 && maze[i - 2][j] != -1) && (i + 2 < height - 1 && maze[i + 2][j] != -1)
                    && (j - 2 > 1 && maze[i][j - 2] != -1) && (j + 2 < width - 1 && maze[i][j + 2] != -1)) { //pick any direction
                directions.clear();
                directions.addAll(Arrays.asList(Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.UP));
                pickRandomDirection(directions);
            } else if (i - 2 <= 1 && (i + 2 < height - 1 && maze[i + 2][j] != -1)
                    && (j - 2 > 1 && maze[i][j - 2] != -1) && (j + 2 < width - 1 && maze[i][j + 2] != - 1)) { //except of moving to up
                directions.clear();
                directions.addAll(Arrays.asList(Direction.DOWN, Direction.LEFT, Direction.RIGHT));
                pickRandomDirection(directions);
            }
        }
    }

    private static void pickRandomDirection(List<Direction> directions) {
        Direction direction = directions.get(random.nextInt(directions.size() + 1) * directions.size());
        switch (direction) {
            case UP:
                i = i - 2;
                break;
            case DOWN:
                i = i + 2;
                break;
            case LEFT:
                j = j - 2;
                break;
            case RIGHT:
                j = j + 2;
                break;
            default:
                break;
        }
    }
}
