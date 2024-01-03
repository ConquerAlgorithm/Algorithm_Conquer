import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 3; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 3; j++) {
                sb.append(st.nextToken());
            }
        }

        Queue<String> q = new LinkedList<>();
        Map<String, Integer> map = new HashMap<>();
        q.add(sb.toString());
        map.put(sb.toString(), 0);
        int[] dx = new int[]{-1, 0, 1, 0};
        int[] dy = new int[]{0, 1, 0 ,-1};
        while(!q.isEmpty()) {
            String cur = q.poll();
            int idx = cur.indexOf("0");
            int x = idx / 3;
            int y = idx % 3;

            for(int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                int nIdx = nx * 3 + ny;
                if(nx < 0 || nx >= 3 || ny < 0 || ny >= 3) continue;
                StringBuilder next = new StringBuilder(cur);
                char tmp = next.charAt(nIdx);
                next.setCharAt(nIdx, '0');
                next.setCharAt(idx, tmp);
                if(map.containsKey(next.toString())) continue;
                map.put(next.toString(), map.get(cur) + 1);
                q.add(next.toString());
            }
        }

        if(map.containsKey("123456780")) System.out.println(map.get("123456780"));
        else System.out.println(-1);
    }
}