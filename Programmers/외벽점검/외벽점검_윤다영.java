import java.util.*;

class Solution {
    int[] weak;
    int[] dist;
    int n, answer;

    public int solution(int n, int[] weak, int[] dist) {
        this.weak = weak;
        this.dist = dist;
        this.n = n;
        this.answer = Integer.MAX_VALUE;

        permutaion(0, dist.length, new boolean[dist.length], new int[dist.length]);

        if(answer == Integer.MAX_VALUE) return -1;

        return answer;
    }

    public void permutaion(int depth, int r, boolean[] visited, int[] result) {
        if (depth == r) {
            check(result);
            return;
        }

        for (int i = 0; i < dist.length; i++) {
            if (visited[i])
                continue;
            visited[i] = true;
            result[depth] = dist[i];
            permutaion(depth + 1, r, visited, result);
            visited[i] = false;
        }
    }

    public void check(int[] result) {
        // waek 조정
        int[] weakTemp = weak.clone();
        for (int i = 0; i < weakTemp.length; i++) {
            // check하는 알고리즘 공부해야함
            int cur = 0, next;
            int disIdx = 0;
            while(cur < weakTemp.length && disIdx < result.length){
                next = cur+1;
                while(next < weakTemp.length &&
                    weakTemp[cur] + result[disIdx] >= weakTemp[next]){
                    next++;
                }
                cur = next;
                disIdx++;
            }
            if(cur == weakTemp.length && disIdx < answer)
                answer = disIdx;

            weakTemp[0] += n;
            Arrays.sort(weakTemp);
        }
    }
}