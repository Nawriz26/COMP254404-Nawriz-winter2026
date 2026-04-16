package exercise1;

/*
 ** File:            IterativeTreeMap.java
 ** Student:         Nawriz Ibrahim
 ** Student number:  301161181
 ** Assignment:      Lab Assignment #7 – Exercise 1 – Search Trees and Sorting
 ** Date:            April 15, 2026
 ** Description:     This program implements a binary search tree map using an iterative
 **                  version of the treeSearch() method instead of recursion. The class is
 **                  based on the textbook TreeMap structure, but the search now uses a loop
 **                  so it can avoid deep recursive calls on large unbalanced trees.
 */

import common.AbstractSortedMap;
import common.Entry;
import common.LinkedBinaryTree;
import common.Position;

import java.util.ArrayList;
import java.util.Comparator;

public class IterativeTreeMap<K, V> extends AbstractSortedMap<K, V> {

    /**
     * Specialized linked binary tree used internally by the map.
     */
    protected static class BalanceableBinaryTree<K, V>
            extends LinkedBinaryTree<Entry<K, V>> {

        /**
         * Tree node with an auxiliary integer field.
         */
        protected static class BSTNode<E> extends Node<E> {
            private int aux = 0;

            BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild) {
                super(e, parent, leftChild, rightChild);
            }

            public int getAux() {
                return aux;
            }

            public void setAux(int value) {
                aux = value;
            }
        }

        public int getAux(Position<Entry<K, V>> p) {
            return ((BSTNode<Entry<K, V>>) p).getAux();
        }

        public void setAux(Position<Entry<K, V>> p, int value) {
            ((BSTNode<Entry<K, V>>) p).setAux(value);
        }

        /**
         * Creates a BSTNode instead of a basic tree node.
         */
        @Override
        protected Node<Entry<K, V>> createNode(Entry<K, V> e,
                                               Node<Entry<K, V>> parent,
                                               Node<Entry<K, V>> left,
                                               Node<Entry<K, V>> right) {
            return new BSTNode<>(e, parent, left, right);
        }

        /**
         * Reconnects a parent to one of its children.
         */
        private void relink(Node<Entry<K, V>> parent, Node<Entry<K, V>> child,
                            boolean makeLeftChild) {
            child.setParent(parent);
            if (makeLeftChild) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }

        /**
         * Rotates a node above its parent.
         */
        public void rotate(Position<Entry<K, V>> p) {
            Node<Entry<K, V>> x = validate(p);
            Node<Entry<K, V>> y = x.getParent();
            Node<Entry<K, V>> z = y.getParent();

            if (z == null) {
                root = x;
                x.setParent(null);
            } else {
                relink(z, x, y == z.getLeft());
            }

            if (x == y.getLeft()) {
                relink(y, x.getRight(), true);
                relink(x, y, false);
            } else {
                relink(y, x.getLeft(), false);
                relink(x, y, true);
            }
        }

        /**
         * Performs a trinode restructuring.
         */
        public Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) {
            Position<Entry<K, V>> y = parent(x);
            Position<Entry<K, V>> z = parent(y);

            if ((x == right(y)) == (y == right(z))) {
                rotate(y);
                return y;
            } else {
                rotate(x);
                rotate(x);
                return x;
            }
        }
    }

    /** Underlying tree structure for the map. */
    protected BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<>();

    /** Creates an empty map that uses the natural order of keys. */
    public IterativeTreeMap() {
        super();
        tree.addRoot(null);   // root starts as an external sentinel leaf
    }

    /** Creates an empty map using a custom comparator. */
    public IterativeTreeMap(Comparator<K> comp) {
        super(comp);
        tree.addRoot(null);
    }

    @Override
    public int size() {
        // Internal nodes contain entries; external leaves are sentinels.
        return (tree.size() - 1) / 2;
    }

    /**
     * Replaces an external node with a real entry and adds two sentinel children.
     */
    private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry) {
        tree.set(p, entry);
        tree.addLeft(p, null);
        tree.addRight(p, null);
    }

    // Convenience wrappers for tree operations
    protected Position<Entry<K, V>> root() { return tree.root(); }
    protected Position<Entry<K, V>> parent(Position<Entry<K, V>> p) { return tree.parent(p); }
    protected Position<Entry<K, V>> left(Position<Entry<K, V>> p) { return tree.left(p); }
    protected Position<Entry<K, V>> right(Position<Entry<K, V>> p) { return tree.right(p); }
    protected Position<Entry<K, V>> sibling(Position<Entry<K, V>> p) { return tree.sibling(p); }
    protected boolean isRoot(Position<Entry<K, V>> p) { return tree.isRoot(p); }
    protected boolean isExternal(Position<Entry<K, V>> p) { return tree.isExternal(p); }
    protected boolean isInternal(Position<Entry<K, V>> p) { return tree.isInternal(p); }
    protected void set(Position<Entry<K, V>> p, Entry<K, V> e) { tree.set(p, e); }
    protected Entry<K, V> remove(Position<Entry<K, V>> p) { return tree.remove(p); }
    protected void rotate(Position<Entry<K, V>> p) { tree.rotate(p); }
    protected Position<Entry<K, V>> restructure(Position<Entry<K, V>> x) { return tree.restructure(x); }

    /**
     * Iterative version of treeSearch().
     * Moves down the tree using a loop instead of recursive calls.
     */
    private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
        Position<Entry<K, V>> walk = p;

        // Continue until the key is found or an external leaf is reached.
        while (isInternal(walk)) {
            int comp = compare(key, walk.getElement());

            if (comp == 0) {
                return walk;                 // exact key found
            } else if (comp < 0) {
                walk = left(walk);          // move to left subtree
            } else {
                walk = right(walk);         // move to right subtree
            }
        }

        // If we stop at an external node, the key was not found.
        return walk;
    }

    /** Returns the position with the smallest key in a subtree. */
    protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
        Position<Entry<K, V>> walk = p;
        while (isInternal(walk)) {
            walk = left(walk);
        }
        return parent(walk);
    }

    /** Returns the position with the largest key in a subtree. */
    protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
        Position<Entry<K, V>> walk = p;
        while (isInternal(walk)) {
            walk = right(walk);
        }
        return parent(walk);
    }

    @Override
    public V get(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);
        rebalanceAccess(p);

        if (isExternal(p)) {
            return null;
        }
        return p.getElement().getValue();
    }

    @Override
    public V put(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K, V> newEntry = new MapEntry<>(key, value);
        Position<Entry<K, V>> p = treeSearch(root(), key);

        if (isExternal(p)) {
            // Insert a brand-new key.
            expandExternal(p, newEntry);
            rebalanceInsert(p);
            return null;
        } else {
            // Replace the existing value for this key.
            V old = p.getElement().getValue();
            set(p, newEntry);
            rebalanceAccess(p);
            return old;
        }
    }

    @Override
    public V remove(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);

        if (isExternal(p)) {
            rebalanceAccess(p);
            return null;
        } else {
            V old = p.getElement().getValue();

            // If p has two internal children, swap with predecessor.
            if (isInternal(left(p)) && isInternal(right(p))) {
                Position<Entry<K, V>> replacement = treeMax(left(p));
                set(p, replacement.getElement());
                p = replacement;
            }

            // Now p has at most one internal child.
            Position<Entry<K, V>> leaf = (isExternal(left(p)) ? left(p) : right(p));
            Position<Entry<K, V>> sib = sibling(leaf);
            remove(leaf);
            remove(p);
            rebalanceDelete(sib);
            return old;
        }
    }

    @Override
    public Entry<K, V> firstEntry() {
        if (isEmpty()) {
            return null;
        }
        return treeMin(root()).getElement();
    }

    @Override
    public Entry<K, V> lastEntry() {
        if (isEmpty()) {
            return null;
        }
        return treeMax(root()).getElement();
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);

        if (isInternal(p)) {
            return p.getElement();
        }

        while (!isRoot(p)) {
            if (p == left(parent(p))) {
                return parent(p).getElement();
            } else {
                p = parent(p);
            }
        }
        return null;
    }

    @Override
    public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);

        if (isInternal(p)) {
            return p.getElement();
        }

        while (!isRoot(p)) {
            if (p == right(parent(p))) {
                return parent(p).getElement();
            } else {
                p = parent(p);
            }
        }
        return null;
    }

    @Override
    public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);

        if (isInternal(p) && isInternal(left(p))) {
            return treeMax(left(p)).getElement();
        }

        while (!isRoot(p)) {
            if (p == right(parent(p))) {
                return parent(p).getElement();
            } else {
                p = parent(p);
            }
        }
        return null;
    }

    @Override
    public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
        checkKey(key);
        Position<Entry<K, V>> p = treeSearch(root(), key);

        if (isInternal(p) && isInternal(right(p))) {
            return treeMin(right(p)).getElement();
        }

        while (!isRoot(p)) {
            if (p == left(parent(p))) {
                return parent(p).getElement();
            } else {
                p = parent(p);
            }
        }
        return null;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());

        // Inorder traversal returns entries in sorted key order.
        for (Position<Entry<K, V>> p : tree.inorder()) {
            if (isInternal(p)) {
                buffer.add(p.getElement());
            }
        }
        return buffer;
    }

    @Override
    public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
        checkKey(fromKey);
        checkKey(toKey);
        ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());

        if (compare(fromKey, toKey) < 0) {
            subMapRecurse(fromKey, toKey, root(), buffer);
        }
        return buffer;
    }

    /**
     * Recursively collects entries whose keys lie in [fromKey, toKey).
     */
    private void subMapRecurse(K fromKey, K toKey, Position<Entry<K, V>> p,
                               ArrayList<Entry<K, V>> buffer) {
        if (isInternal(p)) {
            if (compare(p.getElement(), fromKey) < 0) {
                subMapRecurse(fromKey, toKey, right(p), buffer);
            } else {
                subMapRecurse(fromKey, toKey, left(p), buffer);
                if (compare(p.getElement(), toKey) < 0) {
                    buffer.add(p.getElement());
                    subMapRecurse(fromKey, toKey, right(p), buffer);
                }
            }
        }
    }

    /** Hook method for balanced-tree subclasses after insertion. */
    protected void rebalanceInsert(Position<Entry<K, V>> p) { }

    /** Hook method for balanced-tree subclasses after deletion. */
    protected void rebalanceDelete(Position<Entry<K, V>> p) { }

    /** Hook method for balanced-tree subclasses after access. */
    protected void rebalanceAccess(Position<Entry<K, V>> p) { }
}
