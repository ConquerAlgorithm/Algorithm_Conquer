package Programmers.LV3.풍선_터트리기;

public class 풍선터트리기 {
    class Solution {

        int[] leftMin, rightMin;
        int l, r;

        public int solution(int[] a) {
            int answer = 0;

            int len = a.length;
            leftMin = new int[len];
            rightMin = new int[len];
            l = a[0];
            r = a[len - 1];

            for (int i = 1; i < len - 1; i++) {
                if (l > a[i]) l = a[i];
                leftMin[i] = l;
            }

            for (int i = len - 2; i > 0; i--) {
                if (r > a[i]) r = a[i];
                rightMin[i] = r;
            }

            if (len == 1) return 1;
            answer = 2;

            for (int i = 1; i < len - 1; i++) {
                if (a[i] > leftMin[i] && a[i] > rightMin[i]) continue;
                answer++;
            }

            return answer;
        }
    }
}
