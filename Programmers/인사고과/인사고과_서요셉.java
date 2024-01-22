package Programmers.LV3.인사고과;
import java.util.*;

public class 인사고과 {

    class Solution {
        public int solution(int[][] scores) {
            int answer = 0;

            int len = scores.length;
            int w1 = scores[0][0];
            int w2 = scores[0][1];

            Arrays.sort(scores, (s1, s2) -> {
                if (s1[0] == s2[0]) return s1[1] - s2[1];
                return s2[0] - s1[0];
            });

            int max = scores[0][1];

            for (int i = 1; i < len; i++) {
                if (scores[i][1] < max) {
                    if (scores[i][0] == w1 && scores[i][1] == w2) return -1;

                    scores[i][0] = -1;
                    scores[i][1] = -1;
                } else {
                    max = scores[i][1];
                }
            }

            Arrays.sort(scores, (s1, s2) -> (s2[0] + s2[1]) - (s1[0] + s1[1]));
            answer = 1;

            for (int i = 0; i < len; i++) {
                if (scores[i][0] + scores[i][1] > w1 + w2) answer++;
                else break;
            }

            return answer;
        }
    }
}
