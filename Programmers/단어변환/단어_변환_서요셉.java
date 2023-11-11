package Programmers.LV3;

public class 단어_변환 {
    class Solution {

        static boolean[] visited;
        static int ans = 0;

        public int solution(String begin, String target, String[] words) {
            visited = new boolean[words.length];

            DFS(begin, target, words, 0);

            return ans;
        }

        static void DFS(String begin, String target, String[] words, int cnt) {
            if (begin.equals(target)) {
                ans = cnt;
                return;
            }

            for (int i = 0; i < words.length; i++) {
                if (visited[i]) continue;

                int sameCnt = 0;
                for (int j = 0; j < begin.length(); j++) {
                    if (begin.charAt(j) == words[i].charAt(j)) sameCnt++;
                }

                if (sameCnt == begin.length() - 1) {
                    visited[i] = true;
                    DFS(words[i], target, words, cnt + 1);
                    visited[i] = false;
                }
            }
        }
    }
}
