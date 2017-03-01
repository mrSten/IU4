/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iu4;

import java.util.ArrayList;

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

    }

}
