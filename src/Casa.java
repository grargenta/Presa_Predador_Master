import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//Casa do tabuleiro
public class Casa extends JPanel {

	private static final long serialVersionUID = 1L;
	JLabel label = new JLabel((Icon)null);
	int conteudo; //0 - vazio, 1 a 4 rastro, 10 predador, 20 presa
	private Predador predador;
	private Presa presa;
	
    public Casa() {
        setBackground(Color.white);
        add(label);
        conteudo = 0;
    }

    public void setIcon(Icon icon) {
        label.setIcon(icon);
    }
    
    public int getConteudo(){
    	return conteudo;
    }
    
    public void setConteudo(int conteudo){
    	this.conteudo = conteudo;
    }
    
    public void setPredador(Predador predador){
    	this.predador = predador;
    	this.presa = null;
    }
    
    public Predador getPredador(){
    	return predador;
    }

    public void setPresa(Presa presa){
    	this.presa = presa;
    	this.predador = null;
    }
    
    public Presa getPresa(){
    	return this.presa;
    }
}