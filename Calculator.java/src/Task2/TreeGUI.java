/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Task2;
import java.awt.*;
import java.awt.geom.Arc2D;
import javax.swing.*;

/**
 *
 * @author Windows
 */
public class TreeGUI extends JPanel {
    String infixExpression = "";
    BTree <String> b;//carry operator and operand(String)
    int xCoordinate = 0;
    
    public TreeGUI(){
        setPreferredSize(new Dimension(500, 500));  //the size of the picture is 500,500 (from Drawer.java)size only can set here
    }
    
    void setinfixExpression(String ex){
        infixExpression = ex;
        repaint();
    }
    
    protected void paintComponent (Graphics g){
        //ensure that the viewing area is cleared before a new drawing is displayed
        super.paintComponent(g);//need to use super paint component to show all the thg to jApplet
        Graphics2D gg = (Graphics2D) g;
        b = new BTree <String> ();
        
        //border of circle
        gg.translate(getWidth()/2, 10);
        //set font of operands and operators
        Font f = new Font("Tahoma", Font.BOLD, 20);
        gg.setFont(f);
        
        //if the infix expression is not empty
        if (infixExpression != ""){ //the infix expressio must be fill to proceed to next step
           b.constructTree(infixExpression);
           new Builder().buildTree(b.root, xCoordinate, 20, 35*b.space, gg);
        }
    }
    
    class Builder<Tree> extends BTree <Tree> {
        void buildTree(Node <Tree> node, int xCoordinate, int yCoordinate, int space, Graphics2D gg){
            if (node != null){
                //set colors for the lines for left and right child 
                gg.setColor(Color.BLACK);//line between parent node and children node
                if (node.left != null){
                    gg.drawLine(xCoordinate + 10, yCoordinate + 20, (xCoordinate-space) + 10, yCoordinate + 110 + 20);
                }
                if (node.right != null){
                    gg.drawLine(xCoordinate + 10, yCoordinate + 20, (xCoordinate+space) + 10, yCoordinate + 110 + 20);
                }
                
                //set background of circle
                gg.setColor(Color.BLACK);//circle fill colour
                gg.fill(new Arc2D.Double(xCoordinate, yCoordinate, 30, 30, 90, 360, Arc2D.Double.OPEN));
                
                //set elements of circle (drawString and color)
                gg.setColor(Color.WHITE);//Operand + Operator(String)for the font inside the circle
                gg.drawString(node.data.toString(), (xCoordinate) + 7, yCoordinate + 20);
                
                //recursion
                buildTree(node.left, xCoordinate - space, yCoordinate + 110, space/2, gg);//for left node
                buildTree(node.right, xCoordinate + space, yCoordinate + 110, space/2, gg);//for right node
            }
        }
    }
}