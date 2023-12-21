import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken()); // 가로
        int N = Integer.parseInt(st.nextToken()); // 세로
        int H = Integer.parseInt(st.nextToken()); // 높이

        Queue<int[]> q = new LinkedList<>();

        int[][][] map = new int[H][N][M];
        for(int i = 0; i < H; i++) {
            for(int j = 0; j < N; j++) {
                st = new StringTokenizer(br.readLine());
                for(int k = 0; k < M; k++) {
                    map[i][j][k] = Integer.parseInt(st.nextToken());
                    if(map[i][j][k] == 1) q.add(new int[]{i, j, k});
                }
            }
        }

        int[] dx = {0, 1, 0, -1, 0, 0};
        int[] dy = {1, 0, -1, 0, 0, 0};
        int[] dz = {0, 0, 0, 0, 1, -1};
        int answer = Integer.MIN_VALUE;
        while(!q.isEmpty()) {
            int[] now = q.poll();
            for(int i = 0; i < 6; i++) {
                int nz = now[0] + dx[i];
                int nx = now[1] + dy[i];
                int ny = now[2] + dz[i];
                if(nz < 0 || nx < 0 || ny < 0 || nz >= H || nx >= N || ny >= M) continue;
                if(map[nz][nx][ny] == 0) {
                    map[nz][nx][ny] = map[now[0]][now[1]][now[2]] + 1;
                    q.add(new int[]{nz, nx, ny});
                }
            }
        }

        for(int i = 0; i < H; i++) {
            for(int j = 0; j < N; j++) {
                for(int k = 0; k < M; k++) {
                    if(map[i][j][k] == 0) {
                        System.out.println(-1);
                        return;
                    }
                    answer = Math.max(answer, map[i][j][k]);
                }
            }
        }
        if(answer == 1)
            System.out.println(0);
        else
            System.out.println(answer - 1);
    }

}