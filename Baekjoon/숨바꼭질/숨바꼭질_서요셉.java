package BackJoon.실버.S1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_1697_숨바꼭질 {

    static int N, K, ans;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        if (N == K) ans = 0;
        else bfs(N);

        System.out.println(ans);
    }

    static void bfs(int num) {
        Queue<Integer> q = new LinkedList<>();
        int[] visited = new int[100_001];

        q.add(num);
        visited[num] = 1;

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int i = 0; i < 3; i++) {
                int next;

                if (i == 0) next = cur + 1;
                else if (i == 1) next = cur - 1;
                else next = cur * 2;

                if (next == K) {
                    ans = visited[cur];
                    return;
                }

                if (0 <= next && next < visited.length && visited[next] == 0) {
                    q.add(next);
                    visited[next] = visited[cur] + 1;
                }
            }

        }
    }

}
