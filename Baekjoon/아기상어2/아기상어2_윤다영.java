import java.io.*;
import java.util.*;

public class Main {
    static class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int answer = Integer.MIN_VALUE;
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] map = new int[n][m];
        int[][] check = new int[n][m];
        Queue<Pair> q = new LinkedList<>();
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                check[i][j] = Integer.MAX_VALUE;
                if(map[i][j] == 1) {
                    q.add(new Pair(i, j));
                    check[i][j] = 0;
                }
            }
        }
        int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
        while(!q.isEmpty()) {
            Pair p = q.poll();
            for(int i = 0; i < 8; i++) {
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];
                if(nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
                if(check[nx][ny] > check[p.x][p.y] + 1) {
                    check[nx][ny] = check[p.x][p.y] + 1;
                    q.add(new Pair(nx, ny));
                    answer = Math.max(answer, check[nx][ny]);
                }
            }
        }
        System.out.println(answer);
    }

}