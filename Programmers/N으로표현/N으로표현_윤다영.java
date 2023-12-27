import java.util.*;

class Solution {
    public int solution(int N, int number) {
        List<Set<Integer>> list = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            list.add(new HashSet<>());
        }

        list.get(1).add(N);
        for(int i = 2; i < 9; i++) {
            Set<Integer> set = list.get(i);
            for(int j = 1; j <= i; j++) {
                Set<Integer> pre = list.get(j);
                Set<Integer> post = list.get(i - j);

                for(int preN : pre) {
                    for(int postN : post) {
                        set.add(preN + postN);
                        set.add(preN - postN);
                        set.add(preN * postN);

                        if(preN != 0 && postN != 0) set.add(preN / postN);
                    }
                }
            }
            set.add(Integer.parseInt(String.valueOf(N).repeat(i)));
        }

        for(Set<Integer> set : list) {
            if(set.contains(number)) return list.indexOf(set);
        }
        return -1;
    }
}