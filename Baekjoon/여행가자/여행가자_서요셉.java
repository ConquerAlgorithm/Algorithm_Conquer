package BackJoon.골드.G4.BJ_1976_여행가자;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 여행가자 {

    static int N, M;
    static int[] p;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        p = new int[N + 1];
        p_init();

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                int num = Integer.parseInt(st.nextToken());

                if (num == 1) union(i, j);
            }
        }

        st = new StringTokenizer(br.readLine());
        int start = findP(Integer.parseInt(st.nextToken()));
        boolean isPossible = true;
        for (int i = 1; i < M; i++) {
            int cur = Integer.parseInt(st.nextToken());

            if (start != findP(cur)) {
                isPossible = false;
                break;
            }
        }

        String ans = (isPossible) ? "YES" : "NO";
        System.out.println(ans);
    }

    static void p_init() {
        for (int i = 1; i <= N; i++) {
            p[i] = i;
        }
    }

    static void union(int x, int y) {
        int p1 = findP(x);
        int p2 = findP(y);

        if (p1 != p2) {
            if (p1 < p2) p[p2] = p1;
            else p[p1] = p2;
        }
    }

    static int findP(int x) {
        if (x == p[x]) return x;
        return p[x] = findP(p[x]);
    }
}