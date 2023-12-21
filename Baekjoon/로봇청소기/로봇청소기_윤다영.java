import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
    static int[][] map;
    static int disX[] = {-1,0,1,0};  // 북동남서
    static int disY[] = {0,1,0,-1};
    static int answer = 1;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int dis = Integer.parseInt(st.nextToken());

        map = new int[n][m];
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < m; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        dfs(x, y, dis);
        System.out.println(answer);
    }

    private static void dfs(int x, int y, int dis) {
        map[x][y] = 2; // 청소
        for(int i = 0; i < 4; i++) {
            dis = (dis + 3) % 4; // 왼쪽으로 회전
            int nx = x + disX[dis];
            int ny = y + disY[dis];
            if(outRange(nx, ny) || map[nx][ny] != 0) continue;
            answer++;
            dfs(nx, ny, dis);
            return;
        }

        int back = (dis + 2) % 4;

        int bx = x + disX[back];
        int by = y + disY[back];

        if(outRange(bx, by) || map[bx][by] == 1) return;
        dfs(bx, by, dis);
    }

    private static boolean outRange(int x, int y) {
        return x < 0 || x >= map.length || y < 0 || y >= map[0].length;
    }
}