package com.project1;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Hello world!
 *
 */
public class Tree
{
    static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        TreeNode(int value) {
            this.value = value;
        }

        TreeNode(int value, TreeNode left, TreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * inserts an integer into the tree in BST order. do nothing if the value is 
     * already in the tree.
     * @param root node representing the root of a BST
     * @param value value to be inserted
     * @return root of tree with value inserted
     */
    public static TreeNode insert(TreeNode root, int value) {
        if (root == null) {
            return new TreeNode(value);
        }
        if (value < root.value) {
            root.left = insert(root.left, value);
        } else if (value > root.value) {
            root.right = insert(root.right, value);
        }
        return root;
    }

    /**
     * Creates balanced BST from a list of integers. A BST is balanced if the left
     * and right subtrees differ in height by at most one, the left subtree is balanced, 
     * and the right subtree is balanced.
     * @param list list (not necessarily sorted) of integers
     * @return root of balanced BST from the values
     */
    public static TreeNode makeBalancedBST(List<Integer> list) {
        if (list.isEmpty()) {
            return null;
        }

        list.sort(Integer::compareTo);
        return execMakeBalancedBST(list);
    }

    public static TreeNode execMakeBalancedBST(List<Integer> list) {
        if (list.isEmpty()) {
            return null;
        }
        int mid = Math.floorDiv(list.size(), 2);
        TreeNode root = new TreeNode(list.get(mid));
        root.left = execMakeBalancedBST(list.subList(0, mid));
        root.right = execMakeBalancedBST(list.subList(mid + 1, list.size()));
        return root;
    }

    /**
     * @param root of some BST (could be balanced or unbalanced)
     * @return root of the balanced BST with the same values
     */
    public static TreeNode balanceBST(TreeNode root) {
        if (root == null) {
            return null;
        }
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> nodeList = new ArrayList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            nodeList.add(node.value);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        return makeBalancedBST(nodeList);
    }
}
