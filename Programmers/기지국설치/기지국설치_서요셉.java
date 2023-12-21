package Programmers.LV3.기지국설치;

public class 기지국설치 {
    class Solution {
        public int solution(int n, int[] stations, int w) {
            int answer = 0;

            int begin = 1;
            for (int i = 0; i < stations.length; i++) {
                int station = stations[i];

                if (begin < station - w) answer += search(begin, station - w - 1, w);
                begin = station + w + 1;
            }

            if (stations[stations.length - 1] + w < n) {
                answer += search(stations[stations.length - 1] + w + 1, n, w);
            }

            return answer;
        }

        int search(int begin, int end, int w) {
            int res = (end - begin + 1) / (2 * w + 1);
            if ((end - begin + 1) % (2 * w + 1) > 0) res++;

            return res;
        }
    }
}
