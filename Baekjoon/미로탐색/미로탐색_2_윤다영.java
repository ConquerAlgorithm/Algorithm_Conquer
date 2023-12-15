import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] board = new int[n][m];
        for(int i = 0; i < n; i++) {
            char[] str = br.readLine().toCharArray();
            for(int j = 0; j < m; j++)
                board[i][j] = str[j] - '0';
        }
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0,0});
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        int[][] visited = new int[n][m];
        visited[0][0] = 1;
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            for(int i = 0; i < 4; i++) {
                int nx = cur[0] + dir[i][0];
                int ny = cur[1] + dir[i][1];
                if(nx < 0 || nx >= n || ny < 0 || ny >= m || visited[nx][ny] != 0 || board[nx][ny] == 0) continue;
                visited[nx][ny] = visited[cur[0]][cur[1]] + 1;
                q.add(new int[]{nx,ny});
            }
        }
        System.out.println(visited[n-1][m-1]);
    }
}