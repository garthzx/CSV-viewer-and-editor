package com.company;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import gui.MainFrame;

import javax.swing.*;

public class Main
{

    public static void main(String[] args)
    {
        try
        {
            FlatLightLaf.setup();
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ul)
        {
            ul.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
