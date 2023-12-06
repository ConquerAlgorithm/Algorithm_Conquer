public class Solution {
    public int solution(int[] stones, int k) {
        int left = 1;
        int right = 200_000_000;
        int answer = 0;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (isAvailable(stones, k, mid)) {
                left = mid + 1;
                answer = Math.max(answer, mid);
            } else {
                right = mid - 1;
            }
        }
        return answer;
    }

    private boolean isAvailable(int[] stones, int k, int mid) {
        int jump = 0;
        for (int stone : stones) {
            if (stone - mid < 0) {
                jump++;
            } else {
                jump = 0;
            }
            if (jump == k) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] stones = {2, 4, 5, 3, 2, 1, 4, 2, 5, 1};
        int k = 3;
        int answer = s.solution(stones, k);
        System.out.println("answer = " + answer);
    }
}