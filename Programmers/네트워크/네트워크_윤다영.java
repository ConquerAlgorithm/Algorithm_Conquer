class Solution {
    private static int[] unf;
    public int solution(int n, int[][] computers) {
        unf = new int[n];
        for(int i = 0; i < n; i++) unf[i] = i;
        for(int i = 0; i < computers.length; i++) {
            for(int j = 0; j < computers.length - 1; j++) {
                if(computers[i][j] == 1 && i != j) {
                    union(i, j);
                }
            }
        }

        int answer = 0;
        for(int i = 0; i < n; i++) {
            if(unf[i] == i) answer++;
        }

        return answer;
    }

    public void union(int a, int b) {
        int fa = find(a);
        int fb = find(b);
        if(fa != fb) unf[fa] = fb;
    }

    public int find(int v) {
        return (v == unf[v]) ? v : (unf[v] = find(unf[v]));
    }
}