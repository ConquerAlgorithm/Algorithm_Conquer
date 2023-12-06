public class Solution {
    private int answer = 0;
    boolean[] visited;
    boolean[] visitedSet;

    public int solution(String[] user_id, String[] banned_id) {
        visited = new boolean[user_id.length];
        visitedSet = new boolean[(1 << user_id.length)];
        countUserId(user_id, banned_id, 0);
        return answer;
    }

    private void countUserId(String[] userIds, String[] bannedIds, int bannedIndex) {
        if (bannedIndex == bannedIds.length) {
            int visitedSetIndex = 0;
            for (int i = 0; i < visited.length; i++) {
                if (visited[i]) {
                    visitedSetIndex += (1 << i);
                }
            }
            if (!visitedSet[visitedSetIndex]) {
                visitedSet[visitedSetIndex] = true;
                answer++;
            }
            return;
        }
        for (int i = 0; i < userIds.length; i++) {
            if (!visited[i] && checkUserId(userIds[i], bannedIds[bannedIndex])) {
                visited[i] = true;
                countUserId(userIds, bannedIds, bannedIndex + 1);
                visited[i] = false;
            }
        }
    }

    private boolean checkUserId(String userId, String bannedId) {
        if (userId.length() != bannedId.length()) {
            return false;
        }
        for (int i = 0; i < bannedId.length(); i++) {
            char c = bannedId.charAt(i);
            if (c != '*' && c != userId.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        String[] userId = {"frodo", "fradi", "crodo", "abc123", "frodoc"};
        String[] bannedId = {"fr*d*", "abc1**"};
//        String[] userId = {"frodo", "fradi", "crodo", "abc123", "frodoc"};
//        String[] bannedId = {"*rodo", "*rodo", "******"};
        int answer = s.solution(userId, bannedId);
        System.out.println("answer = " + answer);
    }
}