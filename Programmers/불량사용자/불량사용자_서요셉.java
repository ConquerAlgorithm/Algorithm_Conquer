package Programmers.LV3.불량사용자;
import java.util.*;

// 유저 아이디에서 밴아이디라고 생각되는 아이디를 추가하여 담을 셋.
// 그 셋들을 모으는 셋.
// dfs 에서 모든 밴 아이디를 체크했다면 끝
public class 불량_사용자 {
    class Solution {

        String[] userIds, bannedIds;
        HashSet<HashSet<String>> hs;

        public int solution(String[] user_id, String[] banned_id) {
            userIds = user_id;
            bannedIds = banned_id;

            hs = new HashSet<>();

            DFS(new HashSet<>(), 0);

            return hs.size();
        }

        void DFS(HashSet<String> set, int depth) {
            if (depth == bannedIds.length) {
                hs.add(set);
                return;
            }

            for (int i = 0; i < userIds.length; i++) {
                if (set.contains(userIds[i])) continue;

                if (check(userIds[i], bannedIds[depth])) {
                    set.add(userIds[i]);
                    DFS(new HashSet<>(set), depth + 1);
                    set.remove(userIds[i]);
                }
            }
        }

        boolean check(String userId, String bannedId) {
            if (userId.length() != bannedId.length()) return false;

            boolean isMatch = true;
            for (int i = 0; i < userId.length(); i++) {
                if (bannedId.charAt(i) != '*' && userId.charAt(i) != bannedId.charAt(i)) {
                    isMatch = false;
                    break;
                }
            }

            return isMatch;
        }
    }
}
