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
 * @version 2017-03-13
 */
public class IU4 {

    private static final CSVReader textReader = new CSVReader();

    /**
     * @param args the command line arguments
     */
    private static ArrayList<String[]> dataList = new ArrayList();

    public static void main(String[] args) {
        dataList = textReader.readCSVFile();
        HashMap<String, Integer> mapData;

        System.out.println("1. Antal poster: " + dataList.size() + " st");

        mapData = createHashMap(dataList, 4);
        Object[] resultObj = mapData.entrySet().toArray();
        System.out.println("\n2. "
                + "\n" + resultObj[0] + " st"
                + "\n" + resultObj[1] + " st"
                + "\n" + resultObj[2] + " st"
                + "\n" + resultObj[3] + " st");

        String[] tmpArray = mostViralPost(dataList, 5);
        System.out.println("\n3. Posten med ID " + tmpArray[0] + " har flest \"shares\""
                + "\nTexten är: " + tmpArray[3]
                + "\nTyp: " + tmpArray[4]);

        System.out.println("\n4. I genomsnitt har posterna: " + sharesInAverage(dataList, 5) + " st shares");

        mapData = createUndefinedHashMap(dataList, 6);
        String tempString = mostUsedOfKey(mapData);
        String tempDate = tempString.substring(2, 14);
        String tempValue = tempString.substring(16, 19);
        System.out.println("\n5. I " + tempDate + " publicerades " + tempValue + " poster");

        mapData = createHashMap(dataList, 3);
        tempString = mostUsedOfKey(mapData);
        String tempWord = tempString.substring(0, 3);
        tempValue = tempString.substring(5, 9);
        System.out.println("\n6. Ordet \'" + tempWord + "\' förekommer " + tempValue + " gånger");

        System.out.println("\n7. Totalt har användaren skrivit " + countAllChars(dataList, 3) + " tecken");

        ArrayList<String> tempArray = createWordList(dataList, 3, 35000);
        mapData = createStringHashMap(tempArray);
        System.out.println("\n8. Av Donald Trumps första " + tempArray.size() + " ord, så är " + mapData.size() + " unika, och representerar hans vokabulära omfång"
                + "\nKan jämföras med nedan på samma antal av ord:"
                + "\nShakespear: 5170 unika ord"
                + "\nBoken \'Moby Dick\': 6022 unika ord"
                + "\nEminem: 4494 unika ord"
                + "\nReferens: https://pudding.cool/2017/02/vocabulary/");

    }

    /**
     *
     * @param inArray Array containing data
     * @param inInt index of String[] in the array containing relevant data
     * @return a HashMap with the relevant string as key and value
     */
    public static HashMap createHashMap(ArrayList<String[]> inArray, int inInt) {
        HashMap<String, Integer> tempMap = new HashMap();
        for (String[] strings : inArray) {
            StringTokenizer st = new StringTokenizer(strings[inInt]);
            while (st.hasMoreTokens()) {
                String result = st.nextToken().replaceAll("[^\\w\\s]", "");
                if (tempMap.containsKey(result.toLowerCase())) {
                    int i = tempMap.get(result.toLowerCase());
                    tempMap.put(result.toLowerCase(), (i + 1));
                } else {
                    tempMap.put(result.toLowerCase(), 1);

                }

            }

        }

        return tempMap;
    }

    /**
     *
     * @param inArray Array containing data
     * @param inInt index of String[] in the array containing relevant data
     * @return a HashMap with the relevant string as key and value (does not
     * replace symbols or space)
     */
    public static HashMap createUndefinedHashMap(ArrayList<String[]> inArray, int inInt) {
        HashMap<String, Integer> tempMap = new HashMap();
        for (String[] strings : inArray) {
            if (tempMap.containsKey(strings[inInt].toLowerCase())) {
                int i = tempMap.get(strings[inInt].toLowerCase());
                tempMap.put(strings[inInt].toLowerCase(), (i + 1));
            } else {
                tempMap.put(strings[inInt].toLowerCase(), 1);
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

    /**
     * This method creates a word list of 'inSize' existing words (English
     * dictionary)
     *
     * @param inArray Array containing data
     * @param inInt index of String[] in the array containing relevant data
     * @param inSize the size of words to check
     * @return a ArrayList with the relevant string of words
     */
    public static ArrayList<String> createWordList(ArrayList<String[]> inArray, int inInt, int inSize) {
        ArrayList<String> dictionary = textReader.readTXTFile();
        ArrayList<String> wordList = new ArrayList<>();
        HashMap<String, Integer> wordMap = createStringHashMap(dictionary);

        for (String[] strings : inArray) {
            StringTokenizer st = new StringTokenizer(strings[inInt].toLowerCase());
            while (st.hasMoreTokens()) {
                String result = st.nextToken().replaceAll("[^\\w\\s]", "");
                if (wordMap.containsKey(result)) {
                    wordList.add(result);
                    if (wordList.size() >= inSize) {
                        return wordList;
                    }

                }

            }

        }
        //fail, to few words
        return null;
    }

    /**
     * A hashmap containing unique words as Key
     *
     * @param inArray Array containing data
     * @return a HashMap with the relevant string as key and value of rising
     * integer (amount of occurance of word)
     */
    public static HashMap createStringHashMap(ArrayList<String> inArray) {
        HashMap<String, Integer> tempMap = new HashMap();
        for (String strings : inArray) {
            if (tempMap.containsKey(strings.toLowerCase())) {
                int i = tempMap.get(strings.toLowerCase());
                tempMap.put(strings.toLowerCase(), (i + 1));
            } else {
                tempMap.put(strings.toLowerCase(), 1);
            }
        }
        return tempMap;
    }
}
