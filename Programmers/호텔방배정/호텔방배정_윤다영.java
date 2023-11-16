import java.util.*;

class Solution {
    static Map<Long, Long> map = new HashMap<>();

    public long[] solution(long k, long[] room_number) {
        long[] answer = new long[room_number.length];
        int index = 0;
        for(long num : room_number) answer[index++] = findEmptyRoom(num);
        return answer;
    }

    public static long findEmptyRoom(long num){
        if(!map.containsKey(num)) {
            map.put(num, num + 1);
            return num;
        }
        long emptyNum = findEmptyRoom(map.get(num));
        map.put(num, emptyNum);
        //System.out.println(num + "을 원해요 찾아주세요.");
        //for(Map.Entry<Long, Long> entry : map.entrySet()){
        //     System.out.println(entry.getKey() + "key값이고 다음 빈방은 " + entry.getValue());
        //}
        return emptyNum;
    }
}