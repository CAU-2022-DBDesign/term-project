package cau.dbd.util;

import java.util.Scanner;

public class MyScanner {

    public final static Scanner scanner = new Scanner(System.in);

    /**
     * 김명승
     * min~max 사이의 정수 입력받음. 올바른 형식이 아니면 다시 입력받음.
     */
    public static int getIntInRange(int min, int max) {
        while (true) {
            try {
                int cmd = Integer.parseInt(scanner.nextLine());
                if (cmd > max || cmd < min) {
                    throw new Exception();
                }
                return cmd;
            } catch (Exception e) {
                System.out.printf("[ERROR] Invalid command. Range should be %d~%d%n", min, max);
            }
        }
    }

    public static String getStringInLength(int length) {
        return getStringInLength(length, length);
    }

    /**
     * 김명승
     * min~max 길이 문자열 입력받음. 올바른 형식이 아니면 다시 입력받음.
     */
    public static String getStringInLength(int min, int max) {
        while (true) {
            try {
                String str = scanner.nextLine();
                if (str.length() > max || str.length() < min) {
                    throw new Exception();
                }
                return str;
            } catch (Exception e) {
                System.out.printf("[ERROR] Invalid string. String's length should be %d~%d%n", min, max);
            }
        }
    }
}
