package maze;

import java.util.*;

public class Main {
    private static int[][] maze;
    private static int i = 1;
    private static int j = 1;
    private static final Random random = new Random();
    private static final Deque<Direction> path = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        maze = new int[n][m];
        buildPath(m, n);
    }

    private static void buildPath(int width, int height) {
        List<Direction> directions;
        Deque<List<Integer>> visitedCells = new LinkedList<>();

        while (true) {
            directions = new ArrayList<>(Arrays.asList(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT));
            maze[i][j] = -1;
            printMaze();
            System.out.println("---------------------------------------------");
            if (i <= 2 || maze[i - 2][j] == -1) {
                directions.remove(Direction.UP);
            }
            if (j >= width - 3 || (j < width - 3 && maze[i][j + 2] == -1)) {
                directions.remove(Direction.RIGHT);
            }
            if (i >= height - 3 || (i < height - 3 && maze[i + 2][j] == -1)) {
                directions.remove(Direction.DOWN);
            }
            if (j <= 2 || maze[i][j - 2] == -1) {
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
        path.addLast(direction);
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
        i = 1;
        j = 1;
        while (!path.isEmpty()) {
            Direction direction = path.removeFirst();
            if (direction == Direction.RIGHT) {
                maze[i - 1][j] = 1;
                maze[i + 1][j] = 1;
                maze[i - 1][j - 1] = 1;
                maze[i + 1][j - 1] = 1;
                maze[][]
                j = j + 2;
            } else if (direction == Direction.LEFT) {
                maze[i - 1][j] = 1;
                maze[i + 1][j] = 1;
                maze[i + 1][j + 1] = 1;
                maze[i - 1][j + 1] = 1;
                j = j - 2;
            } else if (direction == Direction.DOWN) {
                maze[i][j - 1] = 1;
                maze[i][j + 1] = 1;
                maze[i - 1][j - 1] = 1;
                maze[i - 1][j + 1] = 1;
                i = i + 2;
            } else if (direction == Direction.UP) {
                maze[i][j - 1] = 1;
                maze[i][j + 1] = 1;
                maze[i + 1][j - 1] = 1;
                maze[i + 1][j + 1] = 1;
                i = i - 2;
            }
            printMaze();
            System.out.println("-------------------------");
        }

    }

    private static void printMaze() {
        for (int[] ints : maze) {
            for (int a : ints) {
                System.out.print(a + " ");
            }
            System.out.println();
        }
        ;
    }
}
