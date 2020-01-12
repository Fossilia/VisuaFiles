/**class used to get all sorts of input.*/

package com.fossilia.visuafiles.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class Input {

    public static final Scanner sc = new Scanner(System.in);

    /**gets an integer from the user between min and max (inclusive), checks for exceptions*/
    public static int getIntInput(int min, int max){
        int input = 0;
        boolean done = false;

        while(!done){
            try{
                input = sc.nextInt(); //gets input, may have an exception if user enters a string
                if(input>=min && input<=max){ //checks if its in the range
                    done = true;
                    sc.nextLine();
                    return input;
                }
                else{
                    System.out.println("Type in a valid number between "+min+" and "+max);
                }
            }
            catch(InputMismatchException e){ //catches case user types in a string
                sc.nextLine();
                System.out.println("Please type in a valid number.");
            }
        }
        return 0;
    }

    /**gets a string from the user, checks for exceptions*/
    public static String getStringInput(){
        String input = "";
        boolean done = false;

        while(!done){
            try{
                input = sc.nextLine(); //gets input, may have an exception if user does not type in a string
                if(input == ""){ //checks for empty string
                    System.out.println("You have to type in something!");
                    continue;
                }
                //sc.nextLine();
                return input;
            }
            catch(InputMismatchException e){ //in case user does not type in a string
                sc.nextLine();
                System.out.println("Please type in a valid string.");
            }
        }
        return "";
    }

}
