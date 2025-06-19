package com.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class TreeNode {
    int data, height = 1;
    TreeNode left, right;
    public TreeNode(int data) { this.data = data; }
}

class RBNode {
    int data;
    RBNode left, right, parent;
    boolean isRed = true;
    public RBNode(int data) { this.data = data; }
}

public class TreeMenu {
    private static final int[] ELEMENTS = {7, 5, 9, 4, 6, 8, 13, 2};
    private static TreeNode bstRoot = null, avlRoot = null;
    private static RBNode rbRoot = null;
    private static final int B_TREE_ORDER = 2;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n===== Tree Implementation Menu =====");
            System.out.println("1. Binary Search Tree (Insert and Display)");
            System.out.println("2. AVL Tree (Insert and Display)");
            System.out.println("3. Red-Black Tree (Insert and Display)");
            System.out.println("4. B-Tree (Insert and Search)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1: handleBST(); break;
                case 2: handleAVL(); break;
                case 3: handleRBTree(); break;
                case 4: handleBTree(); break;
                case 5: System.out.println("Exiting program..."); break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
        scanner.close();
    }
    
    private static void handleBST() {
        System.out.println("\n=== Binary Search Tree ===");
        bstRoot = null;
        for (int num : ELEMENTS) bstRoot = bstInsert(bstRoot, num);
        bstRoot = bstInsert(bstRoot, 3);
        System.out.println("BST Created with elements: 7, 5, 9, 4, 6, 8, 13, 2, 3");
        System.out.println("\nBST Structure:");
        printTree(bstRoot);
        System.out.println("\nPostorder Traversal:");
        postOrder(bstRoot);
        System.out.println();
    }
    
    private static TreeNode bstInsert(TreeNode root, int data) {
        if (root == null) return new TreeNode(data);
        if (data < root.data) root.left = bstInsert(root.left, data);
        else if (data > root.data) root.right = bstInsert(root.right, data);
        return root;
    }
    
    private static void handleAVL() {
        System.out.println("\n=== AVL Tree ===");
        avlRoot = null;
        for (int num : ELEMENTS) avlRoot = avlInsert(avlRoot, num);
        avlRoot = avlInsert(avlRoot, 3);
        System.out.println("AVL Tree Created with elements: 7, 5, 9, 4, 6, 8, 13, 2, 3");
        System.out.println("\nAVL Tree Structure:");
        printTree(avlRoot);
    }
    
    private static int height(TreeNode node) { return node == null ? 0 : node.height; }
    
    private static TreeNode rightRotate(TreeNode y) {
        TreeNode x = y.left, T2 = x.right;
        x.right = y; y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }
    
    private static TreeNode leftRotate(TreeNode x) {
        TreeNode y = x.right, T2 = y.left;
        y.left = x; x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }
    
    private static TreeNode avlInsert(TreeNode node, int data) {
        if (node == null) return new TreeNode(data);
        if (data < node.data) node.left = avlInsert(node.left, data);
        else if (data > node.data) node.right = avlInsert(node.right, data);
        else return node;
        
        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = height(node.left) - height(node.right);
        
        if (balance > 1 && data < node.left.data) return rightRotate(node);
        if (balance < -1 && data > node.right.data) return leftRotate(node);
        if (balance > 1 && data > node.left.data) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && data < node.right.data) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }
    
    private static void handleRBTree() {
        System.out.println("\n=== Red-Black Tree ===");
        rbRoot = null;
        for (int num : ELEMENTS) rbInsert(num);
        rbInsert(3);
        System.out.println("Red-Black Tree Created with elements: 7, 5, 9, 4, 6, 8, 13, 2, 3");
        System.out.println("\nRed-Black Tree Structure (R=Red, B=Black):");
        printRBTree(rbRoot);
    }
    
    private static void rbInsert(int data) {
        RBNode newNode = new RBNode(data);
        if (rbRoot == null) { rbRoot = newNode; rbRoot.isRed = false; return; }
        
        RBNode current = rbRoot, parent = null;
        while (current != null) {
            parent = current;
            if (data < current.data) current = current.left;
            else if (data > current.data) current = current.right;
            else return;
        }
        
        newNode.parent = parent;
        if (data < parent.data) parent.left = newNode;
        else parent.right = newNode;
        fixRBTree(newNode);
    }
    
    private static void fixRBTree(RBNode node) {
        while (node != rbRoot && node.isRed && node.parent != null && node.parent.isRed) {
            RBNode parent = node.parent, grandParent = parent.parent;
            if (grandParent == null) break;
            
            if (parent == grandParent.left) {
                RBNode uncle = grandParent.right;
                if (uncle != null && uncle.isRed) {
                    grandParent.isRed = true;
                    parent.isRed = uncle.isRed = false;
                    node = grandParent;
                } else {
                    if (node == parent.right) {
                        leftRotateRB(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    rightRotateRB(grandParent);
                    boolean temp = parent.isRed;
                    parent.isRed = grandParent.isRed;
                    grandParent.isRed = temp;
                    node = parent;
                }
            } else {
                RBNode uncle = grandParent.left;
                if (uncle != null && uncle.isRed) {
                    grandParent.isRed = true;
                    parent.isRed = uncle.isRed = false;
                    node = grandParent;
                } else {
                    if (node == parent.left) {
                        rightRotateRB(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    leftRotateRB(grandParent);
                    boolean temp = parent.isRed;
                    parent.isRed = grandParent.isRed;
                    grandParent.isRed = temp;
                    node = parent;
                }
            }
        }
        rbRoot.isRed = false;
    }
    
    private static void leftRotateRB(RBNode x) {
        RBNode y = x.right;
        x.right = y.left;
        if (y.left != null) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null) rbRoot = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x;
        x.parent = y;
    }
    
    private static void rightRotateRB(RBNode y) {
        RBNode x = y.left;
        y.left = x.right;
        if (x.right != null) x.right.parent = y;
        x.parent = y.parent;
        if (y.parent == null) rbRoot = x;
        else if (y == y.parent.left) y.parent.left = x;
        else y.parent.right = x;
        x.right = y;
        y.parent = x;
    }
    
    private static void handleBTree() {
        System.out.println("\n=== B-Tree ===");
        BTree bTree = new BTree(B_TREE_ORDER);
        for (int num : ELEMENTS) bTree.insert(num);
        bTree.insert(3);
        System.out.println("B-Tree Created with elements: 7, 5, 9, 4, 6, 8, 13, 2, 3");
        System.out.println("\nB-Tree Structure:");
        bTree.traverse();
        System.out.println("\nSearching for key 8:");
        System.out.println(bTree.search(8) ? "Key 8 found in B-Tree" : "Key 8 not found");
    }
    
    private static void printTree(TreeNode root) { printTreeHelper(root, 0); }
    
    private static void printTreeHelper(TreeNode node, int level) {
        if (node == null) return;
        printTreeHelper(node.right, level + 1);
        for (int i = 0; i < level; i++) System.out.print("    ");
        System.out.println(node.data + " (h:" + node.height + ")");
        printTreeHelper(node.left, level + 1);
    }
    
    private static void printRBTree(RBNode root) { printRBTreeHelper(root, 0); }
    
    private static void printRBTreeHelper(RBNode node, int level) {
        if (node == null) return;
        printRBTreeHelper(node.right, level + 1);
        for (int i = 0; i < level; i++) System.out.print("    ");
        System.out.println(node.data + (node.isRed ? "R" : "B"));
        printRBTreeHelper(node.left, level + 1);
    }
    
    private static void postOrder(TreeNode root) {
        if (root == null) return;
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.data + " ");
    }
    
    static class BTree {
        private BTreeNode root;
        private final int t;
        
        static class BTreeNode {
            List<Integer> keys = new ArrayList<>();
            List<BTreeNode> children = new ArrayList<>();
            boolean isLeaf;
            int t;
            public BTreeNode(int t, boolean isLeaf) {
                this.t = t;
                this.isLeaf = isLeaf;
            }
        }
        
        public BTree(int t) {
            this.t = t;
            root = new BTreeNode(t, true);
        }
        
        public boolean search(int key) { return search(root, key); }
        
        private boolean search(BTreeNode node, int key) {
            int i = 0;
            while (i < node.keys.size() && key > node.keys.get(i)) i++;
            if (i < node.keys.size() && key == node.keys.get(i)) return true;
            return !node.isLeaf && search(node.children.get(i), key);
        }
        
        public void traverse() { traverse(root); }
        
        private void traverse(BTreeNode node) {
            int i;
            for (i = 0; i < node.keys.size(); i++) {
                if (!node.isLeaf) traverse(node.children.get(i));
                System.out.print(node.keys.get(i) + " ");
            }
            if (!node.isLeaf) traverse(node.children.get(i));
        }
        
        public void insert(int key) {
            if (root.keys.isEmpty()) { root.keys.add(key); return; }
            
            if (root.keys.size() == 2 * t - 1) {
                BTreeNode newRoot = new BTreeNode(t, false);
                newRoot.children.add(root);
                splitChild(newRoot, 0);
                root = newRoot;
                insertNonFull(root, key);
            } else insertNonFull(root, key);
        }
        
        private void splitChild(BTreeNode x, int i) {
            BTreeNode y = x.children.get(i);
            BTreeNode z = new BTreeNode(t, y.isLeaf);
            
            for (int j = 0; j < t - 1; j++) z.keys.add(y.keys.get(j + t));
            if (!y.isLeaf) for (int j = 0; j < t; j++) z.children.add(y.children.get(j + t));
            
            for (int j = y.keys.size() - 1; j >= t; j--) y.keys.remove(j);
            if (!y.isLeaf) for (int j = y.children.size() - 1; j >= t; j--) y.children.remove(j);
            
            x.children.add(i + 1, z);
            x.keys.add(i, y.keys.get(t - 1));
            y.keys.remove(t - 1);
        }
        
        private void insertNonFull(BTreeNode node, int key) {
            int i = node.keys.size() - 1;
            
            if (node.isLeaf) {
                while (i >= 0 && key < node.keys.get(i)) i--;
                node.keys.add(i + 1, key);
            } else {
                while (i >= 0 && key < node.keys.get(i)) i--;
                i++;
                
                if (node.children.get(i).keys.size() == 2 * t - 1) {
                    splitChild(node, i);
                    if (key > node.keys.get(i)) i++;
                }
                insertNonFull(node.children.get(i), key);
            }
        }
    }
}