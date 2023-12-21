import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int N, M;
    private static int[][] adjacent;
    private static int[] cities;
    private static int[] roots;

    public static void main(String[] args) throws Exception {
        init();
        joinCities();
        String answer = isAllAdjacent();
        System.out.println(answer);
    }

    private static String isAllAdjacent() {
        int root = find(cities[0]);
        for (int i = 1; i < M; i++) {
            if (root != find(cities[i])) {
                return "NO";
            }
        }
        return "YES";
    }

    private static void joinCities() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (adjacent[i][j] == 1) {
                    join(i, j);
                }
            }
        }
    }

    private static int find(int number) {
        if (roots[number] == number) {
            return number;
        } else {
            return roots[number] = find(roots[number]);
        }
    }

    private static void join(int number1, int number2) {
        int root1 = find(number1);
        int root2 = find(number2);

        if (root1 != root2) {
            roots[root2] = root1;
        }
    }

    private static void init() throws Exception {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        adjacent = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                adjacent[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        cities = new int[M];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            cities[i] = Integer.parseInt(st.nextToken());
        }
        roots = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            roots[i] = i;
        }
    }
}