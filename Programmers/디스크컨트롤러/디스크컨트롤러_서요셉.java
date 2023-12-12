package Programmers.LV3.디스크컨트롤러;

import java.util.*;
public class 디스크컨트롤러 {

    class Solution {

        public int solution(int[][] jobs) {
            int answer = 0;
            int endTime = 0;
            int jobsIdx = 0;
            int cnt = 0;

            // 요청 시간 순 정렬
            Arrays.sort(jobs, (j1, j2) -> j1[0] - j2[0]);
            // 수행 시간 순 정렬
            PriorityQueue<int[]> pq = new PriorityQueue<>((j1, j2) -> j1[1] - j2[1]);

            while (cnt < jobs.length) {
                while (jobsIdx < jobs.length && jobs[jobsIdx][0] <= endTime) {
                    pq.add(jobs[jobsIdx++]);
                }

                if (pq.isEmpty()) endTime = jobs[jobsIdx][0];
                else {
                    int[] job = pq.poll();
                    answer += job[1] + endTime - job[0];
                    endTime += job[1];
                    cnt++;
                }

            }

            return (int) Math.floor(answer / jobs.length);
        }
    }
}
