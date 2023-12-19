package Programmers.LV3.베스트앨범;
import java.util.*;

public class 베스트앨범 {

    class Solution {

        Map<String, Genre> genreHM;
        PriorityQueue<Genre> genrePQ;

        public List<Integer> solution(String[] genres, int[] plays) {
            List<Integer> answer = new ArrayList<>();

            genreHM = new HashMap<>();
            genrePQ = new PriorityQueue<>();

            int len = plays.length;

            for (int i = 0; i < len; i++) {
                Song song = new Song(i, plays[i]);

                if (!genreHM.containsKey(genres[i])) {
                    genreHM.put(genres[i], new Genre(genres[i]));
                }

                genreHM.get(genres[i]).songPQ.add(song);
                genreHM.get(genres[i]).play += plays[i];
            }

            for (Genre g : genreHM.values()) {
                genrePQ.add(g);
            }

            while (!genrePQ.isEmpty()) {
                Genre genre = genrePQ.poll();

                int cnt = 0;
                while (!genre.songPQ.isEmpty() && cnt < 2) {
                    answer.add(genre.songPQ.poll().idx);
                    cnt++;
                }

            }

            return answer;
        }

        class Genre implements Comparable<Genre> {
            String name;
            int play;
            PriorityQueue<Song> songPQ;

            public Genre(String name) {
                this.name = name;
                this.play = 0;
                this.songPQ = new PriorityQueue<>();
            }

            @Override
            public int compareTo(Genre g) {
                return g.play - this.play;
            }
        }

        class Song implements Comparable<Song> {
            int idx, play;

            public Song(int idx, int play) {
                this.idx = idx;
                this.play = play;
            }

            @Override
            public int compareTo(Song s) {
                if (s.play == this.play) return this.idx - s.idx;
                return s.play - this.play;
            }
        }
    }
}
