package BackJoon.골드.G5.BJ_9205_맥주_마시면서_걸어가기;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static final int MAX_DIST = 1_000;

    static int T, N;
    static Point p_sangeun, p_festival;
    static Point[] groceryList;
    static boolean[] v_grocery;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            N = Integer.parseInt(br.readLine());

            groceryList = new Point[N];
            v_grocery = new boolean[N];

            // 상근이네 집
            st = new StringTokenizer(br.readLine());
            p_sangeun = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

            // 편의점
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                Point p_grocery = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                groceryList[i] = p_grocery;
            }

            // 락 페스티벌 좌표
            st = new StringTokenizer(br.readLine());
            p_festival = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

            String ans = solve() ? "happy" : "sad";
            System.out.println(ans);
        }

    }

    static boolean solve() {
        Queue<Point> q = new LinkedList<>();
        boolean isArrived = false;
        q.add(p_sangeun);

        while (!q.isEmpty()) {
            Point cur = q.poll();

            // 현재 위치에서 페스티벌 간 거리가 1_000 이하이면 가능
            if (isInMaxDist(cur, p_festival)) {
                isArrived = true;
                break;
            }

            for (int i = 0; i < N; i++) {
                Point p_grocery = groceryList[i];

                if (v_grocery[i]) continue;
                if (isInMaxDist(cur, p_grocery)) {
                    q.add(p_grocery);
                    v_grocery[i] = true;
                }
            }
        }

        return isArrived;
    }

    static boolean canMoveGrocery() {
        for (int i = 0; i < N; i++) {
            Point p_grocery = groceryList[i];
            if (!v_grocery[i] && isInMaxDist(p_sangeun, p_grocery)) {

                return true;
            }
        }

        return false;
    }

    static boolean isInMaxDist(Point p1, Point p2) {
        int dist = Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
        return dist <= MAX_DIST;
    }

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
