package com.example.habitstracker;

import java.util.Stack;

/**
 * Сервис, который хранит в себе все задачи на удаление сущностей из базы данных
 */
public class CleanerService {
    private static final Stack<Lambda> stack = new Stack<>();

    public static void addTask(Lambda delete) {
        stack.push(delete);
    }

    public static void clean() {
        while (!stack.isEmpty()) {
            stack.pop().act();
        }
    }
}
