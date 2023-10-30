package Programmers.LV3;
import java.util.*;

/*
    - 각 원소의 합이 S가 되는 수의 집합
    - 위 조건을 만족하면서 각 원소의 곱이 최대가 되는 집합
*/
public class 최고의_집합 {
    public int[] solution(int n, int s) {
        // 숫자 개수가 만드려는 합 s 보다 크다면 집합을 만들 수 없다.
        // ex - n : 10 s : 9 -> 각 수가 1이기만 해도 9를 넘어버림
        if (n > s) return new int[]{-1};

        // s를 n개로 나눈 값들에서 나머지만큼씩 더해진 값들이 최대곱의 값에 근접한 값임.
        int[] answer = new int[n];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = s / n;
        }

        for (int i = 0; i < s % n; i++) {
            answer[i]++;
        }
        Arrays.sort(answer);

        return answer;
    }
}