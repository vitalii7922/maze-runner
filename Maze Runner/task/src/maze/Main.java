package maze;

import java.util.*;

public class Main {
    private static int[][] maze;
    private static int i = 1;
    private static int j = 1;
    private static final Random random = new Random();
    private static final Deque<List<Integer>> pathQueue = new LinkedList<>();
    private static final List<List<Integer>> pathList = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        maze = new int[n][m];
        buildPath(m, n);
        buildWayIn();
        buildWayOut(n, m);
        printMaze();
    }

    private static void buildPath(int width, int height) {
        List<Direction> directions;
        Deque<List<Integer>> visitedCells = new LinkedList<>();
        while (true) {
            directions = new ArrayList<>(Arrays.asList(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT));
            maze[i][j] = 1;
            pathQueue.add(Arrays.asList(i, j));
            pathList.add(Arrays.asList(i, j));
            if (i <= 2 || maze[i - 2][j] == 1) {
                directions.remove(Direction.UP);
            }
            if (j >= width - 3 || (j < width - 3 && maze[i][j + 2] == 1)) {
                directions.remove(Direction.RIGHT);
            }
            if (i >= height - 3 || (i < height - 3 && maze[i + 2][j] == 1)) {
                directions.remove(Direction.DOWN);
            }
            if (j <= 2 || maze[i][j - 2] == 1) {
                directions.remove(Direction.LEFT);
            }
            if (!directions.isEmpty()) {
                pickRandomDirection(directions);
                visitedCells.addLast(Arrays.asList(i, j));
            } else if (!visitedCells.isEmpty()) {
                List<Integer> coordinates = visitedCells.removeLast();
                i = coordinates.get(0);
                j = coordinates.get(1);
            } else {
                break;
            }
        }
        drawMaze();
    }

    private static void pickRandomDirection(List<Direction> directions) {
        Direction direction = directions.get(random.nextInt(directions.size()));
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

    private static void drawMaze() {
        List<Integer> coordinates = pathQueue.removeFirst();
        i = coordinates.get(0);
        j = coordinates.get(1);
        while (!pathQueue.isEmpty()) {
            coordinates = pathQueue.removeFirst();
            Direction direction = null;
            if (coordinates.get(0) > i && coordinates.get(1) == j) {
                direction = Direction.DOWN;
            } else if (coordinates.get(0) < i && coordinates.get(1) == j) {
                direction = Direction.UP;
            } else if (coordinates.get(0) == i && coordinates.get(1) > j) {
                direction = Direction.RIGHT;
            } else if (coordinates.get(0) == i && coordinates.get(1) < j) {
                direction = Direction.LEFT;
            }
            if (direction == Direction.RIGHT) {
                maze[i - 1][j] = maze[i - 1][j] == 0 ? 0 : 1;
                maze[i + 1][j] = maze[i + 1][j] == 0 ? 0 : 1;
                maze[i - 1][j - 1] = maze[i - 1][j - 1] == 0 ? 0 : 1;
                maze[i + 1][j - 1] = maze[i + 1][j - 1] == 0 ? 0 : 1;
                maze[i][j + 1] = 1;
            } else if (direction == Direction.LEFT) {
                maze[i - 1][j] = maze[i - 1][j] == 0 ? 0 : 1;
                maze[i + 1][j] = maze[i + 1][j] == 0 ? 0 : 1;
                maze[i + 1][j + 1] = maze[i + 1][j + 1] == 0 ? 0 : 1;
                maze[i - 1][j + 1] = maze[i - 1][j + 1] == 0 ? 0 : 1;
                maze[i][j - 1] = 1;
            } else if (direction == Direction.DOWN) {
                maze[i][j - 1] = maze[i][j - 1] == 0 ? 0 : 1;
                maze[i][j + 1] = maze[i][j + 1] == 0 ? 0 : 1;
                maze[i - 1][j - 1] = maze[i - 1][j - 1] == 0 ? 0 : 1;
                maze[i - 1][j + 1] = maze[i - 1][j + 1] == 0 ? 0 : 1;
                maze[i + 1][j] = 1;
            } else if (direction == Direction.UP) {
                maze[i][j - 1] = maze[i][j - 1] == 0 ? 0 : 1;
                maze[i][j + 1] = maze[i][j + 1] == 0 ? 0 : 1;
                maze[i + 1][j - 1] = maze[i + 1][j - 1] == 0 ? 0 : 1;
                maze[i + 1][j + 1] = maze[i + 1][j + 1] == 0 ? 0 : 1;
                maze[i - 1][j] = 1;
            }
            i = coordinates.get(0);
            j = coordinates.get(1);
        }

    }

    private static void buildWayIn() {
        maze[1][0] = 1;
    }

    private static void buildWayOut(int n, int m) {
        for (int k = 3; k < m; k++) {
            if (maze[n - 3][m - k] == 1) {
                maze[n - 2][m - k] = 1;
                maze[n - 1][m - k] = 1;
                break;
            } else if (maze[n - 2][m - k] == 1) {
                maze[n - 1][m - k] = 1;
                break;
            }
        }
    }

    private static void printMaze() {
        for (int[] ints : maze) {
            for (int a : ints) {
                if (a == 1) {
                    System.out.print("  ");
                } else {
                    System.out.print("\u2588\u2588");
                }
            }
            System.out.println();
        }
    }
}
