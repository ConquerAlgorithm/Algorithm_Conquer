import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static final int MAX_DISTANCE = 1000;
    private static int T;
    private static int N;
    private static Node home, rock;
    private static Node[] convs;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            init(br);
            boolean answer = bfs();
            if (answer) {
                System.out.println("happy");
            } else {
                System.out.println("sad");
            }
        }
    }

    private static boolean bfs() {
        Queue<Node> queue = new LinkedList<>();
        boolean[] visited = new boolean[N];
        queue.offer(home);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (getDistance(node, rock) <= MAX_DISTANCE) {
                return true;
            }
            for (int i = 0; i < N; i++) {
                if (!visited[i] && getDistance(node, convs[i]) <= MAX_DISTANCE) {
                    visited[i] = true;
                    queue.offer(convs[i]);
                }
            }
        }
        return false;
    }

    private static int getDistance(Node node1, Node node2) {
        return Math.abs(node1.y - node2.y) + Math.abs(node1.x - node2.x);
    }

    private static void init(BufferedReader br) throws Exception {
        N = Integer.parseInt(br.readLine());
        convs = new Node[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        int y = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        home = new Node(y, x);
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            y = Integer.parseInt(st.nextToken());
            x = Integer.parseInt(st.nextToken());
            convs[i] = new Node(y, x);
        }
        st = new StringTokenizer(br.readLine());
        y = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        rock = new Node(y, x);
    }

    private static class Node {
        int y;
        int x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}