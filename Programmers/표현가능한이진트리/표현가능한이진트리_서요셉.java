package Programmers.LV3.표현_가능한_이진트리;
import java.util.*;

public class 표현가능한이진트리 {

    class Solution {
        public int[] solution(long[] numbers) {
            List<Integer> answer = new ArrayList<>();

            for (long number : numbers) {
                if (isBinaryTree(number)) {
                    answer.add(1);
                } else {
                    answer.add(0);
                }
            }

            return answer.stream().mapToInt(Integer::intValue).toArray();
        }

        boolean isBinaryTree(long number) {
            String binary = Long.toBinaryString(number);
            String fullBinary = getFullBinary(binary);
            int len = fullBinary.length();

            int root = len / 2;
            String leftSubTree = fullBinary.substring(0, root);
            String rightSubTree = fullBinary.substring(root + 1);

            if (fullBinary.charAt(root) == '0') return false;

            return isBinaryTree(leftSubTree) && isBinaryTree(rightSubTree);
        }

        String getFullBinary(String binary) {
            int len = binary.length();
            int nodeCnt = 1;
            int level = 1;

            while (len > nodeCnt) {
                level *= 2;
                nodeCnt += level;
            }

            int zeroCnt = nodeCnt - len;
            return "0".repeat(zeroCnt) + binary;
        }

        boolean isBinaryTree(String binary) {
            int len = binary.length();
            if (len == 0) return true;

            int root = len / 2;
            String leftSubTree = binary.substring(0, root);
            String rightSubTree = binary.substring(root + 1);

            if (binary.charAt(root) == '0') return isZeroTree(leftSubTree) && isZeroTree(rightSubTree);

            return isBinaryTree(leftSubTree) && isBinaryTree(rightSubTree);
        }

        boolean isZeroTree(String binary) {
            int len = binary.length();
            if (len == 0) return true;

            int root = len / 2;
            String leftSubTree = binary.substring(0, root);
            String rightSubTree = binary.substring(root + 1);

            if (binary.charAt(root) == '1') return false;

            return isZeroTree(leftSubTree) && isZeroTree(rightSubTree);
        }
    }
}
