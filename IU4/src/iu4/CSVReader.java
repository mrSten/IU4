/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iu4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Fredrik Bogren
 * @version 2017-02-27
 */
public class CSVReader {

    public ArrayList readCSVFile() {

        String csvFile = "C:/Users/Fredr/Documents/NetBeansProjects/IU4/IU4/newCSV.csv";
        String line;
        String[] dataArray;
        ArrayList<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                dataArray = line.split(";");
                data.add(dataArray);
            }
        } catch (IOException e) {
        }
        return data;

    }

    public ArrayList readTXTFile() {
        String txtFile = "C:/Users/Fredr/Documents/NetBeansProjects/IU4/IU4/engWords.txt";
        String line;
        ArrayList<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(txtFile))) {
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
        }
        return data;
    }

}
