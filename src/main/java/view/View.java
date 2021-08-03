package view;

import java.util.Scanner;

public class View {


    public void printMessage(String message) {
        System.out.println(message);
    }

    public String inputText(String message, boolean stringСanEmpty) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        String result = scanner.nextLine();
        if (result.equals("") && !stringСanEmpty) {
            System.out.println("Строка не может быть пустой. Повторите ввод ещё раз");
            return inputText(message, stringСanEmpty);
        }
        return result;
    }

    public int inputInt(String message) {
        System.out.print(message);
        return inputInt();
    }

    public int inputInt() {
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();
        } catch (Exception ex) {
            messageIncorrectInput();
            return inputInt();
        }
    }

    public void messageIncorrectInput() {
        System.out.println("Введенно некорректное значение. Повторите ввод ещё раз");
    }
}
