import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Main {
    private static final int N = 3;
    private static final int[] dy = {-1, 1, 0, 0};
    private static final int[] dx = {0, 0, -1, 1};
    private static final String ANSWER = "123456780";
    private static final Map<String, Integer> visited = new HashMap<>();

    public static void main(String[] args) throws Exception {
        init();
        int answer = bfs();
        System.out.println(answer);
    }

    private static int bfs() {
        if (visited.keySet().stream().allMatch(ANSWER::equals)) {
            return 0;
        }
        Queue<String> q = new LinkedList<>();
        visited.keySet().forEach(q::offer);
        while (!q.isEmpty()) {
            String grid = q.poll();
            int count = visited.get(grid);
            int emptyIndex = grid.indexOf("0");
            int y = emptyIndex / 3;
            int x = emptyIndex % 3;
            for (int d = 0; d < 4; d++) {
                int ny = y + dy[d];
                int nx = x + dx[d];
                if (isInGrid(ny, nx)) {
                    int swapIndex = ny * N + nx;
                    String nGrid = makeNextGrid(grid, emptyIndex, swapIndex);
                    if (nGrid.equals(ANSWER)) {
                        return count + 1;
                    } else if (!visited.containsKey(nGrid)) {
                        q.offer(nGrid);
                        visited.put(nGrid, count + 1);
                    }
                }
            }
        }
        return -1;
    }

    private static String makeNextGrid(String grid, int emptyIndex, int swapIndex) {
        StringBuilder sb = new StringBuilder(grid);
        sb.setCharAt(emptyIndex, sb.charAt(swapIndex));
        sb.setCharAt(swapIndex, '0');
        return sb.toString();
    }

    private static boolean isInGrid(int y, int x) {
        return 0 <= y && y < N && 0 <= x && x < N;
    }

    private static void init() throws Exception {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            sb.append(br.readLine().replaceAll(" ", ""));
        }
        visited.put(sb.toString(), 0);
    }
}