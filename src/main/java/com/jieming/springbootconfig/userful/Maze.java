package com.jieming.springbootconfig.userful;


/*×
 * 华为机试 --第43题
 * 迷宫问题
 *
 * */

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

public class Maze {

    public static void main(String[] args) {
        System.out.print("请输入二维数组行数：");
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int row = sc.nextInt();
            System.out.println();
            System.out.print("请输入二维数组列数：");
            int col = sc.nextInt();
            System.out.println();
            int grid[][] = new int[row][col];
            System.out.println("以下输入二位数组的值：");
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < row; j++) {
                    grid[i][j] = sc.nextInt();
                }
            }

            Queue<Point> queue = new LinkedList<>();
            queue.offer(new Point(0, 0, null));
            grid[0][0] = 1;
            Point pos = null;
            while (true) {
                pos = queue.poll();
                if (Objects.isNull(pos)) {
                    System.out.println("是死胡同");
                    break;
                }
                int px = pos.px;
                int py = pos.py;
//              先判断该节点是否时终点
                if (px == row - 1 && py == col - 1) {
                    break;
                }

//              通过该节点向四个方向寻找下个节点
//              向下
                if (px + 1 < row && grid[px + 1][py] == 0) {
                    grid[px + 1][py] = 1;
                    queue.offer(new Point(px + 1, py, pos));
                }
//                向右
                if (py + 1 < col && grid[px][py + 1] == 0) {
                    grid[px][py + 1] = 1;
                    queue.offer(new Point(px, py + 1, pos));
                }
//                向上
                if (px - 1 >= 0 && grid[px - 1][py] == 0) {
                    grid[px - 1][py] = 1;
                    queue.offer(new Point(px - 1, py, pos));
                }
                if (py - 1 >= 0 && grid[px][py - 1] == 0) {
                    grid[px][py - 1] = 1;
                    queue.offer(new Point(px, py - 1, pos));
                }
            }
            print(pos);


        }

    }

    public static void print(Point point) {
        if (!Objects.isNull(point)) {
            print(point.parent);
            System.out.println("(" + point.px + "," + point.py + ")");
        }
    }
}

class Point {
    int px;
    int py;

    Point parent;

    Point() {

    }

    Point(int px, int py, Point parent) {
        this.px = px;
        this.py = py;
        this.parent = parent;
    }
}

