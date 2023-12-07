package BackJoon.골드.G4.BJ_9935_문자열폭발;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class 문자열폭발 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = br.readLine();
        String ex = br.readLine();

        int exSize = ex.length();

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            stack.push(input.charAt(i));

            if (stack.size() >= exSize) {
                boolean isEx = true;

                for (int j = 0; j < exSize; j++) {
                    if (stack.get(stack.size() - exSize + j) != ex.charAt(j)) {
                        isEx = false;
                        break;
                    }
                }

                if (isEx) {
                    for (int j = 0; j < exSize; j++) {
                        stack.pop();
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Character c: stack) {
            sb.append(c);
        }

        System.out.println(sb.length() == 0 ? "FRULA" : sb);
    }
}
