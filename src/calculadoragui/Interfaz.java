package calculadoragui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class Interfaz implements ActionListener{

    JTextField campotexto1, campotexto2;
    Panel pantalla, botonesmemoria, botonesoperaciones;
    JPanel teclado, botonesnumericos;
    JButton memoryclear, memoryrecall, memorystorage, mMas, mMenos, numeros[], operaciones[];
    String[] operadores = {"R", "C", "+", "/", "-" ,"*", "="};
    String ax = "";
    float digito1, digito2, resultado, memoria;//variables para las operaciones
    int tipOperaciones; //para controlar el tipo de operacion que se realiza
    boolean t=false;//control sobre escribir un nuevo numero despues de alguna operacion cambia a true cuando se ha realizado una operacion

    public Interfaz(){

        JFrame jfMain = new JFrame("Calculator");
        jfMain.setLayout(new BorderLayout(4, 4));

        norte();
        sur();

        jfMain.add(pantalla, BorderLayout.NORTH);
        jfMain.add(teclado, BorderLayout.CENTER);

        jfMain.setLocation(100, 80);
        jfMain.setResizable(false);
        jfMain.setVisible(true);
        jfMain.setSize(300, 380);
        jfMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void norte(){

        pantalla = new Panel(null);        

        campotexto1 = new JTextField("");
        campotexto2 = new JTextField("0");

        campotexto1.setHorizontalAlignment(JTextField.RIGHT); 
        campotexto2.setHorizontalAlignment(JTextField.RIGHT); 

        //Quitar bordes a los campos de texto
        campotexto1.setBorder(BorderFactory.createLineBorder(Color.white));
        campotexto2.setBorder(BorderFactory.createLineBorder(Color.white));

        //desabilitando los campos de texto
        campotexto1.setEditable(false);
        campotexto2.setEditable(false);

        campotexto1.setBackground(Color.white);
        campotexto2.setBackground(Color.white);

        pantalla.add(campotexto1); pantalla.add(campotexto2);

        campotexto1.setBounds(35, 10, 200, 15);
        campotexto2.setBounds(35, 25, 200, 30);

        pantalla.setSize(270, 47);
        pantalla.setVisible(true);

    }

    public void sur(){

        teclado = new JPanel(new BorderLayout(6, 50));
        teclado.setLayout(new BorderLayout(4, 4));

        botMem();
        botNum();
        botOpe();

        teclado.add(botonesmemoria, BorderLayout.NORTH);  
        teclado.add(botonesnumericos, BorderLayout.CENTER); 
        teclado.add(botonesoperaciones, BorderLayout.EAST); 

        teclado.setSize(270, 330);
    }

    public void botMem(){

        botonesmemoria = new Panel(null);

        memoryclear = new JButton("MC");  memoryrecall = new JButton("MR");
        memorystorage = new JButton("MS"); mMas = new JButton("M+");
        mMenos = new JButton("M-");

        memoryclear.setFont(new Font("Arial", Font.BOLD, 11));
        memoryrecall.setFont(new Font("Arial", Font.BOLD, 11));
        memorystorage.setFont(new Font("Arial", Font.BOLD, 11));
        mMas.setFont(new Font("Arial", Font.BOLD, 11));
        mMenos.setFont(new Font("Arial", Font.BOLD, 11));

        memoryclear.setMargin(new Insets(1, 1, 1, 1)); memoryrecall.setMargin(new Insets(1, 1, 1, 1));
        memorystorage.setMargin(new Insets(1, 1, 1, 1)); mMas.setMargin(new Insets(1, 1, 1, 1));
        mMenos.setMargin(new Insets(1, 1, 1, 1)); 

        memoryclear.setBounds(35, 0, 33, 33); memoryrecall.setBounds(78, 0, 33, 33); memorystorage.setBounds(121, 0, 33, 33);
        mMas.setBounds(164, 0, 33, 33); mMenos.setBounds(207, 0, 33, 33);

        botonesmemoria.add(memoryclear); botonesmemoria.add(memoryrecall); botonesmemoria.add(memorystorage); botonesmemoria.add(mMas); botonesmemoria.add(mMenos);

        memoryclear.addActionListener(this); memoryrecall.addActionListener(this); memorystorage.addActionListener(this);
        mMas.addActionListener(this); mMenos.addActionListener(this);

        botonesmemoria.setSize(270, 45);
        botonesmemoria.setVisible(true);        
    }

    public void botNum(){

        botonesnumericos = new JPanel(null);

        int nx3=121, nx2=121, nx1=121, n3y=0, n2y=43, n1y=86;
        numeros = new JButton[11];

        //*****************************************
        //bloque para crear los botones, añadirlos y asignar numeros
        for (int i=0; i<=10; i++){

            if(i<=9){
                numeros[i] = new JButton(""+i);
                botonesnumericos.add(numeros[i]);
                numeros[i].setMargin(new Insets(1, 1, 1, 1));
                numeros[i].addActionListener(this);  
            }
            else{
                numeros[i] = new JButton(".");
                botonesnumericos.add(numeros[i]);
                numeros[i].setMargin(new Insets(1, 1, 1, 1));
                numeros[i].addActionListener(this);
            }
        }

        //******************************************
        //bloque para posicionar botones
        for(int i=10; i>=0; i--){

            if(i==10){
                numeros[i].setBounds(121, 129, 35, 35);
            }
            else{
                if(i<=9 && i>=7){
                    numeros[i].setBounds(nx3, n3y, 35, 35);
                    nx3-=43;
                }
                else if(i<=6 && i>=4){   
                    n3y+=43;                    
                    numeros[i].setBounds(nx2, n2y, 35, 35);
                    nx2-=43;
                }
                else if(i<=3 && i>=1){
                    n3y+=43;                    
                    numeros[i].setBounds(nx1, n1y, 35, 35);
                    nx1-=43;
                }
                else if(i==0){
                    numeros[i].setBounds(35, 129, 78, 35);                    
                }
            }                
        }

        botonesnumericos.setSize(170, 150);
        botonesnumericos.setVisible(true);
    }

    public void botOpe(){

        botonesoperaciones = new Panel(null);

        int c=0, x=0, y=0;

        operaciones = new JButton[7];

        for(int i=0; i<=6; i++){
            if(c<=1){

                operaciones[i] = new JButton(operadores[i]);
                botonesoperaciones.add(operaciones[i]);

                    operaciones[i].setBounds(x, y, 30, 35);

                    operaciones[i].setMargin(new Insets(1, 1, 1, 1));
                    operaciones[i].addActionListener(this);
                    x+=33;
                    c++;               
            }
            else{
                if(i==6){
                    x=0; y+=43;
                    operaciones[i] = new JButton(operadores[i]);
                    botonesoperaciones.add(operaciones[i]);

                    operaciones[i].setBounds(x, y, 65, 35);

                    operaciones[i].setMargin(new Insets(1, 1, 1, 1));
                    operaciones[i].addActionListener(this);
                    x+=33;
                    c++;
                }
                else{
                    c=0;
                    x=0; y+=43;
                    operaciones[i] = new JButton(operadores[i]);
                    botonesoperaciones.add(operaciones[i]);

                    operaciones[i].setBounds(x, y, 30, 35);

                    operaciones[i].setMargin(new Insets(1, 1, 1, 1));
                    operaciones[i].addActionListener(this);
                    x+=33;
                    c++;   
                }                             
            }                

        }

        botonesoperaciones.setVisible(true);
        botonesoperaciones.setSize(120, 200);
    }

    public boolean isN(String ax){

        try{
            int n = Integer.parseInt(ax);
            return true;
        }catch(NumberFormatException e){
            return false;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String  op="";

        if(isN(e.getActionCommand())){ //cuando se oprimen numeros

            if(campotexto1.getText().equals("")){
                ax += e.getActionCommand();
                campotexto2.setText(ax);
            }
            else{
                if(tipOperaciones==0){
                    if(t){
                        ax=""; 

                        campotexto1.setText(campotexto2.getText());                        
                        ax += e.getActionCommand();
                        campotexto2.setText(ax);    
                        t = false;
                    }
                    else{
                        ax="";
                        ax += campotexto2.getText()+e.getActionCommand();
                        campotexto2.setText(ax);
                    }                
                }else{
                    ax="";
                    ax += campotexto2.getText()+e.getActionCommand();
                    campotexto2.setText(ax);
                }
            }            
        }
        else{//cuando se oprime el resto de botones

            if(e.getActionCommand().equals("R") ){
                campotexto1.setText("");
                Float a = Float.parseFloat(campotexto2.getText());
                campotexto2.setText(""+Math.sqrt(a)); 
            }
            if(e.getActionCommand().equals("C") ){ //para reiniciar valores y limpiar pantalla
                tipOperaciones=0; digito1 = 0; digito2 =0; resultado=0; campotexto1.setText(""); campotexto2.setText("0"); ax="";
            }   
            if(e.getActionCommand().equals("MC")){//para limpiar la memoria de la calculadora
                memorystorage.setForeground(Color.black);
                campotexto1.setText(""); campotexto2.setText("0");
                memoria=0;
            }
            if(e.getActionCommand().equals("MR")){//para mostrar valor almacenado en la memoria
                campotexto1.setText("");
                campotexto2.setText(String.valueOf(memoria));
            }if(e.getActionCommand().equals("MS")){//guardar un valor en la memoria
                memorystorage.setForeground(Color.red);
                memoria = Float.parseFloat(campotexto2.getText());
            }
            if(e.getActionCommand().equals("M+")){//sumar valor de la pantalla con el valor de la memoria
                memoria += Float.parseFloat(campotexto2.getText());
            }
            if(e.getActionCommand().equals("M-")){//restar valor de la pantalla con el valor de la memoria
                memoria -= Float.parseFloat(campotexto2.getText());
            }    
            if(e.getActionCommand().equals(".")){//usar el punto para los decimales
                ax="";
                if(numeros[10].isEnabled()){
                    numeros[10].setEnabled(false);
                    ax = campotexto2.getText() +".";
                    campotexto2.setText(ax);
                }
            }
            if(e.getActionCommand().equals("+") ){//boton suma
                numeros[10].setEnabled(true);
                ax="";
                if(tipOperaciones==1){

                }else if(tipOperaciones==0 ){//validacion para no chocar con otras operaciones
                        if(campotexto1.getText().equals("") ){
                            digito1 = Float.parseFloat(campotexto2.getText());                    
                            ax += campotexto1.getText()+campotexto2.getText();
                            campotexto1.setText(ax+" + ");
                            campotexto2.setText("");
                            tipOperaciones = 1;
                        }
                        else {
                            if(!t){//validacion para nueva operacion
                                digito1 = Float.parseFloat(campotexto2.getText());                    
                                ax += campotexto2.getText();
                                campotexto1.setText(ax+" + ");
                                campotexto2.setText("");
                                tipOperaciones = 1;
                            }
                            else{//usar otras operaciones con la suma
                                digito1 = Float.parseFloat(campotexto2.getText());                    
                                ax += campotexto1.getText();
                                campotexto1.setText(ax+" + ");
                                campotexto2.setText("");
                                tipOperaciones = 1;
                            }
                        }
                    }                     
             }
                if(e.getActionCommand().equals("-") ){//cuando se decide restar
                    numeros[10].setEnabled(true);
                    ax="";
                    if(tipOperaciones==2){

                    }else if(tipOperaciones==0){//validacion para no chocar con otras operaciones
                        if(campotexto1.getText().equals("")){
                            digito1 = Float.parseFloat(campotexto2.getText());                    
                            ax += campotexto1.getText()+ campotexto2.getText();
                            campotexto1.setText(ax+" - ");
                            campotexto2.setText("");
                            tipOperaciones = 2;
                        }
                        else{
                            if(!t){//validacion para nueva operacion
                                digito1 = Float.parseFloat(campotexto2.getText());                    
                                ax += campotexto2.getText();
                                campotexto1.setText(ax+" - ");
                                campotexto2.setText("");
                                tipOperaciones = 2;
                            }
                            else{//usar otras operaciones con la suma
                                digito1 = Float.parseFloat(campotexto2.getText());                    
                                ax += campotexto1.getText();
                                campotexto1.setText(ax+" - ");
                                campotexto2.setText("");
                                tipOperaciones = 2;
                            }
                        }
                    }                    
                }
            operacionmultiplicar(e);
                if(e.getActionCommand().equals("/") ){//cuando se decide dividir
                    numeros[10].setEnabled(true);
                    ax="";
                    if(tipOperaciones==4){

                    }else if(tipOperaciones==0){//validacion para no chocar con otras operaciones
                        if(campotexto1.getText().equals("")){
                            digito1 = Float.parseFloat(campotexto2.getText());                    
                            ax += campotexto1.getText()+campotexto2.getText();
                            campotexto1.setText(ax+" / ");
                            campotexto2.setText("");
                            tipOperaciones = 4;
                        }
                        else{
                            if(!t){//validacion para nueva operacion
                                digito1 = Float.parseFloat(campotexto2.getText());                    
                                ax += campotexto2.getText();
                                campotexto1.setText(ax+" / ");
                                campotexto2.setText("");
                                tipOperaciones = 4;
                            }
                            else{//usar otras operaciones con la suma
                                digito1 = Float.parseFloat(campotexto2.getText());                    
                                ax += campotexto1.getText();
                                campotexto1.setText(ax+" / ");
                                campotexto2.setText("");
                                tipOperaciones = 4;
                            }
                        }
                    }                      
                }
                if(e.getActionCommand().equals("=") && !campotexto2.getText().equals("")){
                    t = true;
                    if(tipOperaciones==1){//operacion para la suma
                        tipOperaciones = 0;
                        ax="";
                        ax+=campotexto1.getText() + campotexto2.getText();
                        campotexto1.setText(ax);
                        digito2 = Float.parseFloat(campotexto2.getText());
                        resultado=digito1+digito2;
                        campotexto2.setText(String.valueOf(resultado));
                    }
                    else if(tipOperaciones==2){ //operacion para la resta
                        tipOperaciones = 0;
                        ax="";
                        ax+=campotexto1.getText()+campotexto2.getText();
                        campotexto1.setText(ax);
                        digito2 = Float.parseFloat(campotexto2.getText());
                        resultado=digito1-digito2;
                        campotexto2.setText(String.valueOf(resultado));
                    }
                    if(tipOperaciones==3){ //operacion para la multiplicacion
                        tipOperaciones = 0;
                        ax="";
                        ax+=campotexto1.getText()+campotexto2.getText();
                        campotexto1.setText(ax);
                        digito2 = Float.parseFloat(campotexto2.getText());
                        resultado=digito1*digito2;
                        campotexto2.setText(String.valueOf(resultado));
                    }
                    if(tipOperaciones==4){ //operacion para la división
                        if(Float.parseFloat(campotexto2.getText())!=0){
                            tipOperaciones = 0;
                            ax="";
                            ax+=campotexto1.getText()+campotexto2.getText();
                            campotexto1.setText(ax);
                            digito2 = Float.parseFloat(campotexto2.getText());
                            resultado=digito1/digito2;
                            campotexto2.setText(String.valueOf(resultado));
                        }
                        
                    }
                }
        }        
    }       

    public void operacionmultiplicar(ActionEvent e) throws NumberFormatException {
        if(e.getActionCommand().equals("*") ){//cuando se decide multiplicar
            numeros[10].setEnabled(true);
            ax="";
            if(tipOperaciones==3){
                
            }else if(tipOperaciones==0){//validacion para no chocar con otras operaciones
                if(campotexto1.getText().equals("")){
                    digito1 = Float.parseFloat(campotexto2.getText());
                    ax += campotexto1.getText()+campotexto2.getText();
                    campotexto1.setText(ax+" * ");
                    campotexto2.setText("");
                    tipOperaciones = 3;
                }
                else{
                    if(!t){//validacion para nueva operacion
                        digito1 = Float.parseFloat(campotexto2.getText());
                        ax += campotexto2.getText();
                        campotexto1.setText(ax+" * ");
                        campotexto2.setText("");
                        tipOperaciones = 3;
                    }
                    else{//usar otras operaciones con la suma
                        digito1 = Float.parseFloat(campotexto2.getText());
                        ax += campotexto1.getText();
                        campotexto1.setText(ax+" * ");
                        campotexto2.setText("");
                        tipOperaciones = 3;
                    }
                }
            }
        }
    }
}