package Programmers.LV3.숫자게임;
import java.util.*;

public class 숫자게임 {

    class Solution {
        public int solution(int[] A, int[] B) {
            int answer = 0;

            Arrays.sort(A);
            Arrays.sort(B);

            int aIdx = 0;
            int bIdx = 0;

            for (int i = 0; i < A.length; i++) {
                if (A[aIdx] > B[bIdx] || A[aIdx] == B[bIdx]) bIdx++;
                else {
                    aIdx++;
                    bIdx++;
                    answer++;
                }
            }

            return answer;
        }
    }
}
