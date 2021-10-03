package controller;

import com.opencsv.exceptions.CsvException;
import model.Data;
import model.Model;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Controller
{
    private Model model;

    public Controller()
    {
        model = new Model();
    }

    public List<Data> getDataList()
    {
        return model.getDataList();
    }

    public void loadFromFile(File file) throws IOException, CsvException
    {
        model.loadFromFile(file);
    }

    public void saveFromFile(File file) throws IOException
    {
        model.saveFromFile(file);
    }
}
