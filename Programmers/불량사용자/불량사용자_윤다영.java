import java.util.*;

class Solution {
    Set<Set<String>> answer = new HashSet<>();
    public int solution(String[] user_id, String[] banned_id) {
        dfs(new LinkedHashSet<>(), user_id, banned_id);
        return answer.size();
    }

    public void dfs(Set<String> set, String[] user_id, String[] banned_id) {
        if(set.size() == banned_id.length) {
            if(check(set, banned_id)) {
                answer.add(new HashSet<>(set));
            }
            return;
        }

        for(String user : user_id) {
            if(set.add(user)) {
                dfs(set, user_id, banned_id);
                set.remove(user);
            }
        }
    }

    public boolean check(Set<String> set, String[] banned_id) {
        int idx = 0;
        for(String user : set) {
            String ban = banned_id[idx++];
            if(user.length() != ban.length()) return false;

            for(int i = 0; i < ban.length(); i++) {
                if(ban.charAt(i) == '*') continue;
                if(user.charAt(i) != ban.charAt(i)) return false;
            }
        }
        return true;
    }
}