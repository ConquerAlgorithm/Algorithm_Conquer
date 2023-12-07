package BackJoon.실버.S1.BJ_5014_스타트링크;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class 스타트링크 {

    static int F, S, G, U, D;
    static int[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        F = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        G = Integer.parseInt(st.nextToken());
        U = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        visited = new int[F + 1];

        BFS(F, S, G, U, D);
    }

    static void BFS(int f, int s, int g, int u, int d) {
        Queue<Integer> q = new LinkedList<>();

        q.add(s);
        visited[s] = 1;

        while (!q.isEmpty()) {
            int cur = q.poll();

            if (cur == g) {
                System.out.println(visited[cur] - 1);
                break;
            }

            if (cur + u <= f && visited[cur + u] == 0) {
                q.add(cur + u);
                visited[cur + u] = visited[cur] + 1;
            }

            if (cur - d > 0 && visited[cur - d] == 0) {
                q.add(cur - d);
                visited[cur - d] = visited[cur] + 1;
            }
        }

        if (visited[g] == 0) System.out.println("use the stairs");
    }
}
