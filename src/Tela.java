import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tela {
	private JFrame frame;
	private Casa[][] tabuleiro;
	private Random random;
	private ArrayList<Predador> predadores;
	private ArrayList<Presa> presas;
	private int qtdePredador, qtdePresa, iteracoes, segundos;
	private Movimentacao movimentacao;
	private Presa presa;
	private Predador predador;
	
	public Tela(){
		movimentacao = new Movimentacao();
		qtdePredador = 40; //Qtde inicial de predadores
	    qtdePresa = 10; //Qtde inicial de presas
	    iteracoes = 500; //Total de iterações
	    segundos = 3; //Tempo entre cada atualizacao
		
		montaTela();
		
		while(iteracoes > 0 && qtdePresa > 0){
			try{
				//Aguarda uma qtde de segundos antes de atualizar os movimentos
				TimeUnit.SECONDS.sleep(segundos);
			}catch(Exception e){
				System.out.println("Erro: " + e.getMessage());
			}
			
			//Chama a classe movimentacao para ajustar movimentos conforme as regras
			tabuleiro = movimentacao.verifica(predadores, presas, tabuleiro);
							
			//Atualiza a tela, percorrendo as casas e desenhando os comportamentos definidos na classe movimentacao
			imprimeTela(); 
			
			//Decrementa a iteracao realizada
			iteracoes--;
			
			if (presa.isMorreu())
				qtdePresa--;
			
			//System.out.println("iteracao: " + iteracoes);
			if ((qtdePresa == 0)||(iteracoes == 0))
			{
				System.out.println("Final...");
			}
		}
	}
	
	private void montaTela(){
		boolean criou = false;
		
		//Monta tela
		frame = new JFrame("Predador-Presa");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        
		tabuleiro = new Casa[30][30];
		
		JPanel tabuleiroPanel = new JPanel();
	    tabuleiroPanel.setBackground(Color.black);
	    tabuleiroPanel.setLayout(new GridLayout(30, 30, 1, 1));
	    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int componentWidth = frame.getWidth();
        int componentHeight = frame.getHeight();
        
        frame.getContentPane().add(tabuleiroPanel, "Center");
        frame.setBounds((screenSize.width-componentWidth)/2, (screenSize.height-componentHeight)/2, componentWidth, componentHeight);
        frame.setVisible(true);
        frame.setResizable(false);
        
	    //Cria os predadores e presas
	    int linha, coluna;
	    
	    random = new Random();
	    predadores = new ArrayList<Predador>();
	    presas = new ArrayList<Presa>();
	    
	    for (int i = 0; i < tabuleiro.length; i++) {
	    	for(int j = 0; j < tabuleiro.length; j++){
	            tabuleiro[i][j] = new Casa();
	            tabuleiroPanel.add(tabuleiro[i][j]);
	    	}
	    }
	   
	    /* Cria predadores */
	    for(int i = 0; i < qtdePredador; i++) {
	    	criou = false;
	    	
	    	while(!criou){
	    		linha = random.nextInt(30);
		    	coluna = random.nextInt(30);
		    	
		    	if(tabuleiro[linha][coluna].getConteudo() == 0){
		    		tabuleiro[linha][coluna].setIcon(new ImageIcon("images/tigre.png"));
		    		tabuleiro[linha][coluna].setConteudo(10);// 10 = predador
		    		
		    		predador = new Predador(linha, coluna);
		    		
		    		//Adiciona o predador no array e na casa do tabuleiro para poder consultar depois
		    		predadores.add(predador);
		    		tabuleiro[linha][coluna].setPredador(predador);
		    		
		    		criou = true;
		    	}
	    	}
	    }
	    
	    /* Cria presa */
	    for(int i = 0; i < qtdePresa; i++) {
	    	criou = false;
	    	
	    	while(!criou){
	    		linha = random.nextInt(30);
		    	coluna = random.nextInt(30);
		    	
		    	if(tabuleiro[linha][coluna].getConteudo() == 0){
		    		tabuleiro[linha][coluna].setIcon(new ImageIcon("images/ovelha.jpg"));
		    		tabuleiro[linha][coluna].setConteudo(20);// 20 = presa
		    		
		    		presa = new Presa(linha, coluna);
		    		
		    		//Adiciona a presa no array e na casa do tabuleiro para poder consultar depois
		    		presas.add(presa);
		    		tabuleiro[linha][coluna].setPresa(presa);
		    		
		    		criou = true;		    				    		
		    	}
	    	}
	    }
	}
	
	private void imprimeTela(){
		for (int i = 0; i < tabuleiro.length; i++) {
	    	for(int j = 0; j < tabuleiro.length; j++){
	    		switch(tabuleiro[i][j].getConteudo()){
			    	case 0: //Casa sem nada
		 				   tabuleiro[i][j].setIcon(new ImageIcon());
		 				   break;
	    			case 1: //Rastro sumindo
	    				   tabuleiro[i][j].setIcon(new ImageIcon("images/rastro1.png"));
	    				   break;
	    			case 2: //Rastro quase sumindo
	    				   tabuleiro[i][j].setIcon(new ImageIcon("images/rastro2.png"));
	    				   break;
	    			case 3: //Rastro enfraquecendo
	    				   tabuleiro[i][j].setIcon(new ImageIcon("images/rastro3.png"));
	    				   break;
	    			case 4: //Rastro recente
	    				   tabuleiro[i][j].setIcon(new ImageIcon("images/rastro4.png"));
	    				   break;
	    		}
	    	}
	    }
	}
	
	public static void main(String[] args) {
		new Tela();
	}
}