/**
 * Starter code for MDS
 *
 * @author rbk
 */

//  Team Members:
//  Manav Prajapati (mnp200002)
//  Rahul Bosamia (rnb200003)
//  Mayank Goyani (mxg200078)
//  Kalyan Kumar (axs200019)

// Change to your net id
package mxg200078;

import java.util.*;

// If you want to create additional classes, place them in this file as subclasses of MDS

public class MDS {
    public class Item {
        Long id;
        Set<Long> desc;
        Money price;

        public Item(long id, Money price, List<Long> list) {
            this.id = id;
            this.price = price;
            this.desc = new HashSet<Long>(list);
        }

        // public int compareTo(Item item) {
        // return this.id.compareTo(item.id);
        // }

        public String toString() {
            return "[ID: " + id + ",\tPrice: " + price + ",\tDescription: " + desc + "]";
        }

    }

    // Add fields of MDS here
    // Here I am using treeMap becuase finding the data between
    // two value would be easier
    // mapping id -> Item
    TreeMap<Long, Item> treeMap;
    // for getting the data from the desciprion
    // mapping desc -> Number of items
    Map<Long, TreeSet<Item>> map;

    // Constructors
    public MDS() {
        treeMap = new TreeMap<>();
        map = new HashMap<>();
    }

    /*
     * Public methods of MDS. Do not change their signatures.
     * __________________________________________________________________
     * a. Insert(id,price,list): insert a new item whose description is given
     * in the list. If an entry with the same id already exists, then its
     * description and price are replaced by the new values, unless list
     * is null or empty, in which case, just the price is updated.
     * Returns 1 if the item is new, and 0 otherwise.
     */
    public int insert(long id, Money price, List<Long> list) {
        // check if item is exist or not in our system
        if (!treeMap.containsKey(id)) {
            Item item = new Item(id, price, list);
            treeMap.put(id, item);
            // add into desc
            for (long d : list) {
                if (!map.containsKey(d)) {
                    map.put(d, new TreeSet<>((a, b) -> {
                        if (a.price.compareTo(b.price) == 0) {
                            return a.id.compareTo(b.id);
                        }
                        return a.price.compareTo(b.price);
                    }));
                }
                map.get(d).add(item);
            }
            return 1;
        } else {
            Item item = treeMap.get(id);
            if (list != null && list.size() > 0) {
                for (long d : item.desc) {
                    map.get(d).remove(item);
                }
                item.desc = new HashSet<>(list);
                for (long li : list) {
                    if (!map.containsKey(li)) {
                        map.put(li, new TreeSet<>((a, b) -> {
                            return a.price.compareTo(b.price);
                        }));
                    }
                    map.get(li).add(item);
                }

            }
            item.price = price;
            return 0;
        }

    }

    // b. Find(id): return price of item with given id (or 0, if not found).
    public Money find(long id) {
        if (treeMap.containsKey(id)) {
            return treeMap.get(id).price;
        }
        return new Money();
    }

    /*
     * c. Delete(id): delete item from storage. Returns the sum of the
     * long ints that are in the description of the item deleted,
     * or 0, if such an id did not exist.
     */
    public long delete(long id) {
        if (treeMap.containsKey(id)) {
            long result = 0;
            Item item = treeMap.get(id);
            for (long d : item.desc) {
                result += d;
                // remove from desc
                if (map.containsKey(d)) {
                    map.get(d).remove(item);
                }

            }
            treeMap.remove(id);
            return result;
        }
        return 0;
    }

    /*
     * d. FindMinPrice(n): given a long int, find items whose description
     * contains that number (exact match with one of the long ints in the
     * item's description), and return lowest price of those items.
     * Return 0 if there is no such item.
     */
    public Money findMinPrice(long n) {
        if (map.containsKey(n)) {
            Money min = new Money(String.valueOf(Long.MAX_VALUE));
            for (Item item : map.get(n)) {
                if (item.price.compareTo(min) < 0) {
                    min = item.price;
                }
            }
            return min;
        }
        return new Money();
    }

    /*
     * e. FindMaxPrice(n): given a long int, find items whose description
     * contains that number, and return highest price of those items.
     * Return 0 if there is no such item.
     */
    public Money findMaxPrice(long n) {
        if (map.containsKey(n)) {
            Money max = new Money("0");
            for (Item item : map.get(n)) {
                if (item.price.compareTo(max) > 0) {
                    max = item.price;
                }
            }
            return max;
        }
        return new Money();
    }

    /*
     * f. FindPriceRange(n,low,high): given a long int n, find the number
     * of items whose description contains n, and in addition,
     * their prices fall within the given range, [low, high].
     */
    public int findPriceRange(long n, Money low, Money high) {
        if (map.containsKey(n)) {
            int count = 0;
            for (Item item : map.get(n)) {
                if (item.price.compareTo(low) >= 0 && item.price.compareTo(high) <= 0) {
                    count++;
                }
            }
            return count;
        }
        return 0;
    }

    /*
     * g. PriceHike(l,h,r): increase the price of every product, whose id is
     * in the range [l,h] by r%. Discard any fractional pennies in the new
     * prices of items. Returns the sum of the net increases of the prices.
     */
    public Money priceHike(long l, long h, double rate) {
        Map<Long, Item> sub = treeMap.subMap(l, h + 1);
        if (sub != null) {
            long netIncrease = 0;
            for (long key : sub.keySet()) {
                Item item = sub.get(key);
                Money itemPrice = item.price;
                long ogPrice = itemPrice.dollars() * 100 + itemPrice.cents();
                long increase = (long) (Math.floor((double) (ogPrice * rate) / 100));
                long newPrice = ogPrice + increase;
                item.price = new Money(newPrice / 100, (int) (newPrice % 100));
                netIncrease += increase;
            }
            return new Money(netIncrease / 100, (int) (netIncrease % 100));
        }
        return new Money();
    }

    /*
     * h. RemoveNames(id, list): Remove elements of list from the description of id.
     * It is possible that some of the items in the list are not in the
     * id's description. Return the sum of the numbers that are actually
     * deleted from the description of id. Return 0 if there is no such id.
     */
    public long removeNames(long id, List<Long> list) {
        if (treeMap.containsKey(id)) {
            long count = 0;
            Item item = treeMap.get(id);
            for (long d : list) {
                if (item.desc.contains(d)) {
                    map.get(d).remove(item);
                    count++;
                }
                item.desc.remove(d);
            }
            return count;
        }
        return 0;
    }

    // Do not modify the Money class in a way that breaks LP4Driver.java
    public static class Money implements Comparable<Money> {
        long d;
        int c;

        public Money() {
            d = 0;
            c = 0;
        }

        public Money(long d, int c) {
            this.d = d;
            this.c = c;
        }

        public Money(String s) {
            String[] part = s.split("\\.");
            int len = part.length;
            if (len < 1) {
                d = 0;
                c = 0;
            } else if (len == 1) {
                d = Long.parseLong(s);
                c = 0;
            } else {
                d = Long.parseLong(part[0]);
                c = Integer.parseInt(part[1]);
                if (part[1].length() == 1) {
                    c = c * 10;
                }
            }
        }

        public long dollars() {
            return d;
        }

        public int cents() {
            return c;
        }

        public int compareTo(Money other) { // Complete this, if needed

            return (this.d == other.d) ? (this.c == other.c ? 0 : (this.c < other.c) ? -1 : 1)
                    : (this.d < other.d) ? -1 : 1;
        }

        public String toString() {
            if (c < 10)
                return d + ".0" + c;
            return d + "." + c;
        }
    }

}
