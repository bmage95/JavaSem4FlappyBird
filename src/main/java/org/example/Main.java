package org.example;
// libs, codes and packages needed:
// java swing and awt or flutter

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract class InitAll {
    int x,y,height,width;

}

class Bird extends InitAll{

}

public class Main {
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");

        // Press Shift+F10 or click the green arrow button in the gutter to run the code.
        for (int i = 1; i <= 5; i++) {

            // Press Shift+F9 to start debugging your code. We have set one breakpoint
            // for you, but you can always add more by pressing Ctrl+F8.
            System.out.println("i = " + i);
        }
    }
}