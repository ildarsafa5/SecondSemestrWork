package ru.itis.project;

public class SplayTree {
    private Node root;

    public SplayTree() {}

    public SplayTree(int elem) {this.root = new Node(elem);}
    // добавление элемента в дерево
    public void insert(int elem) {
        // если элемент уже есть - не добавляем
        if (searchWithOutSplay(elem) != null) {
            if (searchWithOutSplay(elem).getValue() == new Node(elem).getValue()) {
                return;
            }
        }
        // если в дереве ещё нет элементов
        if (root == null) {
            root = new Node(elem);
            return;
        }
        // вставка элемента в место, определяемое логикой дерева
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
        // поднятие элемента вверх после операции добавления
        splay(current);
    }
    // поиск элемента в дереве
    public boolean search(int elem) {
        Node current = searchWithOutSplay(elem);
        if (current!=null) {
            // splay найденного или того, до которого дошёл поиск, если дерево непустое
            splay(current);
            if (current.getValue()!=elem) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
    // поиск без splay операции
    public Node searchWithOutSplay(int elem) {
        // возвращаем null, если дерево пустое
        if (root == null) {
            return null;
        }
        Node current = root;
        while(true) {
            // если элемент найден возвращаем его узел
            if (current.getValue()==elem) {
                return current;
            }
            if (elem>current.getValue()) {
                // если элемент не найден - возвращаем тот элемент, до которого дошёл поиск
                if (current.getRight() == null) {
                    return current;
                } else {
                    current = current.getRight();
                }
            } else {
                // если элемент не найден - возвращаем тот элемент, до которого дошёл поиск
                if (current.getLeft() == null) {
                    return current;
                } else {
                    current = current.getLeft();
                }
            }
        }
    }
    // удаление элемента
    public boolean delete(int elem) {
        // выход, если элемент не найден
        Node current = searchWithOutSplay(elem);
        if (current == null || current.getValue() != elem) {
            return false;
        }
        // поднятие элемента в корень дерева
        splay(current);
        // разделение дерева на два дерева и дальнейшее их слияние
        if (current.getRight() == null && current.getLeft()!=null) {
            current.getLeft().setParent(null);
            Node left = current.getLeft();
            root = left;
            // поднятие максимального элемента в левом дереве в корень
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
            // поднятие максимального элемента в левом дереве в корень
            Node max = max(left);
            splay(max);
            max.setRight(right);
            right.setParent(max);
            root = max;
        }
        return true;
    }
    // поиск максимального элемента в дереве
    private Node max(Node node) {
        Node current = node;
        while(current.getRight()!=null) {
            current = current.getRight();
        }
        return current;
    }
    // операция поднятие элемента в корень с помощью поворотов
    public void splay(Node node) {
        // если элемент уже является корнем - выходим
        if (root.getValue() == node.getValue()) {
            return;
        }
        while(node.getParent().getParent()!=null) {
            if (node.getParent().getLeft() == node) {
                // операция Zig-Zig
                if (node.getParent().getParent().getLeft() == node.getParent()) {
                    zig(node.getParent());
                    zig(node);
                // операция Zig-Zag
                } else {
                    zig(node);
                    zag(node);
                }
            } else {
                // операция Zag-Zag
                if (node.getParent().getParent().getRight() == node.getParent()) {
                    zag(node.getParent());
                    zag(node);
                // операция Zag-Zig
                } else {
                    zag(node);
                    zig(node);
                }
            }
            // если элемент уже стал корнем, закрывающая операция Zig/Zag уже не нужны
            if (node.getParent() == null) {
                root = node;
                return;
            }
        }
        // операция Zag
        if (node.getParent().getRight() == node) {
            zag(node);
            root = node;
        // операция Zig
        } else {
            zig(node);
            root = node;
        }
    }

    // операция правого поворота
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
    // операция левого поворота
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
