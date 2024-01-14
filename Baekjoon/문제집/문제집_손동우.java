import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    private static int N, M;
    private static Node[] nodes;


    public static void main(String[] args) throws Exception {
        init();
        List<Integer> answer = solveProblem();
        answer.forEach(number -> System.out.print(number + " "));
    }

    private static List<Integer> solveProblem() {
        List<Integer> answer = new ArrayList<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (int i = 1; i <= N; i++) {
            if (nodes[i].isTopologyZero()) {
                pq.offer(nodes[i]);
            }
        }
        while (!pq.isEmpty()) {
            Node problem = pq.poll();
            answer.add(problem.number);
            for (Integer nextNumber : problem.nextNumbers) {
                nodes[nextNumber].subtractTopology();
                if (nodes[nextNumber].isTopologyZero()) {
                    pq.offer(nodes[nextNumber]);
                }
            }
        }
        return answer;
    }

    private static void init() throws Exception {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        nodes = new Node[N + 1];
        for (int i = 0; i <= N; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            nodes[from].addNextNumber(to);
            nodes[to].addTopology();
        }
    }

    private static class Node implements Comparable<Node> {
        int number;
        int topology;
        List<Integer> nextNumbers = new ArrayList<>();

        public Node(int number) {
            this.number = number;
        }

        public void addTopology() {
            topology++;
        }

        public void subtractTopology() {
            topology--;
        }

        public boolean isTopologyZero() {
            return topology == 0;
        }

        public void addNextNumber(int nextNumber) {
            this.nextNumbers.add(nextNumber);
        }

        @Override
        public int compareTo(Node n) {
            return this.number - n.number;
        }
    }
}