package Programmers.LV3.단속카메라;
import java.util.*;

public class 단속카메라 {
    class Solution {
        public int solution(int[][] routes) {
            int answer = 0;

            Arrays.sort(routes, (int[] o1, int[] o2) -> (o1[1] - o2[1]));

            int cam = -30_001;

            for (int[] route : routes) {
                if (cam < route[0]) {
                    cam = route[1];
                    answer++;
                }
            }

            return answer;
        }
    }
}
