/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iu4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author Fredrik Bogren - Fredrik.Bogren@hotmail.com
 * @version 2017-02-27
 */
public class IU4 {

    private static final CSVReader reader = new CSVReader();

    /**
     * @param args the command line arguments
     */
    private static ArrayList<String[]> dataList = new ArrayList();

    public static void main(String[] args) {
        dataList = reader.readFile();

        HashMap<String, Integer> mapData;
        System.out.println("Antal poster av användaren: " + dataList.size());
        mapData = createHashMap(dataList, 4);
        System.out.println("Antal poster per typ: " + mapData.entrySet());
        System.out.println("Mest använda mediet är: " + mostUsedOfKey(mapData));
        System.out.println("Posterna har i genomsnitt: " + sharesInAverage(dataList, 5) + " shares");
        String[] tmpArray = mostViralPost(dataList, 5);
        System.out.println("Mest virala post är ett " + tmpArray[4] + " med " + tmpArray[5] + " antal shares");
        mapData = createHashMap(dataList, 6);
        System.out.println("Mest populära ÅÅMM är " + mostUsedOfKey(mapData).substring(0, 4));
        mapData = createHashMap(dataList, 3);
        System.out.println("Mest använda ordet är: " + mostUsedOfKey(mapData));
        System.out.println("Användaren har skrivit totalt: " + countAllChars(dataList, 3) + " tecken");
    }

    /**
     *
     * @param inArray Array containing data
     * @param inInt index of String[] in the array containing relevant data
     * @return a LinkedHashMap with the relevant string as key and value
     */
    public static HashMap createHashMap(ArrayList<String[]> inArray, int inInt) {
        HashMap<String, Integer> tempMap = new HashMap();
        for (String[] strings : inArray) {
            StringTokenizer st = new StringTokenizer(strings[inInt]);
            while (st.hasMoreTokens()) {
                String result = st.nextToken().replaceAll("[^\\w\\s]", ""); //Tar bort allting som inte är bokstäver
                if (tempMap.containsKey(result.toLowerCase())) {
                    int i = tempMap.get(result.toLowerCase());
                    tempMap.put(result.toLowerCase(), (i + 1));
                } else {
                    tempMap.put(result.toLowerCase(), 1); //Lagrar samtliga ord i HashMap med ordet som nyckel, detta gör även att när samma nyckel (ord) dyker upp flera gånger så hamnar det under samma nyckel

                }

            }

        }

        return tempMap;
    }

    /**
     * Timecomplexity of this searchmethod is O(n), but it will only search for
     * every unique word once because of the datastructure
     *
     * @param inMap a HashMap containing (key(string of word) and value(integer
     * of how many times the word has been used))
     * @return a string of the most used word and how many times it has been
     * used
     */
    public static String mostUsedOfKey(HashMap<String, Integer> inMap) {
        String tempWord = null;
        int value = 0;
        Iterator itKey = inMap.entrySet().iterator();
        while (itKey.hasNext()) {
            Map.Entry pair = (Map.Entry) itKey.next();
            itKey.remove(); // avoids a ConcurrentModificationException
            if ((int) pair.getValue() > value) {
                value = (int) pair.getValue();
                tempWord = (String) pair.getKey();
            }
        }
        return tempWord = tempWord + ": " + value;
    }

    /**
     *
     * @param inArray ArrayList<String[]> containing data
     * @param inInt Index of String[] containing shares count data
     * @return String[] of the most viral post
     */
    public static String[] mostViralPost(ArrayList<String[]> inArray, int inInt) {
        int highestShare = 0;
        String[] tmpArray = null;
        for (String[] strings : inArray) {
            if (Integer.parseInt(strings[inInt]) > highestShare) {
                highestShare = Integer.parseInt(strings[inInt]);
                tmpArray = strings;
            }
        }
        return tmpArray;
    }

    /**
     *
     * @param inArray ArrayList<String[]> containing data
     * @param inInt Index of String[] containing shares count data
     * @return the average amount of shares as Integer (summed down to closest
     * Integer)
     */
    public static int sharesInAverage(ArrayList<String[]> inArray, int inInt) {
        int totalShares = 0;
        for (String[] strings : inArray) {
            totalShares += Integer.parseInt(strings[inInt]);
        }
        return (totalShares / inArray.size());
    }

    /**
     *
     * @param inArray ArrayList<String[]> containing data
     * @param inInt Index of String[] containing messages wrote by poster
     * @return an Integer sum of all Chars written by poster
     */
    public static int countAllChars(ArrayList<String[]> inArray, int inInt) {
        int charAmount = 0;
        for (String[] strings : inArray) {
            charAmount += strings[inInt].length();
        }
        return charAmount;
    }
}
