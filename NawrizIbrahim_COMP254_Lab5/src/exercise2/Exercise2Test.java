/*
 ** File:            Exercise2Test.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      ab Assignment #5 – Using Trees and Priority Queues
 ** Date:            March 22, 2026
 ** Description:     Tests Exercise 2: print each element with the height of its subtree
 */

package exercise2;

public class Exercise2Test {
    public static void main(String[] args) {
        Exercise2BinaryTree<String> tree = new Exercise2BinaryTree<>();

        Position<String> a = tree.addRoot("A");
        Position<String> b = tree.addRoot("B");
        Position<String> c = tree.addRoot("C");
        tree.addLeft(b, "D");
        tree.addRight(b, "E");
        tree.addLeft(c, "F");
        tree.addRight(c, "G");

        System.out.println("================ EXERCISE 2 ================");
        tree.printElementsWithSubtreeHeights();
        System.out.println();
        System.out.println("Worst-case running time: O(n)");
        System.out.println("Each node is visited exactly once in postorder");
    }
}
