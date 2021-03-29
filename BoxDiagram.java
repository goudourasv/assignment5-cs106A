import acm.graphics.*;
import acm.program.ConsoleProgram;
import acm.program.GraphicsProgram;
import acm.program.GraphicsProgram.*;
import acm.program.Program.*;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;



public class BoxDiagram extends GraphicsProgram {
    private static final double BOX_WIDTH = 120;
    private static final double BOX_HEIGHT = 50;


    public void init(){
        nameField = new JTextField(30);
        add = new JButton("Add");
        remove = new JButton("Remove");
        clear = new JButton("Clear");
        add(new JLabel("Name"),SOUTH);
        add(nameField,SOUTH);
        add(add,SOUTH);
        add(remove,SOUTH);
        add(clear,SOUTH);
        nameField.addActionListener(this);
        addActionListeners();
        addMouseListeners();
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==add){
            addNewGCompound();
        }
        if(e.getSource()==remove){
            GCompound objectToRemove = boxes.get(nameField.getText());
            remove(objectToRemove);

        }
        if(e.getSource()==clear){
            removeAll();
        }
    }

    private void addNewGCompound() {
        GCompound item = new GCompound();
        GRect rect = new GRect(getWidth()/2 - BOX_WIDTH/2,getHeight()/2 + BOX_HEIGHT/2,BOX_WIDTH,BOX_HEIGHT);
        item.add(rect);
        String commandLabel = nameField.getText();
        GLabel name = new GLabel(commandLabel,getWidth()/2-BOX_WIDTH/4,getHeight()/2 + BOX_HEIGHT);
        name.setFont("Courier 24");
        item.add(name);
        add(item);
        boxes.put(commandLabel,item);
    }



    /* instance vars*/
    HashMap<String,GCompound> boxes = new HashMap<String,GCompound>();
    private JTextField nameField;
    private JButton add;
    private JButton remove;
    private JButton clear;
}