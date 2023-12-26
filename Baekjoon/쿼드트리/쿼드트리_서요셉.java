package BackJoon.실버.S3.BJ_6580_쿼드트리;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class 쿼드트리 {
    static int N;
    static boolean[][] bitCheck;
    static StringBuilder sb = new StringBuilder();

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine().split(" ")[2]);
        br.readLine();
        br.readLine();
        bitCheck = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            String[] pixels = br.readLine().split(",");

            int start = 0;
            int end = 8;

            for (int j = 0; j < N / 8; j++) {
                int pixel = Integer.parseInt(pixels[j].substring(2), 16);

                for (int k = start; k < end; k++) {
                    if (pixel % 2 == 1) bitCheck[i][k] = true;
                    pixel /= 2;
                }

                start += 8;
                end += 8;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        input();
        System.out.println(N);
        encrypt(0, 0, N);
        System.out.println(sb);
    }

    static void encrypt(int x, int y, int size) {
        if (size == 1) {
            sb.append(bitCheck[x][y] ? "B" : "W");
            return;
        }
        if (isAll(x, y, size)) {
            sb.append(bitCheck[x][y] ? "B" : "W");
            return;
        }

        sb.append("Q");

        size /= 2;

        encrypt(x, y, size);
        encrypt(x, y + size, size);
        encrypt(x + size, y, size);
        encrypt(x + size, y + size, size);
    }

    static boolean isAll(int x, int y, int size) {
        boolean check = bitCheck[x][y];

        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (check ^ bitCheck[i][j]) return false;
            }
        }

        return true;
    }
}
