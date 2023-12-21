public class Solution {
    public int solution(int n, int[] stations, int w) {
        int answer = 0;
        if (stations[0] - w - 1 > 0) {
            answer += (stations[0] - w - 1 + 2 * w) / (2 * w + 1);
        }
        for (int i = 0; i < stations.length - 1; i++) {
            if ((stations[i + 1] - w) - (stations[i] + w + 1) > 0) {
                answer += ((stations[i + 1] - w) - (stations[i] + w + 1) + (2 * w)) / (2 * w + 1);
            }
        }
        if ((n + 1) - (stations[stations.length - 1] + w + 1) > 0) {
            answer += ((n + 1) - (stations[stations.length - 1] + w + 1) + (2 * w)) / (2 * w + 1);
        }
        return answer;
    }

    public static void main(String[] args) {
        int n = 11;
        int[] stations = {4, 11};
        int w = 1;
        Solution s = new Solution();
        int answer = s.solution(n, stations, w);
        System.out.println("answer = " + answer);
    }
}