package maze;

public class Main {
    public static void main(String[] args) {
        int[][] maze = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 1, 0, 0, 1, 0, 0, 0},
                        {1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
                        {1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
                        {1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
                        {1, 0, 0, 1, 1, 0, 1, 0, 0, 1},
                        {1, 0, 0, 0, 1, 0, 1, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 1, 0, 0, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                };
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (maze[i][j] == 1) {
                    System.out.print("\u2588\u2588");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}
