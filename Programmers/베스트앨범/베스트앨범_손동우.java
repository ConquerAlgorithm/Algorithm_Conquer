import java.util.*;

public class Solution {
    private final Map<String, Integer> genreTotalPlayMap = new HashMap<>();
    private final Map<String, PriorityQueue<Play>> genrePlayMap = new HashMap<>();

    public int[] solution(String[] genres, int[] plays) {
        setMaps(genres, plays);
        List<Integer> answer = new ArrayList<>();
        while (!genreTotalPlayMap.isEmpty()) {
            String maxGenre = genreTotalPlayMap.keySet().stream()
                    .max(Comparator.comparingInt(genreTotalPlayMap::get))
                    .get();
            PriorityQueue<Play> pq = genrePlayMap.get(maxGenre);
            for (int i = 0; i < 2 && !pq.isEmpty(); i++) {
                answer.add(pq.poll().index);
            }
            genreTotalPlayMap.remove(maxGenre);
            genrePlayMap.remove(maxGenre);
        }
        return answer.stream().mapToInt(i -> i).toArray();
    }

    private void setMaps(String[] genres, int[] plays) {
        for (int i = 0; i < genres.length; i++) {
            int finalI = i;
            genreTotalPlayMap.computeIfPresent(genres[i], (k, v) -> v + plays[finalI]);
            genreTotalPlayMap.putIfAbsent(genres[i], plays[i]);

            PriorityQueue<Play> pq = genrePlayMap.get(genres[i]);
            if (pq == null) {
                pq = new PriorityQueue<>();
                pq.offer(new Play(plays[i], i));
                genrePlayMap.put(genres[i], pq);
            } else {
                pq.offer(new Play(plays[i], i));
            }
        }
    }

    private class Play implements Comparable {
        int count;
        int index;

        public Play(int count, int index) {
            this.count = count;
            this.index = index;
        }

        @Override
        public int compareTo(Object o) {
            Play play = (Play) o;
            return play.count - this.count;
        }

        @Override
        public String toString() {
            return "Play{" +
                    "count=" + count +
                    ", index=" + index +
                    '}';
        }
    }

    public static void main(String[] args) {
        String[] genres = {"classic", "pop", "classic", "classic", "pop"};
        int[] plays = {500, 600, 150, 800, 2500};
        Solution s = new Solution();
        int[] answer = s.solution(genres, plays);
        System.out.println("answer = " + Arrays.toString(answer));
    }
}