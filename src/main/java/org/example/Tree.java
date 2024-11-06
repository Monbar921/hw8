package org.example;

import lombok.Getter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tree<T extends Comparable<T>>{
    Node<T> root;

    public Tree() {
    }


    public void add(T key) {
        root = insert(root, key);
    }


    public void remove(T key) {
        root = deleteNode(root, key);
    }

    private Node<T> deleteNode(Node<T> cur, T key) {
        if (cur == null)
            return cur;

        if (key.compareTo(cur.key) < 0)
            cur.left = deleteNode(cur.left, key);
        else if (key.compareTo(cur.key) > 0)
            cur.right = deleteNode(cur.right, key);

            // IF KEY IS AT ROOT

            // If left is NULL
        else if (cur.left == null) {
            Node<T> temp = cur.right;
            cur = temp;  // Make right child as root
        }
        // If Right is NULL
        else if (cur.right == null) {
            Node<T> temp = cur.left;
            cur = temp;  // Make left child as root
        }
        // If key is at root and both left and right are not NULL
        else if (cur.left.priority < cur.right.priority) {
            cur = leftRotation(cur);
            cur.left = deleteNode(cur.left, key);
        } else {
            cur = rightRotation(cur);
            cur.right = deleteNode(cur.right, key);
        }

        return cur;
    }


    private Node<T> searchNode(Node<T> cur, T key) {
        if (cur == null || key.compareTo(cur.key) == 0) {
            return cur;
        }
        if (key.compareTo(cur.key) > 0) {
            return searchNode(cur.right, key);
        }
        return searchNode(cur.left, key);
    }


    private Node<T> insert(Node<T> cur, T key) {
        if (cur == null) {
            return new Node<>(key);
        }
        if (key.compareTo(cur.key) > 0) {
            cur.right = insert(cur.right, key);
            if (cur.right.priority < cur.priority) {
                cur = leftRotation(cur);
            }

        } else {
            cur.left = insert(cur.left, key);
            if (cur.left.priority < cur.priority) {
                cur = rightRotation(cur);
            }

        }
        return cur;
    }

    private Node<T> leftRotation(Node<T> x) {
        Node<T> y = x.right;
        Node<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        return y;
    }

    private Node<T> rightRotation(Node<T> y) {
        Node<T> x = y.left;
        Node<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        return x;
    }

    private Node<T>[] split(T key) {
        return root.split(key);
    }

    public Double getProfit(T price, Integer amount) {
        Node<T>[] node = split(price);
        Node<T> orders = node[1];
        int ordersCount = inorder(orders).size();

        return Math.min(ordersCount * 0.01, amount * 0.01);
    }

    public List<Node<T>> inorder(Node<T> cur) {
        List<Node<T>> res = new ArrayList<>();
        inorder(cur, res);
        return res;
    }


    private void inorder(Node<T> cur, List<Node<T>> res) {
        if (cur != null) {
            inorder(cur.left, res);
            res.add(cur);
            inorder(cur.right, res);
        }
    }

    public Comparable<? extends Comparable<?>> next(T key) {
        Node<T> moreKeys = split(key)[1];
        Node<T> minValue = getMin(moreKeys);
        return minValue == null ? -1 : minValue.key;
    }

    private Node<T> getMin(Node<T> node) {
        while (node != null && node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Getter
    public static class Node<T extends Comparable<T>> {
        static Random RND = new Random();

        T key;
        int priority;
        Node<T> left;
        Node<T> right;

        public Node(T key) {
            this(key, RND.nextInt());
        }

        public Node(T key, int priority) {
            this(key, priority, null, null);
        }

        public Node(T key, int priority, Node<T> left, Node<T> right) {
            this.key = key;
            this.priority = priority;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", priority=" + priority +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }

        public Node<T>[] split(T key) {
            Node<T> tmp = null;

            Node<T>[] res = (Node<T>[]) Array.newInstance(this.getClass(), 2);

            if (this.key.compareTo(key) < 0) {
                if (this.right == null) {
                    res[1] = null;
                } else {
                    Node<T>[] rightSplit = this.right.split(key);
                    res[1] = rightSplit[1];
                    tmp = rightSplit[0];
                }
                res[0] = new Node<>(this.key, this.priority, this.left, tmp);
            } else {
                if (left == null) {
                    res[0] = null;
                } else {
                    Node<T>[] leftSplit = this.left.split(key);
                    res[0] = leftSplit[0];
                    tmp = leftSplit[1];
                }
                res[1] = new Node<>(this.key, this.priority, tmp, this.right);
            }
            return res;
        }
    }
}
