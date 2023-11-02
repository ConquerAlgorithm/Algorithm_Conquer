package Programmers.LV3;
import java.util.*;

public class 야근_지수 {
    /*
        - 야근 피로도 : 야근 시작 시점에서 남은일의 작업량을 제곱하여 더한 값
        - N 시간 동안 야근 피로도를 최소화하도록 일한다.
        - 1시간 동안 1만큼 처리할 수 있다고 할 때,
        - 남은 N 시간과 각 일에 대한 작업량 works에 대해 야근 피로도를 최소화한 값을 리턴.
    */
    class Solution {
        public long solution(int n, int[] works) {
            long answer = 0;

            PriorityQueue<Integer> pq = new PriorityQueue<>((n1, n2) -> {return n2 - n1;});

            for (int i = 0; i < works.length; i++) {
                pq.add(works[i]);
            }

            while (!pq.isEmpty() && n > 0) {
                int num = pq.poll();
                num--;

                if (num < 0) continue;

                pq.add(num);
                n--;
            }

            if (pq.isEmpty()) return answer;
            else {
                while (!pq.isEmpty()) {
                    answer += Math.pow(pq.poll(), 2);
                }
            }


            return answer;
        }
    }
}
