package com.ljt.study.algorithm.structure;

/**
 * 子树：每个节点都可以作为子树的根，它和它所有的子节点、子节点的子节点等都包含在子树中。
 * <p>
 * 二叉树：树的每个节点最多只能有两个子节点。
 * 二叉搜索树：若它的左子树不空，则左子树上所有结点的值均小于它的根结点的值； 若它的右子树不空，则右子树上所有结点的值均大于它的根结点的值； 它的左、右子树也分别为二叉搜索树。
 *
 * @author LiJingTang
 * @date 2019-12-29 21:20
 */
public class BinarySearchTree<E extends Comparable<E>> implements Tree<E> {

    private Node<E> root;

    @Override
    public boolean add(E e) {
        Node<E> node = new Node<>(e);

        if (root == null) { // 当前树为空树，没有任何节点
            root = node;
        } else {
            Node<E> current = root;
            Node<E> parent = null;

            while (true) {
                parent = current;
                int result = current.item.compareTo(e);

                if (result > 0) { // 当前值比插入值大，搜索左子节点
                    current = current.left;

                    if (current == null) { // 左子节点为空，直接将新值插入到该节点
                        parent.left = node;
                        return true;
                    }
                } else {
                    current = current.right;

                    if (current == null) { // 右子节点为空，直接将新值插入到该节点
                        parent.right = node;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean delete(E e) {
        if (root == null) {
            return false;
        }

        Node<E> current = root;
        Node<E> parent = root;
        boolean isLeft = false;

        while (!current.item.equals(e)) {
            parent = current;
            int result = current.item.compareTo(e);

            if (result > 0) { // 当前值比查找值大，搜索左子树
                isLeft = true;
                current = current.left;
            } else {
                isLeft = false;
                current = current.right;
            }

            if (current == null) {
                return false;
            }
        }

        if (current.left == null && current.right == null) { // 如果当前节点没有子节点
            if (current == root) {
                root = null;
            } else if (isLeft) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (current.left == null && current.right != null) { // 当前节点有一个子节点
            if (current == root) {
                root = current.right;
            } else if (isLeft) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        } else if (current.left != null && current.right == null) {
            if (current == root) {
                root = current.left;
            } else if (isLeft) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        } else { // 当前节点存在两个子节点
            Node<E> successor = getSuccessorNode(current);

            if (current == root) {
                root = successor;
            } else if (isLeft) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }

            successor.left = current.left;
        }

        return true;
    }

    private Node<E> getSuccessorNode(Node<E> node) {
        Node<E> successorParent = node;
        Node<E> successor = node;
        Node<E> current = node.right;

        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }

        if (successor != node.right) { // 后继节点不是删除节点的右子节点，将后继节点替换删除节点
            successorParent.left = successor.right;
            successor.right = node.right;
        }

        return successor;
    }

    @Override
    public E find(E e) {
        Node<E> current = root;

        while (current != null) {
            int result = current.item.compareTo(e);

            if (result > 0) { // 当前值比查找值大，搜索左子树
                current = current.left;
            } else if (result < 0) { // 当前值比查找值小，搜索右子树
                current = current.right;
            } else {
                return current.item;
            }
        }

        return null; // 遍历完整个树没找到，返回null
    }

    @Override
    public E findMin() {
        Node<E> current = root;
        Node<E> min = current;

        while (current != null) {
            min = current;
            current = current.left;
        }

        return min != null ? min.item : null;
    }

    @Override
    public E findMax() {
        Node<E> current = root;
        Node<E> max = current;

        while (current != null) {
            max = current;
            current = current.right;
        }

        return max != null ? max.item : null;
    }

    @Override
    public String midToString() {
        StringBuilder sbuilder = new StringBuilder(this.getClass().getSimpleName() + ":midOrder[");
        midOrder(root, sbuilder);
        if (sbuilder.length() > 1) {
            sbuilder.deleteCharAt(sbuilder.length() - 1);
        }
        sbuilder.append("]");

        return sbuilder.toString();
    }

    private void midOrder(Node<E> node, StringBuilder sbuilder) {
        if (node != null) {
            midOrder(node.left, sbuilder);
            sbuilder.append(node.item.toString()).append(", ");
            midOrder(node.right, sbuilder);
        }
    }

    @Override
    public String preToString() {
        StringBuilder sbuilder = new StringBuilder(this.getClass().getSimpleName() + ":preOrder[");
        preOrder(root, sbuilder);
        if (sbuilder.length() > 1) {
            sbuilder.deleteCharAt(sbuilder.length() - 1);
        }
        sbuilder.append("]");

        return sbuilder.toString();
    }

    private void preOrder(Node<E> node, StringBuilder sbuilder) {
        if (node != null) {
            sbuilder.append(node.item.toString()).append(", ");
            midOrder(node.left, sbuilder);
            midOrder(node.right, sbuilder);
        }
    }

    @Override
    public String postToString() {
        StringBuilder sbuilder = new StringBuilder(this.getClass().getSimpleName() + ":postOrder[");
        postOrder(root, sbuilder);
        if (sbuilder.length() > 1) {
            sbuilder.deleteCharAt(sbuilder.length() - 1);
        }
        sbuilder.append("]");

        return sbuilder.toString();
    }

    private void postOrder(Node<E> node, StringBuilder sbuilder) {
        if (node != null) {
            midOrder(node.left, sbuilder);
            midOrder(node.right, sbuilder);
            sbuilder.append(node.item.toString()).append(", ");
        }
    }


    private static class Node<E> {
        E item;
        Node<E> left;
        Node<E> right;

        public Node(E item) {
            super();
            this.item = item;
        }

    }

}
