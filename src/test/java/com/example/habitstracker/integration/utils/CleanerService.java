package com.example.habitstracker.integration.utils;

import java.util.Stack;

/**
 * Сервис, который хранит в себе все задачи на удаление сущностей из базы данных
 */
public class CleanerService {
    private static final Stack<Runnable> stack = new Stack<>();

    public static void addTask(Runnable delete) {
        stack.push(delete);
    }

    public static void clean() {
        while (!stack.isEmpty()) {
            stack.pop().run();
        }
    }
}
