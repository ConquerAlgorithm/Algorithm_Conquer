package Programmers.LV3.억억단을_외우자;
import java.util.*;

public class 억억단을외우자 {

    class Solution {

        Num[] DP;

        public int[] solution(int e, int[] starts) {
            int[] answer = new int[starts.length];

            calculate(e);

            for (int i = 0; i < starts.length; i++) {
                int s = starts[i];
                for (int j = 0; j < e; j++) {
                    if (DP[j].num >= s) {
                        answer[i] = DP[j].num;
                        break;
                    }
                }
            }

            return answer;
        }

        void calculate(int e) {
            DP = new Num[e];

            for (int i = 1; i <= e; i++) {
                DP[i - 1] = new Num(i, 1);
            }

            for (int i = 2; i <= e; i++) {
                for (int j = 1; j <= e / i; j++) {
                    DP[i * j - 1].cnt++;
                }
            }

            Arrays.sort(DP);

        }

        class Num implements Comparable<Num> {
            int num, cnt;

            public Num(int num, int cnt) {
                this.num = num;
                this.cnt = cnt;
            }

            @Override
            public int compareTo(Num n) {
                if (n.cnt != this.cnt) return n.cnt - this.cnt;
                else return this.num - n.num;
            }
        }
    }
}
