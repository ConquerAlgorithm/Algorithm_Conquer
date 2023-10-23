package BackJoon.골드.G4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_2110_공유기_설치 {

    static int N, C;
    static int[] house;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        house = new int[N];

        for (int i = 0; i < N; i++) {
            house[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(house);

        int low = 1;
        int high = house[N - 1] - house[0] + 1;

        while (low < high) {
            int mid = (low + high) / 2;

            if (canInstall(mid) < C) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        System.out.println(low - 1);
    }

    static int canInstall(int dist) {
        int cnt = 1;
        int lastLocate = house[0];

        for (int i = 0; i < house.length; i++) {
            int locate = house[i];

            if (locate - lastLocate >= dist) {
                cnt++;
                lastLocate = locate;
            }
        }
        return cnt;
    }
}
