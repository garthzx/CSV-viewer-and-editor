package gui;

import com.opencsv.exceptions.CsvException;
import controller.Controller;
import model.Data;
import utils.Utils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFrame extends JFrame
{
    private TablePanel tablePanel;
    private Controller controller;
    private JFileChooser fileChooser;

    public MainFrame()
    {
        super("CSV Editor");

        setLayout(new BorderLayout());

        controller = new Controller();

        initComponents();

        setJMenuBar(createMenuBar());

        add(tablePanel, BorderLayout.CENTER);

        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents()
    {
        tablePanel = new TablePanel();
        List<Data> defaultDataList = new ArrayList<>();


        tablePanel.setData(defaultDataList);

        fileChooser = new JFileChooser();
        fileChooser.setPreferredSize(new Dimension(710, 400));
        fileChooser.addChoosableFileFilter(new FileFilter()
        {
            @Override
            public boolean accept(File f)
            {
                String fileName = f.getName();
                String fileExtension = Utils.getFileExtensions(fileName);
                if (fileExtension == null)
                    return false;
                return fileExtension.equals("csv");
            }

            @Override
            public String getDescription()
            {
                return "CSV Files (*.csv)";
            }
        });
    }

    private JMenuBar createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exportDataItem = new JMenuItem("Export Data...");
        JMenuItem importDataItem = new JMenuItem("Import Data...");
        JMenuItem exitItem = new JMenuItem("Exit");

        importDataItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        controller.loadFromFile(fileChooser.getSelectedFile());
                        tablePanel.setData(controller.getDataList());
                        tablePanel.refresh();
                        System.out.println("Success");
                    }
                    catch (IOException | CsvException ioe)
                    {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Could not load data from file.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        fileMenu.add(importDataItem);
        fileMenu.add(exportDataItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        return menuBar;
    }
}

class TablePanel extends JPanel
{
    private JTable table;
    private TableModel tableModel;

    public TablePanel()
    {
        tableModel = new TableModel();

        table = new JTable(tableModel);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void refresh()
    {
        tableModel.fireTableDataChanged();
//        tableModel.fireTableStructureChanged();
    }

    public void setData(List<Data> dataList)
    {
        tableModel.setData(dataList);
    }
}

class TableModel extends AbstractTableModel
{
    private List<Data> data;
    private List<String> colNames;

    public TableModel()

    {
        colNames = new ArrayList<>();

        // initial values on startup
        colNames.add("1");
        colNames.add("2");
        colNames.add("3");
        colNames.add("4");
        colNames.add("5");
    }

    public void setData(List<Data> data)
    {
        this.data = data;
        if (!data.isEmpty())
        {
            String[] dataColumnNames = data.get(0).getValue();
            colNames.clear();
            colNames.addAll(Arrays.asList(dataColumnNames));

            // remove first row
            data.remove(0);
        }

        fireTableStructureChanged();
        fireTableDataChanged();;
    }

    @Override
    public int getRowCount()
    {
        return data.size();
    }

    @Override
    public int getColumnCount()
    {
        return colNames.size();
    }

    @Override
    public String getColumnName(int column)
    {
        return colNames.get(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return data.get(rowIndex).getValue()[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true;
    }

}