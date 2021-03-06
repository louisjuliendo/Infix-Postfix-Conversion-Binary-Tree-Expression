/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment2Final;
import java.util.*;

/**
 *
 * @author Windows
 */
public class BTree <Tree> {
    Node <Tree> root;
    int space = 1;
    
    static class Node <Tree>{
        Tree data;
        Node <Tree> left, right;
        
        public Node(Tree data){
            this.data = data;
            left = right = null;
        }
        
        public String toString(){
            return data.toString();
        }
    }
    
    public BTree() {
        root = null;
    }
    
    
    private boolean isOperator (String ch) {            
            switch(ch){
                case "+": 
                case "-":
                case "*":
                case "/":
                    return true;                    
            }
            return false;
    }
    
    public String checkPrecedence (String expression){
        String o [] = expression.split("\\s");

        for (int i = 0; i < o.length; i++) {
             String str = o[i];
            if ((str.equals("+") || str.equals("-"))) {
                return str;
            }
        }
                
        for (int i = 0; i < o.length; i++) {
             String str = o[i];
            if ((str.equals("*") || str.equals("/"))) {
                return str;
            }
        }
            return expression;
    }
    

    void constructTree (String expression){
        root = constructTree(expression, root, space);
    }
    
    Node <Tree> constructTree (String expression, Node<Tree> node, int space){
        String o = checkPrecedence(expression);
        if (isOperator(o)){
            node = new Node <Tree> ((Tree) o);
            String leftrightchild [] = expression.split("\\" + o, 2);
            String leftchild = leftrightchild [0];
            String rightchild = leftrightchild [1];
            node.left = constructTree(leftchild, node.left, space++);
            node.right = constructTree(rightchild, node.right, space++);
            return node;
        }
        //else {
//            if (this.space < space) 
//                this.space = space;
                return new Node <Tree> ((Tree)o);
        //}     
    }
}
