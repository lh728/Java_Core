package com_second.Input_and_output;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This program tests regular expression matching. Enter a pattern and string to math,
 * or hit Cancel to exit. If the pattern contaions groups, the group boundaries are displayed
 * in the match.
 *
 */
public class RegexTest {
    public static void main(String[] args) {
        var in = new Scanner(System.in);
        System.out.println("Enter pattern: ");
        String patternString = in.nextLine();

        Pattern pattern = Pattern.compile(patternString);

        while (true){
            System.out.println(" Enter a string to match: ");
            String input = in.nextLine();
            if (input == null || input.equals("")) return;
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()){
                System.out.println("Match");
                int g = matcher.groupCount();
                if (g > 0){
                    for (int i = 0;i < input.length();i ++){
                        //print any empty group
                        for (int j = 1;j <= g;j++)
                            if (i == matcher.start(j) && i == matcher.end(j))
                                System.out.print("()");
                        // print ( for non-empty groups starting here
                        for (int j = 1;j <= g;j++)
                            if (i == matcher.start(j) && i != matcher.end(j))
                                System.out.print('(');
                        System.out.print(input.charAt(i));
                        //print ) for non-empty groups ending here
                        for (int j = 1;j <= g;j++)
                            if (i+1 != matcher.start(j) && i+1 == matcher.end(j))
                                System.out.print(')');
                    }
                    System.out.println();
                }
            }
            else
                System.out.println(" no match");
        }
    }

}
