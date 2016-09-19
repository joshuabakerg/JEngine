package com.joshuabakerg.GBC;

import Entity.GameObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua on 2016/05/27.
 */
public class GBC {
    public static int y =10;
    public static List<Option> options = new ArrayList<Option>();

    /**
     * @wbp.parser.entryPoint
     */
    public static GameObject CreateObject(){
        JFrame frame = new JFrame("object creator");
        frame.setSize(388,330);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton btnCreate = new JButton("Create");
        btnCreate.setBounds(10, 257, 89, 23);
        frame.getContentPane().add(btnCreate);
        
        JButton btnAdd = new JButton("add");
        btnAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                Option o =  new Option(y);
                frame.getContentPane().add(o);
                options.add(o);
                y += 30;
        	}
        });
        btnAdd.setBounds(279, 11, 69, 23);
        frame.getContentPane().add(btnAdd);
        
        JButton btnRemove = new JButton("remove");
        btnRemove.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                frame.remove(options.get(options.size()-1));
                options.remove(options.size()-1);
                frame.revalidate();
                frame.repaint();
                y -= 30;
        	}
        });
        btnRemove.setBounds(279, 41, 69, 23);
        frame.getContentPane().add(btnRemove);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        return null;
    }

}

class Option extends Panel{

    private JComboBox comboBox = new JComboBox();
    private JTextField field = new JTextField();
    private JLabel label = new JLabel("Name:");


    public Option(int y){
        super();

        setBounds(10, y, 219, 24);

        setLayout(null);

        comboBox.setBounds(134, 0, 85, 20);
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"transform", "renderSprite", "walk"}));

        field.setBounds(53, 0, 71, 20);
        field.setColumns(10);

        label.setBounds(10, 3, 38, 14);

        add(comboBox);
        add(field);
        add(label);

    }

}