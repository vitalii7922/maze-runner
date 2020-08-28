package maze;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    private static int[][] maze;
    private static final Scanner scanner = new Scanner(System.in);
    private static int i = 1;
    private static int j = 1;
    private static final Random random = new Random();
    private static final Deque<List<Integer>> ends = new LinkedList<>();
    private static final Deque<Point> escape = new LinkedList<>();
    private static boolean isLoaded = false;
    private static boolean isGenerated = false;
    private static boolean isWorking = true;
    private static Point startPoint;
    private static Point endPoint;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        while (isWorking) {
            if (isGenerated || isLoaded) {
                printLongMenu();
            } else {
                printShortMenu();
            }
        }
    }


    private static void printLongMenu() throws IOException, ClassNotFoundException {
        System.out.println("=== Menu ===");
        System.out.println("1. Generate a new maze.");
        System.out.println("2. Load a maze.");
        System.out.println("3. Save the maze.");
        System.out.println("4. Display the maze.");
        System.out.println("5. Find the escape.");
        System.out.println("0. Exit.");
        pickOptionLongMenu(scanner.nextInt());
    }

    private static void printShortMenu() throws IOException, ClassNotFoundException {
        System.out.println("=== Menu ===");
        System.out.println("1. Generate a new maze.");
        System.out.println("2. Load a maze.");
        System.out.println("0. Exit.");
        pickOptionShortMenu(scanner.nextInt());
    }

    private static void saveMaze() throws IOException {
        for (int k = 0; k < maze.length; k++) {
            for (int l = 0; l < maze[k].length; l++) {
                if (maze[k][l] == -1 || maze[k][l] == 2) {
                    maze[k][l] = 1;
                }
            }
        }

        SerializationUtils.serialize(new Maze(maze, startPoint, endPoint), scanner.next());
    }

    private static void loadMaze() throws IOException, ClassNotFoundException {

        Maze mazeArray = (Maze) SerializationUtils.deserialize(scanner.next());
        if (mazeArray != null) {
            isLoaded = true;
            maze = mazeArray.getMazeArray();
            startPoint = mazeArray.getStartPoint();
            endPoint = mazeArray.getEndPoint();
        }
        escape.clear();
    }

    private static void pickOptionLongMenu(int option) throws IOException, ClassNotFoundException {
        switch (option) {
            case 0:
                System.out.println("Bye!");
                isWorking = false;
                break;
            case 1:
                generateNewMaze();
                break;
            case 2:
                loadMaze();
                break;
            case 3:
                saveMaze();
                break;
            case 4:
                printMaze();
                break;
            case 5:
                findEscape(maze[0].length);
            default:
                System.out.println("Incorrect option. Please try again");
                break;
        }
    }

    private static void pickOptionShortMenu(int option) throws IOException, ClassNotFoundException {
        switch (option) {
            case 0:
                System.out.println("Bye!");
                isWorking = false;
                break;
            case 1:
                generateNewMaze();
                break;
            case 2:
                loadMaze();
                break;
            case 3:
                saveMaze();
                break;
            case 4:
                printMaze();
                break;
            case 5:
                findEscape(maze[0].length);
                break;
            default:
                System.out.println("Incorrect option. Please try again");
                break;
        }
    }


    private static void generateNewMaze() {
        escape.clear();
        System.out.println("Enter the size of a new maze");
        int n = scanner.nextInt();
        maze = new int[n][n];
        i = 1;
        j = 1;
        buildPath(n, n);
        buildWayIn();
        buildWayOut(n, n);
        printMaze();
        isGenerated = true;
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
        startPoint = new Point(1, 0);
    }

    private static void buildWayOutCorner(int n, int m) {
        for (int k = 3; k < m; k++) {
            if (maze[n - 3][m - k] == 1) {
                maze[n - 2][m - k] = 1;
                maze[n - 1][m - k] = 1;
                endPoint = new Point(n - 1, m - k);
                break;
            } else if (maze[n - 2][m - k] == 1) {
                maze[n - 1][m - k] = 1;
                endPoint = new Point(n - 1, m - k);
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
                endPoint = new Point(i - 1, j);
            } else if (i + 1 == height - 1) {
                maze[i + 1][j] = 1;
                ends.clear();
                isBuilt = true;
                endPoint = new Point(i + 1, j);
            } else if (i + 2 == height - 1) {
                maze[i + 1][j] = 1;
                maze[i + 2][j] = 1;
                ends.clear();
                isBuilt = true;
                endPoint = new Point(i + 2, j);
            } else if (j - 1 == 0) {
                maze[i][j - 1] = 1;
                ends.clear();
                isBuilt = true;
                endPoint = new Point(i, j - 1);
            } else if (j + 1 == width - 1) {
                maze[i][j + 1] = 1;
                ends.clear();
                isBuilt = true;
                endPoint = new Point(i, j + 1);
            } else if (j + 2 == width - 1) {
                maze[i][j + 1] = 1;
                maze[i][j + 2] = 1;
                endPoint = new Point(i, j + 2);
                ends.clear();
                isBuilt = true;
            }
        }
        if (!isBuilt) {
            buildWayOutCorner(height, width);
        }
    }

    private static void findEscape(int n) {
        maze[startPoint.getRow()][startPoint.getCol()] = 2;
        escape.addLast(startPoint);
        i = startPoint.getRow();
        j = startPoint.getCol();
        while (isEndPointAchieved()) {
            if (i - 1 >= 0 && maze[i - 1][j] == 1) {
                escape.addLast(new Point(i - 1, j));
                maze[--i][j] = 2;
            } else if (j - 1 >= 0 && maze[i][j - 1] == 1) {
                escape.addLast(new Point(i, j - 1));
                maze[i][--j] = 2;
            } else if (j + 1 < n && maze[i][j + 1] == 1) {
                escape.addLast(new Point(i, j + 1));
                maze[i][++j] = 2;
            } else if (i + 1 < n && maze[i + 1][j] == 1) {
                escape.addLast(new Point(i + 1, j));
                maze[++i][j] = 2;
            } else if (!escape.isEmpty()){
                escape.removeLast();
                Point point = escape.peekLast();
                assert point != null;
                i = point.getRow();
                j = point.getCol();
            }
        }
        printMaze();
    }

    private static boolean isEndPointAchieved() {
        return (i != endPoint.getRow() && j != endPoint.getCol())
                || (i != endPoint.getRow() || j != endPoint.getCol());
    }


    private static  void printMazeTest() {
        for (int[] a : maze) {
            for (int b : a) {
                System.out.print(b);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    private static void printMaze() {
        while (!escape.isEmpty()) {
            Point point = escape.removeLast();
            maze[point.getRow()][point.getCol()] = -1;
        }
        for (int[] ints : maze) {
            for (int a : ints) {
                if (a == 1 || a == 2) {
                    System.out.print("  ");
                } else if (a == -1) {
                    System.out.print("//");
                } else {
                    System.out.print("\u2588\u2588");
                }
            }
            System.out.println();
        }
    }
}