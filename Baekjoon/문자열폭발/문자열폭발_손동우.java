import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    private static String str;
    private static String bomb;

    public static void main(String[] args) throws IOException {
        init();
        String answer = getAnswer();
        if (answer.isEmpty()) {
            System.out.println("FRULA");
        } else {
            System.out.println(answer);
        }
    }

    private static String getAnswer() {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            stack.push(c);
            if (c == bomb.charAt(bomb.length() - 1)) {
                checkAndBomb(stack);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : stack) {
            sb.append(c);
        }
        return sb.toString();
    }

    private static void checkAndBomb(Stack<Character> stack) {
        int stackIndex = stack.size() - 1;
        int bombIndex = bomb.length() - 1;
        if (stackIndex < bombIndex) {
            return;
        }
        while (bombIndex >= 0) {
            if (stack.get(stackIndex) == bomb.charAt(bombIndex)) {
                stackIndex--;
                bombIndex--;
            } else {
                return;
            }
        }
        for (int i = 0; i < bomb.length(); i++) {
            stack.pop();
        }
    }

    private static void init() throws IOException {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        str = br.readLine();
        bomb = br.readLine();
    }
}