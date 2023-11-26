import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Queue<Integer> q = new LinkedList<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        q.add(n);
        int[] dist = new int[100_001];
        while(!q.isEmpty()) {
            int temp = q.poll();
            if(temp == m) {
                System.out.println(dist[temp]);
                break;
            }
            for(int i = 0; i < 3; i++) {
                int next;
                if(i == 0) next = temp + 1;
                else if(i == 1) next = temp - 1;
                else next = temp * 2;
                if(next < 0 || next > 100_000 || dist[next] != 0) continue;
                q.add(next);
                dist[next] = dist[temp] + 1;
            }
        }
    }
}