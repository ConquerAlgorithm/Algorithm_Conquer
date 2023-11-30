import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];
        int[][] dist = new int[n][m];
        boolean[][] visited = new boolean[n][m];
        for(int i = 0; i < n; i++) {
            String str = br.readLine();
            for(int j = 0; j < m; j++) {
                map[i][j] = str.charAt(j) - '0';
            }
        }

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0,0});
        visited[0][0] = true;

        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        while (!q.isEmpty()) {
            int[] temp = q.poll();
            for(int i = 0; i < 4; i++) {
                int nx = temp[0] + dx[i];
                int ny = temp[1] + dy[i];
                if(nx < 0 || ny < 0 || nx >= n || ny >= m || map[nx][ny] == 0 || visited[nx][ny]) continue;
                visited[nx][ny] = true;
                dist[nx][ny] = dist[temp[0]][temp[1]] + 1;
                q.add(new int[]{nx, ny});
            }
        }

        System.out.println(dist[n - 1][m - 1] + 1);
    }
}