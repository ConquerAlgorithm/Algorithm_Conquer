package SWEA.D2.SWEA_1288_새로운_불면증_치료법;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
1
1295
 */
public class 새로운불면증치료법 {

    static final int MAX_LEN = 10;

    static int N, ans;
    static boolean[] check;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {

            N = Integer.parseInt(br.readLine());
            check = new boolean[MAX_LEN];

            ans = 0;
            // check 의 모든 인덱스가 찼다면 중지
            while (!isAll()) {
                ans++;

                int num = ans * N;

                // num 을 길이 만큼 돌고 하나씩 자른다.
                String num_str = String.valueOf(num);
                for (int j = 0; j < num_str.length(); j++) {
                    int num_one = num_str.charAt(j) - '0';
                    check[num_one] = true;
                }
            }

            System.out.println("#" + t + " " + ans * N);
        }
    }

    static boolean isAll() {
        for (int i = 0; i < MAX_LEN; i++) {
            if (!check[i]) return false;
        }
        return true;
    }
}
