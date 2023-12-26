package Programmers.LV3.N으로표현;
import java.util.*;

public class N으로표현 {

    class Solution {
        public int solution(int N, int number) {
            int answer = 0;

            List<Set<Integer>> cntList = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                cntList.add(new HashSet<>());
            }

            cntList.get(1).add(N);

            for (int i = 2; i < 9; i++) {
                Set<Integer> cntSet = cntList.get(i);

                for (int j = 1; j <= i; j++) {
                    Set<Integer> preSet = cntList.get(j);
                    Set<Integer> nextSet = cntList.get(i - j);

                    for (int num1 : preSet) {
                        for (int num2 : nextSet) {
                            cntSet.add(num1 + num2);
                            cntSet.add(num1 - num2);
                            cntSet.add(num1 * num2);

                            if (num1 != 0 && num2 != 0) cntSet.add(num1 / num2);
                        }
                    }
                }

                String strN = String.valueOf(N) + String.valueOf(N);
                cntSet.add(Integer.parseInt(strN));
            }

            for (Set<Integer> set : cntList) {
                if (set.contains(number)) return cntList.indexOf(set);
            }

            return -1;
        }
    }
}
