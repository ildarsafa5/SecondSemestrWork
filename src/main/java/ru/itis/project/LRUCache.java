package ru.itis.project;

import java.util.LinkedList;

// Реализовать структуру данных, которая хранит N последних уникальных чисел,
// но при переполнении удаляет число, к которому дольше всего не обращались.

public class LRUCache {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);

        cache.access(10); // [10]
        cache.access(20); // [20, 10]
        // Самый старый обработанный - 10
        System.out.println(cache.getLeastRecentlyUsed());
        // Выходим за размер кэша -> удаляем самый старый(10)
        cache.access(30); // [30, 20]

        cache.printCache(); // Теперь кэш: [30, 20]

        // Снова выходим за размер кэша -> удаляем самый старый(20)
        cache.access(40); // [40, 30]
        cache.printCache(); // LRU order: [40, 30, 20]
    }
    private final SplayTree splayTree;
    private final LinkedList<Integer> lruOrder; // Последний использованный — в начале
    private final int maxSize;

    public LRUCache(int maxSize) {
        this.splayTree = new SplayTree();
        this.lruOrder = new LinkedList<>();
        this.maxSize = maxSize;
    }

    // Добавляет число в кэш или обновляет его статус
    public void access(int x) {
        if (splayTree.searchWithOutSplay(x)!=null && splayTree.searchWithOutSplay(x).getValue() == new Node(x).getValue()) {
            // Число уже есть — перемещаем его в начало списка
            lruOrder.removeFirstOccurrence(x);
            lruOrder.addFirst(x);
            // SplayTree автоматически "поднимет" x при поиске
            splayTree.search(x);
        } else {
            // Новое число — добавляем в кэш
            splayTree.insert(x);
            lruOrder.addFirst(x);

            // Если кэш переполнен, удаляем самый "старый" элемент
            if (lruOrder.size() > maxSize) {
                int oldest = lruOrder.removeLast();
                splayTree.delete(oldest);
            }
        }
    }

    // Возвращает число, к которому дольше всего не обращались
    public int getLeastRecentlyUsed() {
        return lruOrder.getLast();
    }

    public void printCache() {
        System.out.println("LRU order: " + lruOrder);
    }
}
