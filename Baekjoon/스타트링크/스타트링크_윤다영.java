import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int F = Integer.parseInt(st.nextToken()); // 층수
        int S = Integer.parseInt(st.nextToken()); // 현재 층
        int G = Integer.parseInt(st.nextToken()); // 목표 층
        int U = Integer.parseInt(st.nextToken()); // 위로 U층
        int D = Integer.parseInt(st.nextToken()); // 아래로 D층

        int[] check = new int[F + 1];
        Queue<Integer> q = new LinkedList<>();
        q.add(S);
        check[S] = 1;

        while(!q.isEmpty()) {
            int temp = q.poll();
            if(temp == G) {
                System.out.println(check[temp] - 1);
                return;
            }

            if(temp + U <= F && check[temp + U] == 0) {
                check[temp + U] = check[temp] + 1;
                q.add(temp + U);
            }

            if(temp - D > 0 && check[temp - D] == 0) {
                check[temp - D] = check[temp] + 1;
                q.add(temp - D);
            }
        }

        System.out.println("use the stairs");
    }
}