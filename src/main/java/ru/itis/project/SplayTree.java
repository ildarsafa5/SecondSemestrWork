package ru.itis.project;

public class SplayTree {
    private Node root;

    public SplayTree() {}

    public SplayTree(int elem) {this.root = new Node(elem);}

    public void insert(int elem) {
        if (searchWithOutSplay(elem) != null) {
            if (searchWithOutSplay(elem).getValue() == new Node(elem).getValue()) {
                return;
            }
        }
        if (root == null) {
            root = new Node(elem);
            return;
        }
        Node current  = root;
        while (true) {
            if (elem>current.getValue()) {
                if (current.getRight()==null) {
                    current.setRight(new Node(elem));
                    current.getRight().setParent(current);
                    current = current.getRight();
                    break;
                } else {
                    current = current.getRight();
                }
            } else {
                if (current.getLeft()==null) {
                    current.setLeft(new Node(elem));
                    current.getLeft().setParent(current);
                    current = current.getLeft();
                    break;
                } else {
                    current = current.getLeft();
                }
            }
        }
        splay(current);
    }

    public boolean search(int elem) {
        Node current = searchWithOutSplay(elem);
        if (current!=null) {
            splay(current);
            if (current.getValue()!=elem) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public Node searchWithOutSplay(int elem) {
        if (root == null) {
            return null;
        }
        Node current = root;
        while(true) {
            if (current.getValue()==elem) {
                return current;
            }
            if (elem>current.getValue()) {
                if (current.getRight() == null) {
                    return current;
                } else {
                    current = current.getRight();
                }
            } else {
                if (current.getLeft() == null) {
                    return current;
                } else {
                    current = current.getLeft();
                }
            }
        }
    }

    public boolean delete(int elem) {
        Node current = searchWithOutSplay(elem);
        if (current == null || current.getValue() != elem) {
            return false;
        }
        splay(current);
        if (current.getRight() == null && current.getLeft()!=null) {
            current.getLeft().setParent(null);
            Node left = current.getLeft();
            root = left;
            Node max = max(left);
            splay(max);
            root = max;
        } else if (current.getRight() != null && current.getLeft() ==null) {
            current.getRight().setParent(null);
            Node right = current.getRight();
            root = right;
        } else if (current.getRight() == null && current.getLeft() ==null) {
            root = null;
        } else {
            current.getRight().setParent(null);
            current.getLeft().setParent(null);
            Node left = current.getLeft();
            root = left;
            Node right = current.getRight();
            Node max = max(left);
            splay(max);
            max.setRight(right);
            right.setParent(max);
            root = max;
        }
        return true;
    }

    private Node max(Node node) {
        Node current = node;
        while(current.getRight()!=null) {
            current = current.getRight();
        }
        return current;
    }

    public void splay(Node node) {
        if (root.getValue() == node.getValue()) {
            return;
        }
        while(node.getParent().getParent()!=null) {
            if (node.getParent().getLeft() == node) {
                if (node.getParent().getParent().getLeft() == node.getParent()) {
                    zig(node.getParent());
                    zig(node);
                } else {
                    zig(node);
                    zag(node);
                }
            } else {
                if (node.getParent().getParent().getRight() == node.getParent()) {
                    zag(node.getParent());
                    zag(node);
                } else {
                    zag(node);
                    zig(node);
                }
            }
            if (node.getParent() == null) {
                root = node;
                return;
            }
        }
        if (node.getParent().getRight() == node) {
            zag(node);
            root = node;
        } else {
            zig(node);
            root = node;
        }
    }

    public Node getRoot() {
        return root;
    }

    private void zig(Node x) {
        Node t2 = x.getRight();
        Node y = x.getParent();
        if (y.getParent()!=null) {
            Node ded = y.getParent();
            x.setRight(y);
            y.setParent(x);
            y.setLeft(t2);
            if (t2!=null) {
                t2.setParent(y);
            }
            if (ded.getLeft() == y) {
                ded.setLeft(x);
            } else {
                ded.setRight(x);
            }
            x.setParent(ded);
        } else {
            x.setRight(y);
            y.setParent(x);
            y.setLeft(t2);
            x.setParent(null);
            if (t2!= null) {
                t2.setParent(y);
            }
        }
    }

    private void zag(Node y) {
        Node t2 = y.getLeft();
        Node x = y.getParent();
        if (x.getParent() != null) {
            Node ded = x.getParent();
            y.setLeft(x);
            x.setParent(y);
            x.setRight(t2);
            if (t2!=null) {
                t2.setParent(x);
            }
            if (ded.getLeft() == x) {
                ded.setLeft(y);
            } else {
                ded.setRight(y);
            }
            y.setParent(ded);
        } else {
            y.setLeft(x);
            x.setParent(y);
            x.setRight(t2);
            y.setParent(null);
            if (t2!=null) {
                t2.setParent(x);
            }
        }
    }
}
