package Task2;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Drawer extends JApplet {
    
    

    String inputs = "";

    JTextField jtxfInfix = new JTextField("", 50);
    JButton jbtNew = new JButton("New");
    JButton jbtSave = new JButton("Save");
    JButton jbtExit = new JButton("Exit");
    JLabel lblInfix = new JLabel("Infix Expression:");

    JButton j0 = new JButton("0");
    JButton j1 = new JButton("1");
    JButton j2 = new JButton("2");
    JButton j3 = new JButton("3");
    JButton j4 = new JButton("4");
    JButton j5 = new JButton("5");
    JButton j6 = new JButton("6");
    JButton j7 = new JButton("7");
    JButton j8 = new JButton("8");
    JButton j9 = new JButton("9");
    JButton jadd = new JButton("+");
    JButton jmin = new JButton("-");
    JButton jmul = new JButton("*");
    JButton jdiv = new JButton("/");
    JButton jc = new JButton("C");
    JButton jca = new JButton("CA");

    TreeGUI t = new TreeGUI();

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Drawer");
        JApplet applet = new Drawer();//
        applet.init();//throw all the button to japplet
        frame.getContentPane().add(applet);
        frame.pack();
        frame.setVisible(true);
    }
    
     public boolean checkInput(String input) {
        String temp = "";
        temp = input.replaceAll(" ", ""); //remove spaces
        
        //scan every character to check if character is odd or even
        for (int i = 0; i < temp.length(); i++) {
            if (i % 2 == 0) { //if character is even
                if (!isNumeric(temp.charAt(i))) { //if character is not numeric
                    return true;
                }
                else{
                    if(isNumeric(temp.charAt(0)) && temp.length()== 1){ //if the character is numeric
                        return true;
                    }
                }
            } 
            else if (i % 2 != 0 || i == temp.length()-1) { //if character is odd
                if (!isOperator(temp.charAt(i))) { //if character is not an operator
                    return true;
                }    
                else{
                    if(isOperator(temp.charAt(temp.length()-1))){ //the last operator cannot be operator
                        return true;
                    }
                }
            }
        }
        return false;
    }
         private boolean isOperator(char operator) { //also used for exception handling
        if (operator == '+' || operator == '-' || operator == '*' || 
            operator == '/' ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isNumeric(char num) { //used for exception handling
        if (num =='0' || num == '1' || num == '2' || num == '3' || num == '4' ||
            num == '5' || num == '6' || num == '7' || num == '8' || num == '9'){
            return true;
        } else {
            return false;
        }
    }

    public void init() {
        JPanel buttons = new JPanel();
        
        //buttons.add(j0);
        buttons.add(j7);
        buttons.add(j8);
        buttons.add(j9);
        buttons.add(jadd);
        buttons.add(j4);
        buttons.add(j5);
        buttons.add(j6);
        buttons.add(jmin);
        buttons.add(j1);
        buttons.add(j2);
        buttons.add(j3);
        buttons.add(jmul);
        buttons.add(jc);
        buttons.add(j0);
        buttons.add(jca);
        buttons.add(jdiv);
        buttons.add(jbtNew);
        buttons.add(jbtSave);
        buttons.add(jbtExit);
        
        Font f = new Font("Tahoma", Font.BOLD, 25);
        j0.setFont(f);
        j1.setFont(f);
        j2.setFont(f);
        j3.setFont(f);
        j4.setFont(f);
        j5.setFont(f);
        j6.setFont(f);
        j7.setFont(f);
        j8.setFont(f);
        j9.setFont(f);
        jc.setFont(f);
        jca.setFont(f);
        jadd.setFont(f);
        jmin.setFont(f);
        jmul.setFont(f);
        jdiv.setFont(f);
        jbtNew.setFont(f);
        jbtSave.setFont(f);
        jbtExit.setFont(f);
        lblInfix.setFont(f);
        jtxfInfix.setFont(f);
        
        buttons.setLayout(new GridLayout(5,4));
        getContentPane().add(buttons, BorderLayout.EAST);
        
        
        
        JPanel lbltxf = new JPanel();
        lblInfix.setForeground(Color.BLACK);
        lbltxf.add(lblInfix);
        lbltxf.add(jtxfInfix);
        
        lbltxf.setLayout(new GridLayout(2,1));
        getContentPane().add(lbltxf, BorderLayout.NORTH);
 

        jbtNew.addActionListener(new ButtonListener());
        j0.addActionListener(new NumberListener());
        j1.addActionListener(new NumberListener());
        j2.addActionListener(new NumberListener());
        j3.addActionListener(new NumberListener());
        j4.addActionListener(new NumberListener());
        j5.addActionListener(new NumberListener());
        j6.addActionListener(new NumberListener());
        j7.addActionListener(new NumberListener());
        j8.addActionListener(new NumberListener());
        j9.addActionListener(new NumberListener());
        jc.addActionListener(new NumberListener());
        jca.addActionListener(new NumberListener());
        jbtExit.addActionListener(new ExitListener());
        jbtSave.addActionListener(new SaveListener());

        //JPanel tree = new JPanel();
        //tree.add(t);
        t.setBackground(Color.white);
        getContentPane().add(t, BorderLayout.CENTER); //no need the getContentPane() also can

    }

 private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String input = jtxfInfix.getText();
            if(input.equals("")||input==null){//if input is "" means no input
                JOptionPane.showMessageDialog(null, "There is no input.");
            }                                               
            else{
                input= (jtxfInfix.getText().charAt(0)+jtxfInfix.getText().substring(1).replaceAll("", " "));//to remove all the spaes
                input=input.substring(0, input.length());
                if(checkInput(input)){
                    JOptionPane.showMessageDialog(null, "Invalid infix input.");
                }
                else{
                    input = input.replaceAll("[()]", "");   //to replace all the [()] in the stack to ""
                    t.setinfixExpression(input);
                }
            } 
    
        }
    }

   private class NumberListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == j0) {
                inputs += "0";
                jtxfInfix.setText(inputs);
            } else if (e.getSource() == j1) {
                inputs += "1";
                jtxfInfix.setText(inputs);
            } else if (e.getSource() == j2) {
                inputs += "2";
                jtxfInfix.setText(inputs);
            } else if (e.getSource() == j3) {
                inputs += "3";
                jtxfInfix.setText(inputs);
            } else if (e.getSource() == j4) {
                inputs += "4";
                jtxfInfix.setText(inputs);
            } else if (e.getSource() == j5) {
                inputs += "5";
                jtxfInfix.setText(inputs);
            } else if (e.getSource() == j6) {
                inputs += "6";
                jtxfInfix.setText(inputs);
            } else if (e.getSource() == j7) {
                inputs += "7";
                jtxfInfix.setText(inputs);
            } else if (e.getSource() == j8) {
                inputs += "8";
                jtxfInfix.setText(inputs);
            } else if (e.getSource() == j9) {
                inputs += "9";
                jtxfInfix.setText(inputs);
            } else if (e.getSource() == jadd){
                inputs += "+";
                jtxfInfix.setText(inputs);
            } else if (e.getSource() == jmin){
                inputs += "-";
                jtxfInfix.setText(inputs);
            } else if (e.getSource() == jmul){
                inputs += "*";
                jtxfInfix.setText(inputs);
            } else if (e.getSource() == jdiv){
                inputs += "/";
                jtxfInfix.setText(inputs);
            } else if (e.getSource() == jc) {   //clear
                if ("".equals(inputs)) {    //if the txtfield ady is "" then nthg to be remove
                    JOptionPane.showMessageDialog(null, "No infix expression to be cleared.");
                } else {
                    int temp = inputs.length() - 1;
                    inputs = inputs.substring(0, temp);
                    jtxfInfix.setText(inputs);
                }
                
            } else if (e.getSource() == jca) {  //clear all
                inputs += "";   //set the textfield to empty
                jtxfInfix.setText("");
            }
        }
    }
   
   private class ExitListener implements ActionListener {
        public void actionPerformed (ActionEvent e){
            if (e.getSource() == jbtExit){
                System.exit(0);
            }
        }
    }
   
   private class SaveListener implements ActionListener { //SaveListener cannot chg

    public void actionPerformed(ActionEvent e) {
        //save as user file name
        String str = JOptionPane.showInputDialog(null, "Enter the file name to save your binary tree:");
        //save as jpeg
        BufferedImage binarytree = new BufferedImage(t.getSize().width, t.getSize().height,BufferedImage.TYPE_INT_RGB);  
        //user need to enter the width and height of the binary tree. TYPE_INT_RGB is integer pixel
        t.paint(binarytree.createGraphics()); 
        
        if (str != null){ //the str(name of the binary tree) cannot be empty
            try {
                ImageIO.write(binarytree, "jpg", new File(str + ".jpg"));
                JOptionPane.showMessageDialog(null, "Image successfully saved.");
            } catch (IOException ex) {
                ex.printStackTrace();//error message
                JOptionPane.showMessageDialog(null, "Image failed to save.");
            }
        }
        else{
            System.out.println("");
        }

    }

    }
  }
 


