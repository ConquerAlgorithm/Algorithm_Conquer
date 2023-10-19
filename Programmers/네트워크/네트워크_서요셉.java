package Programmers.LV3;

public class 네트워크 {
    class Solution {

        static int[] parents;

        public int solution(int n, int[][] computers) {
            int answer = 0;

            parents = new int[n];

            init(n);

            // 연결되어 있으면 작은쪽으로 부모를 바꿔 놓고
            // 부모 배열에서 달라지면 네트워크 개수를 늘리는 식으로 cnt한다.
            for (int i = 0; i < computers.length; i++) {
                for (int j = 0; j < computers[i].length; j++) {
                    if (i == j) continue;

                    if (computers[i][j] == 1) {
                        union(i, j);
                    }
                }
            }

            int parentsCnt = 0;
            boolean[] linked = new boolean[201];

            for (int i = 0; i < n; i++) {
                if (!linked[findParents(parents[i])]) {
                    linked[findParents(parents[i])] = true;
                    parentsCnt++;
                }

            }

            return parentsCnt;
        }

        static void init(int n) {
            for (int i = 0; i < n; i++) {
                parents[i] = i;
            }
        }

        static int findParents(int c1) {
            if (parents[c1] == c1) return parents[c1];
            else return parents[c1] = findParents(parents[c1]);
        }

        static void union(int c1, int c2) {
            int p1 = findParents(c1);
            int p2 = findParents(c2);

            if (p1 < p2) parents[p2] = p1;
            else parents[p1] = p2;
        }
    }
}
