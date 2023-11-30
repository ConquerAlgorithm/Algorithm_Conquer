import java.util.*;

class Solution {
    public int solution(int[] A, int[] B) {
        int answer = 0;
        Arrays.sort(A);
        Arrays.sort(B);

        int bInx = 0;
        int aInx = 0;

        while(bInx < B.length) {
            if(B[bInx] < A[aInx]) bInx++;
            else if(B[bInx] == A[aInx]) bInx++;
            else {
                aInx++; bInx++; answer++;
            }
        }

        return answer;
    }
}