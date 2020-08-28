package maze;

import java.io.Serializable;

public class Maze implements Serializable {
    int[][] mazeArray;
    Point startPoint;
    Point endPoint;

    public Maze(int[][] maze, Point startPoint, Point endPoint) {
        this.mazeArray = maze;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public int[][] getMazeArray() {
        return mazeArray;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setMazeArray(int[][] mazeArray) {
        this.mazeArray = mazeArray;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }
}
