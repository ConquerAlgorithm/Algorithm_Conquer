import java.util.*;

class Solution {
    public long solution(int n, int[] works) {
        long answer = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for(int num : works) {
            pq.add(num);
        }

        int cnt = 0;
        while(n-- > 0) {
            int num = pq.poll() - 1;
            if(num == 0) cnt++;
            if(cnt == works.length) return 0;
            pq.add(num);
        }

        for(int num : pq) {
            answer += Math.pow(num, 2);
        }

        return answer;
    }
}