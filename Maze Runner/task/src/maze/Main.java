package maze;

import java.util.*;

public class Main {
    private static int[][] maze;
    private static int i = 1;
    private static int j = 1;
    private static final Random random = new Random();
    private static final Deque<List<Integer>> ends = new LinkedList<>();

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
        boolean isEndAchieved = false;
        Deque<List<Integer>> visitedCells = new LinkedList<>();
        while (true) {
            directions = new ArrayList<>(Arrays.asList(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT));
            maze[i][j] = 1;
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
                isEndAchieved = true;
            } else if (!visitedCells.isEmpty()) {
                List<Integer> coordinates = visitedCells.removeLast();
                i = coordinates.get(0);
                j = coordinates.get(1);
                if (isEndAchieved) {
                    ends.addLast(Arrays.asList(i, j));
                }
                isEndAchieved = false;
            } else {
                break;
            }
        }
    }

    private static void pickRandomDirection(List<Direction> directions) {
        Direction direction = directions.get(random.nextInt(directions.size()));
        switch (direction) {
            case UP:
                maze[i][j - 1] = maze[i][j - 1] == 0 ? 0 : 1;
                maze[i][j + 1] = maze[i][j + 1] == 0 ? 0 : 1;
                maze[i + 1][j - 1] = maze[i + 1][j - 1] == 0 ? 0 : 1;
                maze[i + 1][j + 1] = maze[i + 1][j + 1] == 0 ? 0 : 1;
                maze[i - 1][j] = 1;
                i = i - 2;
                break;
            case DOWN:
                maze[i][j - 1] = maze[i][j - 1] == 0 ? 0 : 1;
                maze[i][j + 1] = maze[i][j + 1] == 0 ? 0 : 1;
                maze[i - 1][j - 1] = maze[i - 1][j - 1] == 0 ? 0 : 1;
                maze[i - 1][j + 1] = maze[i - 1][j + 1] == 0 ? 0 : 1;
                maze[i + 1][j] = 1;
                i = i + 2;
                break;
            case LEFT:
                maze[i - 1][j] = maze[i - 1][j] == 0 ? 0 : 1;
                maze[i + 1][j] = maze[i + 1][j] == 0 ? 0 : 1;
                maze[i + 1][j + 1] = maze[i + 1][j + 1] == 0 ? 0 : 1;
                maze[i - 1][j + 1] = maze[i - 1][j + 1] == 0 ? 0 : 1;
                maze[i][j - 1] = 1;
                j = j - 2;
                break;
            case RIGHT:
                maze[i - 1][j] = maze[i - 1][j] == 0 ? 0 : 1;
                maze[i + 1][j] = maze[i + 1][j] == 0 ? 0 : 1;
                maze[i - 1][j - 1] = maze[i - 1][j - 1] == 0 ? 0 : 1;
                maze[i + 1][j - 1] = maze[i + 1][j - 1] == 0 ? 0 : 1;
                maze[i][j + 1] = 1;
                j = j + 2;
                break;
            default:
                break;
        }

    }

    private static void buildWayIn() {
        maze[1][0] = 1;
    }

    private static void buildWayOutCorner(int n, int m) {
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

    private static void buildWayOut(int height, int width) {
        boolean isBuilt = false;
        List<Integer> coordinates = null;
        while (!ends.isEmpty()) {
            coordinates = ends.removeLast();
            i = coordinates.get(0);
            j = coordinates.get(1);
            if (i - 1 == 0) {
                maze[i - 1][j] = 1;
                ends.clear();
                isBuilt = true;
            } else if (i + 1 == height - 1) {
                maze[i + 1][j] = 1;
                ends.clear();
                isBuilt = true;
            } else if (i + 2 == height - 1) {
                maze[i + 1][j] = 1;
                maze[i + 2][j] = 1;
                ends.clear();
                isBuilt = true;
            } else if (j - 1 == 0) {
                maze[i][j - 1] = 1;
                ends.clear();
                isBuilt = true;
            } else if (j + 1 == width - 1) {
                maze[i][j + 1] = 1;
                ends.clear();
                isBuilt = true;
            } else if (j + 2 == width - 1) {
                maze[i][j + 1] = 1;
                maze[i][j + 2] = 1;
                ends.clear();
                isBuilt = true;
            }
        }
        if (!isBuilt) {
            buildWayOutCorner(height, width);
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
        System.out.println(ends.size());
    }
}