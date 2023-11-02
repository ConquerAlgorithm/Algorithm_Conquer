package Programmers.LV3;

/*
    - 외벽의 총 둘레 n 미터, 취약점들이 존재
    - 내부 공사 도중에도 취약점들이 손상되지 않는지 점검을 진행
    - 빠른 공사 진행을 위해 점검 시간은 1시간.
    - 1시간 동안 이동할 수 있는 거리는 친구들이 제각각이다. 그래서 '최소한'의 친구들을 투입해 점검을 진행하고
    - 나머지는 내부 공사를 돕도록한다.
    - 정북 방향 지점 : 0. 취약점의 위치는 정북방향에서 부터 시계방향으로 떨어진 거리를 나타냄.
    - 친구들은 시계, 반시계 방향으로 외벽을 따라서만 이동한다.

    -> 환형큐 형태, 가능한 취약점의 경우를 모두 만든다.
    -> 순열로 고를 수 있는 dist의 경우의 수를 모두 만든다.
    -> dist가 다 골라지면 모두 커버가 가능해지는 dist의 개수를 '최소값'으로 갱신한다.
*/
public class 외벽점검 {

    int[] weak, dist;
    int[][] weakCases;
    int N, ans;

    public int solution(int n, int[] w, int[] d) {

        weak = w;
        dist = d;
        weakCases = new int[weak.length][weak.length];
        N = n;
        ans = dist.length + 1;

        mkWeakCases();
        mkDistCases(new boolean[dist.length], new int[dist.length], 0);

        return (ans == dist.length + 1) ? -1 : ans;
    }

    void mkWeakCases() {
        int[] weakCase = weak.clone();
        weakCases[0] = weakCase.clone();

        for (int i = 1; i < weak.length; i++) {
            int tmp = weakCase[0];

            for (int j = 1; j < weak.length; j++) {
                weakCase[j - 1] = weakCase[j];
            }

            weakCase[weak.length - 1] = tmp + N;
            weakCases[i] = weakCase.clone();
        }
    }

    void mkDistCases(boolean[] visited, int[] distCase, int idx) {
        if (idx == dist.length) {
            // 골라진 dist의 case 들을 모든 취약점에 대입하여 커버시켜본다.
            for (int[] weakCase : weakCases) {
                checkAns(distCase, weakCase);
            }
        }

        for (int i = 0; i < dist.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                distCase[idx] = dist[i];
                mkDistCases(visited, distCase, idx + 1);
                visited[i] = false;
                distCase[idx] = 0;
            }
        }
    }

    void checkAns(int[] distCase, int[] weakCase) {
        int cur = 0;
        int next;
        int distIdx = 0;

        // 범위 안에서
        while (cur < weakCase.length && distIdx < distCase.length) {
            next = cur + 1;

            // 현재 취약점 + 거리값이 다음보다 크거나 같으면 커버되는것. 다음으로 넘겨 확인
            while (next < weakCase.length && weakCase[cur] + distCase[distIdx] >= weakCase[next]) next++;

            // 인덱스 조절
            cur = next;
            distIdx++;
        }

        if (cur == weak.length && distIdx < ans) ans = distIdx;
    }

}