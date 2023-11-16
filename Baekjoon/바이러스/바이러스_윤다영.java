import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

abstract class Graph {
    int vertex;
    int answer;

    public Graph(int vertex) {
        this.vertex = vertex;
        this.answer = 0;
    }

    abstract void addEdge(int vertex, int edge);

    abstract void dfs(int vertex, boolean[] visited);
}

public class Solution {
    static Graph graphList;
    //  static Graph graphArray;

    static class GraphArray extends Graph {
        int[][] adj;

        public GraphArray(int vertex) {
            super(vertex);
            adj = new int[vertex + 1][vertex + 1];
        }

        @Override
        void addEdge(int vertex, int edge) {
            adj[vertex][edge] = 1;
            adj[edge][vertex] = 1;
        }

        @Override
        void dfs(int vertex, boolean[] visited) {
            visited[vertex] = true;
            for (int n : adj[vertex]) {
                if (visited[n] && n == 0)
                    continue;
                visited[n] = true;
                dfs(n, visited);
                answer++;
            }

        }
    }

    static class GraphList extends Graph {
        List<List<Integer>> adj;

        public GraphList(int vertex) {
            super(vertex);
            adj = new ArrayList<>();
            for (int i = 0; i <= vertex; i++) {
                adj.add(i, new ArrayList<>());
            }
        }

        @Override
        void addEdge(int vertex, int edge) {
            adj.get(vertex).add(edge);
            adj.get(edge).add(vertex);
        }

        @Override
        void dfs(int vertex, boolean[] visited) {
            visited[vertex] = true;
            for (int n : this.adj.get(vertex)) {
                if (visited[n])
                    continue;
                visited[n] = true;
                dfs(n, visited);
                answer++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int vertex = Integer.parseInt(br.readLine());
        int edge = Integer.parseInt(br.readLine());

        graphList = new GraphList(vertex);
        //  graphArray = new GraphArray(vertex);

        StringTokenizer st;
        for (int i = 0; i < edge; i++) {
            st = new StringTokenizer(br.readLine());
            int startV = Integer.parseInt(st.nextToken());
            int startE = Integer.parseInt(st.nextToken());
            graphList.addEdge(startV, startE);
            //   graphArray.addEdge(startV, startE);
        }
        graphList.dfs(1, new boolean[vertex + 1]);
        // graphArray.dfs(1, new boolean[vertex + 1]);
        System.out.println(graphList.answer);
    }
}