import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Solution {
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());
            int count = getMinCount(N);
            System.out.println("#" + tc + " " + count);
        }
    }

    private static int getMinCount(int n) {
        boolean[] visited = new boolean[10];
        int mul = 1;
        while (!isAllVisited(visited)) {
            setVisited(mul * n, visited);
            mul++;
        }
        return (mul - 1) * n;
    }

    private static void setVisited(int num, boolean[] visited) {
        String str = Integer.toString(num);
        for (int i = 0; i < str.length(); i++) {
            visited[str.charAt(i) - '0'] = true;
        }
    }

    private static boolean isAllVisited(boolean[] visited) {
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                return false;
            }
        }
        return true;
    }
}