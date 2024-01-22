package Programmers.LV3.보석쇼핑;
import java.util.*;

public class 보석쇼핑 {

    class Solution {
        public int[] solution(String[] gems) {
            int[] answer = new int[2];

            int kind = new HashSet<>(Arrays.asList(gems)).size();

            int len = Integer.MAX_VALUE;
            int start = 0;

            Map<String, Integer> gemHM = new HashMap<>();

            for (int end = 0; end < gems.length; end++) {
                gemHM.put(gems[end], gemHM.getOrDefault(gems[end], 0) + 1);

                while (gemHM.get(gems[start]) > 1) {
                    gemHM.put(gems[start], gemHM.get(gems[start]) - 1);
                    start++;
                }

                if (gemHM.size() == kind && len > (end - start)) {
                    len = end - start;
                    answer[0] = start + 1;
                    answer[1] = end + 1;
                }
            }

            return answer;
        }
    }
}
