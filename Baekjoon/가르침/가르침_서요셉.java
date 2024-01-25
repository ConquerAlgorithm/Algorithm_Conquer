package BackJoon.골드.G4.BJ_1062_가르침;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, K;
    static int max;
    static boolean[] studied;
    static String[] word;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        word = new String[N];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();

            input = input.replace("anta", "");
            input = input.replace("tica", "");
            word[i] = input;
        }

        if (K < 5) {
            System.out.println(0);
            return;
        } else if (K == 26) {
            System.out.println(N);
            return;
        }

        studied = new boolean[26];
        studied['a' - 'a'] = true;
        studied['c' - 'a'] = true;
        studied['n' - 'a'] = true;
        studied['t' - 'a'] = true;
        studied['i' - 'a'] = true;

        process(0, 0);
        System.out.println(max);
    }

    static void process(int c, int len) {
        if (len == K - 5) {
            int cnt = 0;

            for (int i = 0; i < N; i++) {
                boolean isRead = true;
                for (int j = 0; j < word[i].length(); j++) {
                    if (!studied[word[i].charAt(j) - 'a']) {
                        isRead = false;
                        break;
                    }
                }
                if (isRead) cnt++;
            }

            max = Math.max(max, cnt);
            return;
        }

        for (int i = c; i < 26; i++) {
            if (!studied[i]) {
                studied[i] = true;
                process(i, len + 1);
                studied[i] = false;
            }
        }

    }
}
