package model;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Model
{
    private List<Data> dataList;

    public Model()
    {
        dataList = new ArrayList<>();
    }

    public List<Data> getDataList()
    {
        return this.dataList;
    }

    public void loadFromFile(File file) throws IOException, CsvException
    {
        // clear the list first, then insert values from csv file
        dataList.clear();

        CSVReader reader = new CSVReader(new FileReader(file));
        List<String[]> entries = reader.readAll();
        for (String[] substrings : entries)
        {
            dataList.add(new Data(substrings));
        }

        reader.close();
    }

    public void saveFromFile(File file) throws IOException
    {
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        for (Data data : dataList)
        {
            writer.writeNext(data.getValue());
        }

        writer.close();
    }
}
