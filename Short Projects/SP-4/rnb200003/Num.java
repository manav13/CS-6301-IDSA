// Starter code for SP3.
// Version 1.0 (Fri, Feb 3).

// Change following line to your NetId
package rnb200003;

import java.util.List;
import java.util.Queue;
import java.lang.Object;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.lang.Exception;
public class Num implements Comparable<Num> {

    static long defaultBase = 10; // Change as needed
    long base = defaultBase; // Change as needed
    long[] lngnumber; // array to store arbitrarily large integers
    boolean isNegative; // boolean flag to represent negative numbers
    int len; // actual number of elements of array that are used; number is stored in
             // list[0..len-1]

    private Num() {

    }

    public Num(String s) {
        int str_indx = s.length() - 1;
        int array_index = 0;

        lngnumber = new long[s.length()];
        for (; str_indx >= 0; str_indx -= 1) {
            char ch = s.charAt(str_indx);
            lngnumber[array_index] = Character.getNumericValue(ch);
            array_index += 1;
        }

        this.len = array_index;
    }

    public Num(long number) {
        int array_size = 0;

        if (number == 0) {
            array_size = 1;
        }

        if (number != 0) {
            array_size = (int) Math.floor(Math.log10(Math.abs(number))) + 1;
        }

        int array_index = 0;

        long[] num_arr = new long[array_size];
        long temp = Math.abs(number);

        while (temp != 0) {
            long reminder = temp % 10;
            num_arr[array_index] = reminder;
            array_index += 1;
            temp = temp / 10;

        }

        this.len = array_size;
    }

    public static Num add(Num a, Num b) {
        long[] array_a = a.lngnumber;
        long[] array_b = b.lngnumber;
        String result = "";
        int index = 0;
        long carry = 0;
        int min_array_size = Math.min(array_a.length, array_b.length);

        for (index = 0; index < min_array_size; index += 1) {
            long total = array_a[index] + array_b[index] + carry;
            long added_number = total % 10;

            if (total > 9) {
                carry = total / 10;
            } else {
                carry = 0;
            }

            result += Long.toString(added_number);
        }
        if (index < array_a.length) {
            for (; index < array_a.length; index += 1) {

                long total = array_a[index] + carry;
                long added_number = total % 10;

                if (total > 9) {
                    carry = total / 10;
                } else {
                    carry = 0;
                }

                result += Long.toString(added_number);
            }
        }

        else if (index < array_b.length) {
            for (; index < array_b.length; index += 1) {

                long total = array_b[index] + carry;
                long added_number = total % 10;

                if (total > 9) {
                    carry = total / 10;
                } else {
                    carry = 0;
                }

                result += Long.toString(added_number);
            }
        }

        if (carry == 1) {
            result += Long.toString(carry);
        }

        Num final_result = new Num();
        StringBuffer str = new StringBuffer(result);
        String final_ans = new String(str.reverse());
        String afterleadingzero = Num.trimNum(final_ans);
        if (afterleadingzero.equals("0")) {
            final_result.isNegative = false;
            final_result = new Num(afterleadingzero);
        }

        final_result = new Num(afterleadingzero);
        return final_result;
    }

    public static Num product(Num a, Num b) {
        Num product_result = new Num();

        product_result = product_result.multiplyProduct(a, b);

        return product_result;
    }

    public Num multiplyProduct(Num a, Num b) {
        Num output = new Num();
        long[] array_a = a.lngnumber;
        long[] array_b = b.lngnumber;
        String result = "";

        if ((a.len == 1 && a.lngnumber[0] == 0) || (b.len == 1 && b.lngnumber[0] == 0)) {

            return new Num(0);

        } else {

            int total_length = a.len + b.len;
            long[] output_arr = new long[total_length];
            int index = 0;
            int output_pointer = 1;

            int i = 0;
            int j = 0;
            int carry = 0;

            while (i < array_a.length) {

                while (j < array_b.length) {

                    long product = (array_b[j] * array_a[i]) + carry;
                    output_arr[index] = output_arr[index] + product;
                    index += 1;
                    j += 1;

                }
                index = output_pointer;
                output_pointer += 1;
                i += 1;
                j = 0;
            }
            carry = 0;

            index = 0;
            while (index < total_length) {
                long number_to_be_added = (output_arr[index] + carry) % 10;
                carry = ((int) output_arr[index] + carry) / 10;
                output_arr[index] = number_to_be_added;
                result += Long.toString(number_to_be_added);
                index += 1;
            }
            StringBuffer ans = new StringBuffer(result);
            String final_ans = new String(ans.reverse());
            String afterleadingzero = Num.trimNum(final_ans);
            output = new Num(afterleadingzero);
            return output;

        }
    }

    // Return number to a string in base 10
    public String toString() {
        StringBuilder numsStr = new StringBuilder();

        for (long digit : this.lngnumber) {
            numsStr.append(digit);
        }

        numsStr.reverse();

        return numsStr.toString();
    }

    public static String trimNum(String number) {
        int idx = 0;
        while (idx < number.length() && number.charAt(idx) == '0') {
            idx += 1;
        }
        if (idx < number.length()) {
            return number.substring(idx, number.length());
        }
        return "0";
    }

    // methods below are optional

    public static Num subtract(Num a, Num b) {
        return null;
    }

    // Use divide and conquer
    public static Num power(Num a, long n) {
        return null;
    }

    // Use divide and conquer or division algorithm to calculate a/b
    public static Num divide(Num a, Num b) {
        return null;
    }

    public static Num subtractNums(Num a, Num b) {
        return null;
    }

    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1
    // otherwise
    public int compareTo(Num other) {
        return 0;
    }

    public static Num shiftleft(Num x, int n) {
        return null;
    }

    // Output using the format "base: elements of list ..."
    public void printList() {
        System.out.print(defaultBase + " : ");
        for (long num : lngnumber) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public long base() {
        return base;
    }

    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {
        return null;
    }

    // Divide by 2, for using in binary search
    public Num by2() {
        return null;
    }
    
    public static Num evaluateExp(String expr) throws Exception{
        
        Queue<String> experList = new LinkedList<>();
        StringBuilder strBuilder = new StringBuilder();
        expr = expr.trim();
        String[] Operators = { "(", ")", "*", "+", "/", "-" };

        for (int i = 0; i < expr.length(); i++) {
            if (Arrays.stream(Operators).anyMatch(String.valueOf(expr.charAt(i))::equals)) {
                if(String.valueOf(expr.charAt(i)) == "-" || String.valueOf(expr.charAt(i)) == "/"){
                    throw new Exception("Invalid operator");
                }

                if (strBuilder.length() > 0) {
                    experList.add(strBuilder.toString());
                    strBuilder = new StringBuilder();
                }

                experList.add(expr.charAt(i) + "");
            }

            else if (expr.charAt(i) == ' ') {
                continue;
            }

            else {
                strBuilder.append(expr.charAt(i));
            }
        }

        if (!strBuilder.toString().equals("")) {
            experList.add(strBuilder.toString());
        }
        return evaluateAdd(experList);
    }

    public static Num evaluateAdd(Queue<String> questr) {
        Num value1 = evaluateMul(questr);

        if (questr.peek() != null) {
            while (questr.peek().equals("+")) {
                String operate = questr.remove();
                Num value2 = evaluateMul(questr);
                if (operate.equals("+")) {
                    value1 = Num.add(value1, value2);
                }

                if (questr.peek() == null) {
                    break;

                }
            }
        }

        return value1;
    }

    public static Num evaluateMul(Queue<String> questr){
        Num value1 = evaluateBr(questr);

        if (questr.peek() != null) {
            while (questr.peek().equals("*")) {
                String operate = questr.remove();
                Num value2 = evaluateBr(questr);
                if (operate.equals("*")) {
                    value1 = Num.product(value1, value2);
                }

                if (questr.peek() == null) {
                    break;
                }
            }
        }

        return value1;
    }

    public static Num evaluateBr(Queue<String> questr) {
        Num finalval = null;
        if (questr.peek().equals("(")) {
            questr.remove();
            finalval = evaluateAdd(questr);
            questr.remove();
        }

        else {
            String number = questr.remove();
            finalval = new Num(number);
        }

        return finalval;
    }

    public static void main(String[] args) throws Exception{
        Num x = new Num("8888888888888");
        Num y = new Num("2");
        Num z = Num.add(x, y);
        System.out.println(z);
        Num a = Num.product(x, y);
        System.out.println(a);
        if (a != null)
            a.printList();
            System.out.println(Num.evaluateExp(
                "(928482938293848293882932939238472839239+18238182828391921838383919283828193)*((9182873818182364616177128737817838723818237123718*162371721737381817827282828281717)*1912831827337373763758593838382928393939)+192839839393838338399191929293839923993"));
    }
}