package Programmers.LV4;
import java.util.*;

public class 호텔방배정 {
    class Solution {

        HashMap<Long, Long> roomHM;   // <rNo, nNo> : 방 번호, 다음 방 번호

        public long[] solution(long k, long[] room_number) {
            long[] answer = new long[room_number.length];

            roomHM = new HashMap<>();

            for (int i = 0; i < room_number.length; i++) {
                answer[i] = findRoomNo(room_number[i]);
            }

            return answer;
        }

        long findRoomNo(long roomNo) {
            // 빈 방이라면
            if (!roomHM.containsKey(roomNo)) {
                roomHM.put(roomNo, roomNo + 1);
                return roomNo;
            }

            // 빈방이 아니라면
            long nextRoomNo = roomHM.get(roomNo);
            long emptyRoomNo = findRoomNo(nextRoomNo);
            roomHM.put(roomNo, emptyRoomNo);
            return emptyRoomNo;
        }

    }
}
