package BackJoon.골드.G2.BJ_1525_퍼즐;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class 퍼즐 {

    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, -1, 0, 1};

    static final int N = 3;
    static final String ANS = "123456780";
    static Map<String, Integer> hm;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        hm = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                sb.append(Integer.parseInt(st.nextToken()));
            }
        }

        String init = sb.toString();
        hm.put(init, 0);
        System.out.println(BFS(init));
    }

    static int BFS(String init) {
        Queue<String> q = new LinkedList<>();

        q.add(init);

        while (!q.isEmpty()) {
            String curS = q.poll();

            int mCnt = hm.get(curS);
            int empty = curS.indexOf('0');

            int cx = empty / N;
            int cy = empty % N;

            if (curS.equals(ANS)) return mCnt;

            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                if (isNotBoundary(nx, ny)) continue;

                int nextIdx = nx * 3 + ny;
                char c = curS.charAt(nextIdx);

                String nextS = curS.replace(c, 'c');
                nextS = nextS.replace('0', c);
                nextS = nextS.replace('c', '0');

                if (!hm.containsKey(nextS)) {
                    q.add(nextS);
                    hm.put(nextS, mCnt + 1);
                }
            }
        }

        return -1;
    }

    static boolean isNotBoundary(int x, int y) {
        return !(0 <= x && x < N && 0 <= y && y < N);
    }
}
