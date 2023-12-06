package Programmers.LV3.징검다리건너기;

public class 징검다리건너기_서요셉 {
    class Solution {
        public int solution(int[] stones, int k) {
            int answer = 0;
            int min = 1;
            int max = 200_000_000;

            while (min <= max) {
                int mid = (min + max) / 2;

                if (canCross(stones, k, mid)) {
                    min = mid + 1;
                    answer = Math.max(answer, mid);
                } else {
                    max = mid - 1;
                }
            }

            return answer;
        }

        boolean canCross(int[] s, int k, int mid) {
            int skip = 0;

            for (int stone : s) {
                if (stone - mid < 0) skip++;
                else skip = 0;

                if (skip == k) return false;
            }

            return true;
        }
    }
}
