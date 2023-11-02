class Solution {
    int answer = Integer.MAX_VALUE;
    String target;

    public int solution(String begin, String target, String[] words) {
        this.target = target;

        dfs(begin, words, new boolean[words.length], 0);
        return answer == Integer.MAX_VALUE ? 0 : answer;
    }

    public void dfs(String now, String[] words, boolean[] visited, int depth) {
        if(now.equals(target)) {
            answer = depth;
            return;
        }
        for(int i = 0; i < words.length; i++) {
            if(visited[i]) continue;
            int cnt = 0;
            for(int j = 0; j < now.length(); j++) {
                if(now.charAt(j) != words[i].charAt(j)) cnt++;
            }

            if(cnt == 1) {
                visited[i] = true;
                dfs(words[i], words, visited, depth+1);
                visited[i] = false;
            }
        }
    }
}