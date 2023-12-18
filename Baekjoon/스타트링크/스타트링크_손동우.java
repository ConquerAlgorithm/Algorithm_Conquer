import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static int F, S, G, U, D;
    private static boolean[] visited;

    public static void main(String[] args) throws IOException {
        init();
        int btnCount = getBtnCount();
        if (btnCount == -1) {
            System.out.println("use the stairs");
        } else {
            System.out.println(btnCount);
        }
    }

    private static int getBtnCount() {
        Queue<Integer> floors = new LinkedList<>();
        visited[S] = true;
        floors.add(S);
        int btnCount = 0;
        int size = 1;
        while (!floors.isEmpty()) {
            int floor = floors.poll();
            if (floor == G) {
                break;
            }
            int upFloor = floor + U;
            int downFloor = floor - D;
            if (upFloor <= F && !visited[upFloor]) {
                visited[upFloor] = true;
                floors.offer(upFloor);
            }
            if (downFloor > 0 && !visited[downFloor]) {
                visited[downFloor] = true;
                floors.offer(downFloor);
            }
            if (--size == 0) {
                btnCount++;
                size = floors.size();
            }
        }
        if (!visited[G]) {
            return -1;
        }
        return btnCount;
    }

    private static void init() throws IOException {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        F = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        G = Integer.parseInt(st.nextToken());
        U = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        visited = new boolean[F + 1];
    }
}