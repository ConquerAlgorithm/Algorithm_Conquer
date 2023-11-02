package Programmers.LV3;
import java.util.*;

public class 이중우선순위큐_Wrapper클래스사용 {
    class Solution {
        public int[] solution(String[] operations) {
            int[] answer = {};

            // 최대힙, 최소힙 구성
            PriorityQueue<Integer> maxPQ = new PriorityQueue<>((o1, o2) -> (o2 - o1));
            PriorityQueue<Integer> minPQ = new PriorityQueue<>((o1, o2) -> (o1 - o2));

            for (int i = 0; i < operations.length; i++) {
                String[] OP = operations[i].split(" ");

                String command = OP[0];
                Integer num = Integer.parseInt(OP[1]);

                // 삽입
                if (command.equals("I")) {
                    // 새로운 숫자를 PQ에 삽입합니다.
                    // Num newNum = new Num(i, num);

                    maxPQ.offer(num);
                    minPQ.offer(num);
                } else {
                    // 최댓값 삭제
                    if (num == 1) {
                        // 최대힙에서 숫자하나를 꺼내고 해당 숫자의 id를 가진 숫자를 최소힙에서도 삭제합니다.
                        // 만일 q가 비어있다면 해당 연산은 무시한다.
                        if (maxPQ.isEmpty()) continue;

                        minPQ.remove(maxPQ.poll());
                    }
                    // 최소값 삭제
                    else {
                        // 최소힙에서 숫자하나를 꺼내고 해당 숫자의 id를 가진 숫자를 최대힙에서도 삭제합니다.
                        // 만일 q가 비어있다면 해당 연산은 무시한다.
                        if (minPQ.isEmpty()) continue;

                        maxPQ.remove(minPQ.poll());
                    }
                }
            }

            if (maxPQ.isEmpty()) return new int[]{0, 0};
            else return new int[]{maxPQ.peek(), minPQ.peek()};
        }
    }
}
