import java.util.Stack;

public class Solution {
    public String solution(String number, int k) {
        Stack<Integer> s = new Stack<>();
        for (int i = number.length() - 1; i >= 0; i--) {
            s.add(number.charAt(i) - '0');
        }
        Stack<Integer> answer = new Stack<>();
        while (!s.isEmpty() && k > 0) {
            int num = s.pop();
            if (answer.isEmpty() || answer.peek() >= num) {
                answer.add(num);
            } else {
                while (!answer.isEmpty() && answer.peek() < num && k > 0) {
                    answer.pop();
                    k--;
                }
                answer.add(num);
            }
        }
        while (!s.isEmpty()) {
            answer.add(s.pop());
        }
        while (k > 0) {
            answer.pop();
            k--;
        }

        StringBuilder sb = new StringBuilder();
        for (Integer num : answer) {
            sb.append(num);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        String number = "111";
        int k = 1;
        String answer = s.solution(number, k);
        System.out.println("answer = " + answer);
    }
}