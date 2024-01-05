import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static int N, M, K;
    private static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        init();
        getKthWord(N, M, K);
        System.out.println(sb.toString());
    }

    private static void getKthWord(int n, int m, int k) {
//        System.out.print("n = " + n);
//        System.out.print(", m = " + m);
//        System.out.println(", k = " + k);
        if (n == 0 || m == 0) {
            if (k > 1) {
                sb = new StringBuilder().append(-1);
            } else {
                if (n > 0) {
                    sb.append("a".repeat(n));
                } else if (m > 0) {
                    sb.append("z".repeat(m));
                }
            }
            return;
        } else if (k == 1) {
            sb.append("a".repeat(n));
            sb.append("z".repeat(m));
            return;
        }
        int vacant = m - 1;
        int a = 0;
        int comb = 1;
        int count = 0;
        while (count + comb < k && vacant < m + n && a <= n) {
            count += comb;
            vacant++;
            a++;
            comb = comb * vacant / a;
        }
        if (a > n || vacant >= m + n) {
            sb = new StringBuilder().append(-1);
            return;
        }
        sb.append("a".repeat(Math.max(0, n - a)));
        sb.append("z");
//        System.out.print("a = " + a);
//        System.out.print(", vacant = " + vacant);
//        System.out.print(", count = " + count);
//        System.out.println(", sb = " + sb);
//        System.out.println();
        getKthWord(a, m - 1, k - count);
    }

    private static void init() throws Exception {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
    }
}