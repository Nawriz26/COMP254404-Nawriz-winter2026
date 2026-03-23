/*
 ** File:            Exercise1Test.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      ab Assignment #5 – Using Trees and Priority Queues
 ** Date:            March 22, 2026
 ** Description:     Tests Exercise 1: inorderNext(p)
 */

package exercise1;

public class Exercise1Test {
    public static void main(String[] args) {
        Exercise1BinaryTree<String> tree = new Exercise1BinaryTree<>();

        Position<String> a = tree.addRoot("A");
        Position<String> b = tree.addLeft(a, "B");
        Position<String> c = tree.addRight(a, "C");
        Position<String> d = tree.addLeft(b, "D");
        Position<String> e = tree.addRight(b, "E");
        Position<String> f = tree.addLeft(c, "F");
        Position<String> g = tree.addRight(c, "G");

        System.out.println("================ EXERCISE 1 ================");
        System.out.println("Testing inorderNext(p)");
        tree.printInorder();
        System.out.println("Expected inorder order: D B E A F C G");
        System.out.println();

        printNext(tree, d);
        printNext(tree, b);
        printNext(tree, e);
        printNext(tree, a);
        printNext(tree, f);
        printNext(tree, c);
        printNext(tree, g);
    }
    private static void printNext(Exercise1BinaryTree<String> tree, Position<String> p) {
        Position<String> next = tree.inorderNext(p);
        String nextValue = (next == null) ? "null" : next.getElement();
        System.out.println("inorderNext(" + p.getElement() + ") =  " + nextValue);
    }
}
