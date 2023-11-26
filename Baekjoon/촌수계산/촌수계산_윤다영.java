import java.util.Scanner;

public class Main {

    public static int[][] node;
    public static boolean[] visited;
    public static int M, N, start, end;
    public static int answer = Integer.MAX_VALUE;

    public static void dfs(int start, int end, int count) {
        if (start == end) {
            answer = Math.min(answer, count);
        }

        for (int i = 1; i < visited.length; i++) {
            if (node[start][i] == 1 && !visited[i]) {
                visited[i] = true;
                dfs(i, end, count + 1);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        M = sc.nextInt();
        start = sc.nextInt();
        end = sc.nextInt();
        N = sc.nextInt();

        node = new int[M + 1][M + 1];
        visited = new boolean[M + 1];

        for (int i = 0; i < N; i++) {
            int m = sc.nextInt();
            int n = sc.nextInt();
            node[m][n] = node[n][m] = 1;
        }
        visited[start] = true;
        dfs(start, end, 0);
        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }
}