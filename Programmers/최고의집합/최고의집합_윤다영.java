import java.util.*;

class Solution {
    public int[] solution(int n, int s) {
        int[] answer = new int[n];
        if(n > s) return new int[]{-1};

        Arrays.fill(answer, s/n);

        for(int i = 0; i < s % n; i++) {
            //System.out.println((n - 1) - i);
            answer[(n - 1) - i] += 1;
        }
        return answer;
    }
}