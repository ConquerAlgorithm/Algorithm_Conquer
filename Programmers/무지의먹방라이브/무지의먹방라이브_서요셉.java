package Programmers.LV4;
import java.util.*;

public class 무지의먹방라이브 {
    class Solution {

        final Comparator<Food> timeComparator = (f1, f2) -> {
            return Integer.compare(f1.time, f2.time);
        };

        final Comparator<Food> idxComparator = (f1, f2) -> {
            return Integer.compare(f1.idx, f2.idx);
        };

        class Food {
            int time, idx;

            public Food(int time, int idx) {
                this.time = time;
                this.idx = idx;
            }
        }

        public int solution(int[] food_times, long k) {
            long foodSum = 0;
            for (int foodTime : food_times) {
                foodSum += foodTime;
            }

            if (foodSum <= k) return -1;

            // 먹는데 걸리는 시간이 작은 순서대로 PQ 삽입
            PriorityQueue<Food> pq = new PriorityQueue<>(timeComparator);
            for (int i = 0; i < food_times.length; i++) {
                pq.add(new Food(food_times[i], i + 1));
            }

            long totalTime = 0;                     // 먹기 위해 사용된 총 시간
            long prevTime = 0;                      // 직전에 다먹은 음식 시간
            long length = food_times.length;        // 남은 음식 개수

            // 현재 먹는 음식을 먹는데 걸리는 시간 = (현재 음식 시간 - 이전 음식 시간 * 배열길이)
            while (totalTime + ((pq.peek().time - prevTime) * length) <= k) {
                int now = pq.poll().time;
                totalTime += (now - prevTime) * length;
                length -= 1;
                prevTime = now;
            }

            // 남은 음식들을 리스트에 넣는다.
            List<Food> remain = new ArrayList<>();
            while (!pq.isEmpty()) {
                remain.add(pq.poll());
            }

            // idx 기준으로 정렬
            Collections.sort(remain, idxComparator);

            return remain.get((int) ((k - totalTime) % length)).idx;
        }
    }
}
