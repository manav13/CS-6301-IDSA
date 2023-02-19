// Starter code for SP3.
// Version 1.0 (Fri, Feb 3).

// Change following line to your NetId
package rnb200003;
import java.util.List;
import java.util.ArrayList;

public class Num  implements Comparable<Num> {

    static long defaultBase = 10;  // Change as needed
    long base = defaultBase;  // Change as needed
    ArrayList<Long> lngnumber;  // array to store arbitrarily large integers
    boolean isNegative;  // boolean flag to represent negative numbers
    int len;  // actual number of elements of array that are used;  number is stored in list[0..len-1]

    public Num(String s) {
        lngnumber = new ArrayList<>();
        String strNumber;

        strNumber = new StringBuilder(s).reverse().toString();
        String[] charStrings = strNumber.split("");

        for(String str : charStrings){
            lngnumber.add(Long.parseLong(str));
        }
    }

    public Num(ArrayList<Long> number){
        lngnumber = number;
    }

    public static Num addTwoNumbers(Num a, Num b){
        int a_length =  a.lngnumber.size();
        int b_length = b.lngnumber.size();
        long sum_total;
        long Base = a.base;
        long carry = 0;
        int i;
        ArrayList<Long> add_sum = new ArrayList<>(a_length);

        for(i = 0; i < b_length; i++){
            sum_total = a.lngnumber.get(i) + b.lngnumber.get(i) + carry;
            carry = sum_total >= Base ? 1 : 0;
            add_sum.add(i, sum_total - carry * Base);
        }

        while(i < a_length){
            sum_total = a.lngnumber.get(i) + carry;
            carry = sum_total >= Base ? 1 : 0;
            add_sum.add(i++, sum-carry * Base);
        }

        if(carry > 0) r.add(carry);
        StringBuilder stringNum = new StringBuilder();

        for(long nums : add_sum){
            stringNum.append(nums);
        }

        Num final_result = new Num(stringNum.reverse().toString());

        return final_result;
    }
    public static Num addNums(Num a, Num b){
        if(a.lngnumber.size() > b.lngnumber.size()){
            return addTwoNumbers(a, b);
        }

        return addTwoNumbers(b, a);
    }
    public static Num add(Num a, Num b) {
        return addNums(a,b);
    }


    public static Num product(Num a, Num b) {
        if(b.lngnumber.size() == 1 && b.lngnumber.get(0) == 0) return 0;
        if(b.lngnumber.size() == 1 && b.lngnumber.get(0) == 1) return a;
        return Karatsuba(a, b);
    }

    public static Num Karatsuba(Num x, Num y){
        int num_min_size = Math.min(x.lngnumber.size(), y.lngnumber.size());

        if(n <= 20) return Multi_Nums(x,y);

        n = (int) Math.ceil(num_min_size / 2);

        Num a = new Num(new ArrayList<>(x.lngnumber.subList(0, n)));
        Num b = new Num(new ArrayList<>(x.lngnumber.subList(n, x.number.size())));
        Num c = new Num(new ArrayList<>(y.lngnumber.subList(0, n)));
        Num d = new Num(new ArrayList<>(y.lngnumber.subList(n, x.number.size())));

        Num ac = Karatsuba(a, c);
        Num bd = Karatsuba(b, d);
        Num abcd = Karatsuba(add(a, b), add(c, d));

        Num result = add(add(ac, shiftLeft(subtract(subtract(abcd, ac), bd), n)), shiftLeft(bd, 2 * n));
        trimLongNum(result);
        return result;
    }
	
    public static Num Multi_Nums(Num x, Num y){
        int a_length = x.lngnumber.size();
        int b_length = y.lngnumber.size();

        int total_length = a_length + b_length;
        long[] result = new long[total_length];
        Object[] a = x.lngnumber.toArray();
        Object[] b = y.lngnumber.toArray();
        long Base = x.base;
        int i;
        long a_ln, b_ln, final_product;

        for(i = 0; i < a_ln; ++i){
            a_ln = (long) a[i];

            for(int j = 0; j < b_len; ++j){
                b_ln = (long) b[j];
                final_product = a_ln * b_ln + result[i+j];
                int carry = (int) Math.floor(product / Base);
                result[i+j] = final_product - carry * Base;
                result[i+j+1] += carry;
            }
        }

        StringBuilder stringNum = new StringBuilder();

        for(long num : result){
            stringNum.append(num);
        }

        Num productResult = new Num(stringNum.reverse().toString());
        trimLongNum(productResult);

        return productResult;
    }
	// Return number to a string in base 10
    public String toString() {
        StringBuilder numsStr = new StringBuilder();

        for(long digit : this.lngnumber){
            numStr.append(digit);
        }

        numsStr.reverse();

        return numsStr.toString();
    }

    public static void trimLongNum(Num a){
        StringBuilder strBuilder = new StringBuilder();
        int n = a.lngnumber.size();

        while(--i != 0 && a.lngnumber.get(i) == 0 ){
            a.remove(i);
        }

        for(int j = i; j >=0; j--){
            strBuilder.append(a.lngnumber.get(j));
        }
    }

    public static void trimNum(ArrayList<Long> number){
        int i = number.size();

        while(i < v.size()){
            if(v.get(--i) == 0){
                v.remove(i);
            }
        }
    }

	//  methods below are optional
	
	public static Num subtract(Num a, Num b) {
	return null;
    }
    // Use divide and conquer
    public static Num power(Num a, long n) {
	return null;
    }

    // Use divide and conquer or division algorithm to calculate a/b
    public static Num divide(Num a, Num b) {
        return SubtractNums(a,b);
    }

    public static Num subtractNums(Num a, Num b){
        Num val;

        if(a.compareTo(b) >= 0){
            val = subNumbers(a,b);
        }

        else{
            val = subNumbers(b,a);
        }

        return val;
    }

    public static Num subNumbers(Num a, Num b){
        int a_length = a.lngnumber.size();
        int b_length = b.lngnumber.size();
        Arraylist<Long> result = new ArrayList<>(a_length);
        long borrow = 0;
        int i = 0;
        long Base = a.base;
        long diff = 0;

        for(i = 0; i < b_length; i++){
            diff = a.lngnumber.get(i) - borrow - b.lngnumber.get(i);

            if(diff < 0){
                diff += Base;
                borrow = 1;
            }

            else{
                borrow = 0;
            }

            result.add(i, diff);
        }

        for(i = b_length; i < a_length; i++){
            diff = a.lngnumber.get(i)-borrow;
            if(diff < 0) diff += Base;
            else{
                result.add(i++, diff);
                break;
            }

            result.add(i, diff);
        }

        for(; i < a_length; i++){
            result.add(i, a.lngnumber.get(i));
        }

        trimNum(result);

        StringBuilder stringNum = new StringBuilder();

        for (long num : resulkt){
            stringNum.append(num);
        }
        Num output_result = new Num(stringNum.reverse().toString());

        return output_result;
    }


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
        if(this.lngnumber.size() == 1 && other.lngnumber.size() == 1 && this.lngnumber.get(0) == 0 && other.lngnumber.get(0) == 0) return 0;

        if(this.lngnumber.size() != other.lngnumber.size()){
            return this.lngnumber.size() > other.lngnumber.size() ? 1 : -1;
        }

        for(int i = this.lngnumber.size()-1; i >= 0; i--){
            if (this.number.get(i) != other.lngnumber.get(i)) return this.number.get(i) > other.lngnumber.get(i) ? 1 : -1;
        }

        return 0;
    }

    public static Num shiftleft(Num x, int n){
        List<Long> number = new ArrayList<>();
        while(n-- > 0) number.add((long) 0);
        number.addAll(x.number);
        StringBuilder strNum = new StringBuilder();

        for(long num: number){
            strNum.append(num);
        }

        return new Num(strNum.reverse().toString());
    }
    
    // Output using the format "base: elements of list ..."
    public void printList() {
		System.out.print(Base + " : ");
		for(long num : lngnumber) {
			System.out.print(num + " ");
		}
	System.out.println();
    }
    


    public long base() { return base; }

    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {
	return null;
    }

    // Divide by 2, for using in binary search
    public Num by2() {
	return null;
    }

 


    public static void main(String[] args) {
	Num x = new Num("8888888888888");
	Num y = new Num("2");
	Num z = Num.add(x, y);
	System.out.println(z);
	Num a = Num.product(x, y);
	System.out.println(a);
	if(a != null) a.printList();
    }
}