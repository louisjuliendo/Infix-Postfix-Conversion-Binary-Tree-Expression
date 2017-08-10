
import java.util.Stack;
//import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class Calculator extends javax.swing.JFrame {

    String inputs = "";
    String outputTojtfPostFix = "";
    String outputTojtfEvaluation = "";

    public Calculator() {
        initComponents();
    }

   
    private boolean isOperator(char operator) { //also used for exception handling
        if (operator == '+' || operator == '-' || operator == '*' || 
            operator == '/' || operator == '^') {
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
    

    private boolean checkPrecedence(char O1, char O2) { //check order of precedence(charRead,s.peek(last add in))
        if ((O2 == '+' || O2 == '-') && (O1 == '+' || O1 == '-')) {
            return true;
        } else if ((O2 == '*' || O2 == '/') && (O1 == '+' || O1 == '-' || //if 02>01 ,then 02
                    O1 == '*' || O1 == '/')) {
            return true;
        } else if ((O2 == '^') && (O1 == '+' || O1 == '-' || O1 == '*' || //if 02>01 ,then 02
                    O1 == '/')) {
            return true;
        } else {
            return false;
        }
    }

    private String stackFormat(String s) { //remove (comma,space and [] )inside the infix convert to postfix result
        s = s.replaceAll(",", "");  //removes all comma in stack
        s = s.replaceAll(" ", "");  //removes all spaces in stack
        s = s.substring(1, s.length() - 1);  //removes the [] from stack

        return s;
    }

    private String stackFormatEva(String sEva) { //remove comma and [] inside the evaluation result
        sEva = sEva.replaceAll(",", " ");  //replace all comma with spaces in stack
        sEva = sEva.substring(1, sEva.length() - 1);  //removes the [] from stack

        return sEva;
    }

     //code of infix to postfix (steps)
    public String Convert(String input) {
        Stack<Character> s = new Stack<>(); //every number is a character inside stack
        String output = ""; //declare output as empty first
        s.push(' ');    //to compare empty space " " so to know that the stack is empty (when meet ' ')
        int stepPostfix = 0; //indicates the number of steps
        
        for (int i = 0; i < input.length(); i += 2) {//plus 2 because of the spaces added
            char charRead = input.charAt(i);    //each loop the charread,will =+2(include space)
            stepPostfix++;                      //the step of the postfix ++
            if (isOperator(charRead)) {     //if the scanned character is an operator
                while (checkPrecedence(charRead, s.peek())) {//compare precedence
                    output += s.pop();      //check operator precedence and pop the biggest precedence, the smallest remain
                }
                s.push(charRead);           //push the smallest one to stack (because biggest ady pop)
            } else if (charRead == '(') {   //if charRead is ( then push to stack
                s.push(charRead);           //push (
            } else if (charRead == ')') {   //if the character read is )

                while (s.peek() != '(') {   //when the the peek is not ( then continue pop until meet (
                    output += s.pop(); //pop until it meets '('
                }
                if (s.peek() == '(') {
                    s.pop();//last in first out (LIFO) eg.1+( << ( is the top/peek . so we pop (
                }
            } else {
                output += charRead; //if scanned character is an operand, pop directly to the output
            }
            String jtfPostFix_Step = ""; 
            String jtfPostFix_charRead = "";
            String jtfPostFix_Stack = "";
            String jtfPostFix_Output = "";
            String jtfPostFix_Main = "";

            jtfPostFix_Step = ("Step " + (stepPostfix));
            jtfPostFix_charRead = ("Character read: " + charRead);
            jtfPostFix_Stack = ("Stack: " + stackFormat(s.toString()));
            jtfPostFix_Output = ("Output String: " + output);
            
             //combining all strings into one jtfPostFix (per step) 
            jtfPostFix_Main = String.format(jtfPostFix_Step + "%n" + 
                    jtfPostFix_charRead + "%n" + jtfPostFix_Stack
                    + "%n" + jtfPostFix_Output + "%n%n");

            outputTojtfPostFix += jtfPostFix_Main;
        }
        while (!s.isEmpty()) {
            output += s.pop();
        }
        return output;
    }

    //code for postfix evaluation
    private boolean isOperatorEva(char op) {

        if (op == '*' || op == '/' || op == '+' || op == '-' || op == '^') {
            return true;
        } else {
            return false;
        }
    }
    
    //convert postfix to infix (steps) 
    public int ConvertEva(String input) {
        Stack<Integer> sEva = new Stack<>();
        int outputEva = 0;
        String outputEva2 = "";//empty string on the steps when charReadEva is not operator(the charRead is operand)
        int stepEva = 0;
        
        //scanning of every character
        for (int i = 0; i < input.length() - 1; i++) {//postfix dont hv space so no need to +=2,-1 because is to remove the space since i is start from 0
            char charReadEva = input.charAt(i);
            stepEva++; //indicates the number of steps
            int a = 0;//number1
            int b = 0;//number2
            if (!isOperatorEva(charReadEva)) { //is number
                sEva.push(Character.getNumericValue(charReadEva));//charRead(character)->Integer(need to convert to integer)
            } else {    //is operator
                b = sEva.pop();//a = number
                a = sEva.pop();//b = number
                switch (charReadEva) {
                    case '+':   //if operator is + then sum all number 1 and number 2
                        outputEva = a + b;
                        sEva.push(outputEva);
                        break;
                    case '-':   //if operator is - then number 1 minus number 2
                        outputEva = a - b;
                        sEva.push(outputEva);
                        break;
                    case '*':
                        outputEva = a * b;  //if operator is * then number 1 multiply number 2
                        sEva.push(outputEva);
                        break;
                    case '/':
                        outputEva = a / b;  //if operator is / then number 1 devide number 2
                        sEva.push(outputEva);
                        break;
                    case '^':
                        outputEva = (int) Math.pow(a, b);//netbean dont have ^ function so use Math.pow(a,b)
                        sEva.push(outputEva);   //if operator is ^ then number 1 power number 2
                        break;
                    default:
                        System.out.println("Error");
                }
            }

            String jtfEvaluation_Step = "";
            String jtfEvaluation_charRead = "";
            String jtfEvaluation_Stack = "";
            String jtfEvaluation_Operation = "";
            String jtfEvaluation_Main = "";

            if (!isOperator(charReadEva)) { //if the charRead is not *operator*(is operand)
                jtfEvaluation_Step      = ("Step " + (stepEva));
                jtfEvaluation_charRead  = ("Character read: " + charReadEva);
                jtfEvaluation_Stack     = ("Stack: " + stackFormatEva(sEva.toString()));
                jtfEvaluation_Operation = ("Operation: " + outputEva2);//just show the operation[before last step]

                jtfEvaluation_Main = String.format(jtfEvaluation_Step + "%n" + 
                        jtfEvaluation_charRead + "%n" + jtfEvaluation_Stack
                        + "%n" + jtfEvaluation_Operation + "%n%n");

                outputTojtfEvaluation += jtfEvaluation_Main;
            } else {    // the charRead is *operator* [this is for the last step]
                jtfEvaluation_Step      = ("Step " + (stepEva));
                jtfEvaluation_charRead  = ("Character read: " + charReadEva);
                jtfEvaluation_Stack     = ("Stack: " + stackFormatEva(sEva.toString()));
                jtfEvaluation_Operation = ("Operation: " + a + " " + charReadEva
                        + " " + b + " = " + outputEva);//for the last step of convertion

                jtfEvaluation_Main = String.format(jtfEvaluation_Step + "%n" + 
                        jtfEvaluation_charRead + "%n" + jtfEvaluation_Stack
                        + "%n" + jtfEvaluation_Operation + "%n%n");

                outputTojtfEvaluation += jtfEvaluation_Main;
            }
        }
        return outputEva;
    }
    
    //exception handling (error checking)
    public boolean checkInput(String input) {
        String temp = "";
        temp = input.replaceAll(" ", ""); //remove spaces
        
        //scan every character to check if character is odd or even
        for (int i = 0; i < temp.length(); i++) {
            if (i % 2 == 0) { //if character is even (even number only can be operand=number)
                if (!isNumeric(temp.charAt(i))) { //if character is not numeric
                    return true;
                }
                else{//the character still is even
                    if(isNumeric(temp.charAt(0)) && temp.length()== 1){//if character is numeric
                        return true;
                    }
                }
            } 
            else if (i % 2 != 0 || i == temp.length()-1) { //if character is odd [because a%2 !=0]
                if (!isOperator(temp.charAt(i))) { //if character is not an operator(odd number only can be operator)
                    return true;
                }    
                else{
                    if(isOperator(temp.charAt(temp.length()-1))){   //the last digit cannot be operator
                        return true;//return true only can inform user last number cannot be operator
                    }
                }
            }
        }
        return false;
    }
    
 /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlblPostfix = new javax.swing.JLabel();
        jlblEvaluationofPostfix = new javax.swing.JLabel();
        jlblInfixExpression = new javax.swing.JLabel();
        jbtEvaluate = new javax.swing.JButton();
        jtfInfixExpression = new javax.swing.JTextField();
        jbtConvertPostfix = new javax.swing.JButton();
        jtfPostfixOutput = new javax.swing.JTextField();
        jtfEvaluation = new javax.swing.JTextField();
        jlblCalculatorTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jbt3 = new javax.swing.JButton();
        jbt2 = new javax.swing.JButton();
        jbt1 = new javax.swing.JButton();
        jbt0 = new javax.swing.JButton();
        jbt4 = new javax.swing.JButton();
        jbt5 = new javax.swing.JButton();
        jbt6 = new javax.swing.JButton();
        jbt7 = new javax.swing.JButton();
        jbt8 = new javax.swing.JButton();
        jbt9 = new javax.swing.JButton();
        jbtDivision = new javax.swing.JButton();
        jbtMultiplication = new javax.swing.JButton();
        jbtClear = new javax.swing.JButton();
        jbtAddition = new javax.swing.JButton();
        jbtSubtraction = new javax.swing.JButton();
        jtbOpenBracket = new javax.swing.JButton();
        jbtCloseBracket = new javax.swing.JButton();
        jbtPower = new javax.swing.JButton();
        btnClearAll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Calculator");

        jlblPostfix.setText("Postfix Output:");

        jlblEvaluationofPostfix.setText("Evaluation of Postfix Output:");

        jlblInfixExpression.setText("Infix Expression:");

        jbtEvaluate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbtEvaluate.setText("Evaluate Postfix");
        jbtEvaluate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtEvaluateActionPerformed(evt);
            }
        });

        jtfInfixExpression.setEditable(true);
        jtfInfixExpression.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfInfixExpressionActionPerformed(evt);
            }
        });

        jbtConvertPostfix.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jbtConvertPostfix.setText("Convert to Postfix");
        jbtConvertPostfix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtConvertPostfixActionPerformed(evt);
            }
        });

        jtfPostfixOutput.setEditable(false);
        jtfPostfixOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfPostfixOutputActionPerformed(evt);
            }
        });

        jtfEvaluation.setEditable(false);

        jlblCalculatorTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jlblCalculatorTitle.setText("Calculator");

        jbt3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbt3.setText("3");
        jbt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt3ActionPerformed(evt);
            }
        });

        jbt2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbt2.setText("2");
        jbt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt2ActionPerformed(evt);
            }
        });

        jbt1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbt1.setText("1");
        jbt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt1ActionPerformed(evt);
            }
        });

        jbt0.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbt0.setText("0");
        jbt0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt0ActionPerformed(evt);
            }
        });

        jbt4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbt4.setText("4");
        jbt4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt4ActionPerformed(evt);
            }
        });

        jbt5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbt5.setText("5");
        jbt5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt5ActionPerformed(evt);
            }
        });

        jbt6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbt6.setText("6");
        jbt6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt6ActionPerformed(evt);
            }
        });

        jbt7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbt7.setText("7");
        jbt7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt7ActionPerformed(evt);
            }
        });

        jbt8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbt8.setText("8");
        jbt8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt8ActionPerformed(evt);
            }
        });

        jbt9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbt9.setText("9");
        jbt9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt9ActionPerformed(evt);
            }
        });

        jbtDivision.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtDivision.setText("/");
        jbtDivision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtDivisionActionPerformed(evt);
            }
        });

        jbtMultiplication.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtMultiplication.setText("*");
        jbtMultiplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtMultiplicationActionPerformed(evt);
            }
        });

        jbtClear.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtClear.setText("C");
        jbtClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtClearActionPerformed(evt);
            }
        });

        jbtAddition.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtAddition.setText("+");
        jbtAddition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAdditionActionPerformed(evt);
            }
        });

        jbtSubtraction.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtSubtraction.setText("-");
        jbtSubtraction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSubtractionActionPerformed(evt);
            }
        });

        jtbOpenBracket.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtbOpenBracket.setText("(");
        jtbOpenBracket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtbOpenBracketActionPerformed(evt);
            }
        });

        jbtCloseBracket.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtCloseBracket.setText(")");
        jbtCloseBracket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCloseBracketActionPerformed(evt);
            }
        });

        jbtPower.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtPower.setText("^");
        jbtPower.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtPowerActionPerformed(evt);
            }
        });

        btnClearAll.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnClearAll.setText("CA");
        btnClearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbt4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jbt5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbt6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtMultiplication, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jbtClear, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jbt0, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jtbOpenBracket, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbtCloseBracket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jbt1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jbt7, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jbt8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jbtPower, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtAddition, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jbt9, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jbtDivision, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbtSubtraction, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnClearAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbt8, jbt9, jbtPower});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtAddition, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtPower, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jbtCloseBracket, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtbOpenBracket, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbt8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbt9, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtSubtraction, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbt7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbt4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbt5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbt6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtMultiplication, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbt1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbt2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbt3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtDivision, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClearAll)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbt0, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtClear, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnClearAll, jbt0, jbt1, jbt4});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jbt7, jbt8, jbtCloseBracket, jtbOpenBracket});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jbt2, jbt3});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbtConvertPostfix)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtEvaluate, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jlblEvaluationofPostfix)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfEvaluation))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlblInfixExpression)
                                    .addComponent(jlblPostfix))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtfInfixExpression)
                                    .addComponent(jtfPostfixOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jlblCalculatorTitle)
                        .addGap(87, 87, 87))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblCalculatorTitle)
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblInfixExpression)
                    .addComponent(jtfInfixExpression, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblPostfix)
                    .addComponent(jtfPostfixOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblEvaluationofPostfix)
                    .addComponent(jtfEvaluation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtConvertPostfix)
                    .addComponent(jbtEvaluate))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtfInfixExpressionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfInfixExpressionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfInfixExpressionActionPerformed

    private void jbtConvertPostfixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConvertPostfixActionPerformed
        String input = jtfInfixExpression.getText();
 
        if (input.isEmpty()) {  //cannot be empty in infix
            JOptionPane.showMessageDialog(null, "No infix expression to be converted.");
            
        } else if (checkInput(inputs) || checkInput(jtfInfixExpression.getText())) {  //error checking
            JOptionPane.showMessageDialog(null, "Invalid infix input."); //if user enter string instead of integer
            jtfInfixExpression.setText("");
            
        } else if ((inputs.contains("/ 0")) || (jtfInfixExpression.getText().contains("/ 0"))|| ((jtfInfixExpression.getText().contains("/0")))) {
            JOptionPane.showMessageDialog(null, "Cannot divide by 0. (undefined)");//any number cannot devide by 0
            jtfInfixExpression.setText("");
            
        } 
          else if (jtfInfixExpression.getText().matches("[a-zA-Z]+")) {     //check for alphabet(a-z)/(A-Z)
            JOptionPane.showMessageDialog(null, "Invalid infix input.");    //if got alphabet reset the textfield
            jtfInfixExpression.setText("");
        }
        else {
            String postfixOutput = Convert(input);  //convert the infix to postfix that user key in

            jtfPostfixOutput.setText(postfixOutput);//set the postfix output to the txtfield

            InfixToPostfix w2 = new InfixToPostfix();
            InfixToPostfix.jtfInfix.setText(input);//set text to the [frame infix to postfix] txtfield
            InfixToPostfix.jtfPostFix.setText(outputTojtfPostFix + 
                    "The Final Postfix Expression Is : " + postfixOutput);

            w2.setVisible(true);
            w2.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_jbtConvertPostfixActionPerformed

    private void jtfPostfixOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfPostfixOutputActionPerformed

    }//GEN-LAST:event_jtfPostfixOutputActionPerformed

    private void jbt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt3ActionPerformed
        inputs += jbt3.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbt3ActionPerformed

    private void jbt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt2ActionPerformed
        inputs += jbt2.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbt2ActionPerformed

    private void jbt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt1ActionPerformed
        inputs += jbt1.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbt1ActionPerformed

    private void jbt0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt0ActionPerformed
        inputs += jbt0.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbt0ActionPerformed

    private void jbt4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt4ActionPerformed
        inputs += jbt4.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbt4ActionPerformed

    private void jbt5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt5ActionPerformed
        inputs += jbt5.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbt5ActionPerformed

    private void jbt6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt6ActionPerformed
        inputs += jbt6.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbt6ActionPerformed

    private void jbt7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt7ActionPerformed
        inputs += jbt7.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbt7ActionPerformed

    private void jbt8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt8ActionPerformed
        inputs += jbt8.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbt8ActionPerformed

    private void jbt9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt9ActionPerformed
        inputs += jbt9.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbt9ActionPerformed

    private void jbtSubtractionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSubtractionActionPerformed
        inputs += jbtSubtraction.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbtSubtractionActionPerformed

    private void jbtAdditionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAdditionActionPerformed
        inputs += jbtAddition.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbtAdditionActionPerformed

    private void jbtClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtClearActionPerformed
        if ("".equals(inputs)) {    //also can chg to inputs.equals("")
            JOptionPane.showMessageDialog(null, "No infix expression to be cleared.");
        } else {    //if got operator or operand to clear
            int temp = inputs.length() - 2;//to remove space and (operator or operand)
            inputs = inputs.substring(0, temp);//clear the input and fill(temp) it again(starting point,how many need to take)
            jtfInfixExpression.setText(inputs); // then set the input to txtfield
        }
    }//GEN-LAST:event_jbtClearActionPerformed

    private void jbtDivisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtDivisionActionPerformed
        inputs += jbtDivision.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbtDivisionActionPerformed

    private void jbtMultiplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtMultiplicationActionPerformed
        inputs += jbtMultiplication.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbtMultiplicationActionPerformed

    private void jtbOpenBracketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtbOpenBracketActionPerformed
        inputs += jtbOpenBracket.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jtbOpenBracketActionPerformed

    private void jbtCloseBracketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCloseBracketActionPerformed
        inputs += jbtCloseBracket.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbtCloseBracketActionPerformed

    private void jbtPowerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtPowerActionPerformed
        inputs += jbtPower.getText() + " ";
        jtfInfixExpression.setText(inputs);
    }//GEN-LAST:event_jbtPowerActionPerformed

    private void btnClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearAllActionPerformed
//         if ("".equals(inputs)) {
//            JOptionPane.showMessageDialog(null, "No infix expression to be cleared.");
//        }
//         else{
        outputTojtfEvaluation = "";
        outputTojtfPostFix = "";
        jtfInfixExpression.setText("");
        jtfPostfixOutput.setText("");
        jtfEvaluation.setText("");
        inputs = "";
//         }
    }//GEN-LAST:event_btnClearAllActionPerformed

    private void jbtEvaluateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtEvaluateActionPerformed
        String inputEva = jtfPostfixOutput.getText();

        if (inputEva.isEmpty()) { //if the input is ""/empty
            JOptionPane.showMessageDialog(null, "No postfix expresstion to be evaluated.");
        } else {
            int outputEva = ConvertEva(inputEva);
            String evaluation = Integer.toString(outputEva);

            jtfEvaluation.setText(evaluation);

            EvaluationOfPostfix w3 = new EvaluationOfPostfix();
            EvaluationOfPostfix.jtfInfix.setText(inputEva);
            EvaluationOfPostfix.jtfEvaluation.setText(outputTojtfEvaluation + 
                    "The result of evaluation of the postfix expression is : " + outputEva);

            w3.setVisible(true);
            w3.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_jbtEvaluateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Calculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Calculator f1 = new Calculator();
                f1.setVisible(true);
                f1.setLocationRelativeTo(null);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearAll;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbt0;
    private javax.swing.JButton jbt1;
    private javax.swing.JButton jbt2;
    private javax.swing.JButton jbt3;
    private javax.swing.JButton jbt4;
    private javax.swing.JButton jbt5;
    private javax.swing.JButton jbt6;
    private javax.swing.JButton jbt7;
    private javax.swing.JButton jbt8;
    private javax.swing.JButton jbt9;
    private javax.swing.JButton jbtAddition;
    private javax.swing.JButton jbtClear;
    private javax.swing.JButton jbtCloseBracket;
    private javax.swing.JButton jbtConvertPostfix;
    private javax.swing.JButton jbtDivision;
    private javax.swing.JButton jbtEvaluate;
    private javax.swing.JButton jbtMultiplication;
    private javax.swing.JButton jbtPower;
    private javax.swing.JButton jbtSubtraction;
    private javax.swing.JLabel jlblCalculatorTitle;
    private javax.swing.JLabel jlblEvaluationofPostfix;
    private javax.swing.JLabel jlblInfixExpression;
    private javax.swing.JLabel jlblPostfix;
    private javax.swing.JButton jtbOpenBracket;
    private javax.swing.JTextField jtfEvaluation;
    public javax.swing.JTextField jtfInfixExpression;
    private javax.swing.JTextField jtfPostfixOutput;
    // End of variables declaration//GEN-END:variables
}
