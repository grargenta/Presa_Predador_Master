import java.util.ArrayList;

public class Movimentacao {
	private ArrayList<Predador> predadores;
	private ArrayList<Presa> presas;
	private Casa[][] tabuleiro;
	private int[][] tabuleiroFuturo;
	private Predador predador;
	private Presa presa;
	
	public Movimentacao(){
		
	}
	
	public Casa[][] verifica(ArrayList<Predador> predadores, ArrayList<Presa> presas, Casa[][] tabuleiro){
		/* Recebe os itens da classe da tela */
		this.predadores = predadores;
		this.presas = presas;
		this.tabuleiro = tabuleiro;
		
		/* Cria tabuleiro que ira possuir os movimentos futuros */
		tabuleiroFuturo = new int[30][30];
		
		//Percorre presa
		for(int i = 0; i < presas.size(); i++){
			presa = presas.get(i);
						
			presa.analiseMovimento(tabuleiro);
			
			tabuleiroFuturo[presa.getLinhaFuturo()][presa.getColunaFuturo()]++; /* Soma a casa */
			
		}

		//Percorre predador
		for(int i = 0; i < predadores.size(); i++){
			predador = predadores.get(i);
			
			predador.analiseMovimento(tabuleiro);
			
			tabuleiroFuturo[predador.getLinhaFuturo()][predador.getColunaFuturo()]++; /* Soma a casa */
			
		}

		aplicaMovimento();
		
		return this.tabuleiro;
	}
	
	public void aplicaMovimento(){
		
		//Percorre presa
		for(int i = 0; i < presas.size(); i++){
			presa = presas.get(i);
			if(presa.isMorreu()){
				tabuleiro[presa.getLinha()][presa.getColuna()].setConteudo(0);
				presas.remove(i);
			}
			else presa.aplicaMovimento(tabuleiro, tabuleiroFuturo);
		}
		
		//Percorre predador
		for(int i = 0; i < predadores.size(); i++){
			predador = predadores.get(i);
			predador.aplicaMovimento(tabuleiro, tabuleiroFuturo);
		}

		/* Atualiza os rastros dos predadores, pois uma vez que este foi aplicado pelo predador no primeiro instante
		   a intensidade dos rastros passara a ser controle do ambiente */
		for (int i = 0; i < tabuleiro.length; i++) {
		   	for(int j = 0; j < tabuleiro.length; j++){
		        if(tabuleiro[i][j].getConteudo() > 0 && tabuleiro[i][j].getConteudo() < 6){ //intervalo de valores dos rastros de predadores
		        	tabuleiro[i][j].setConteudo(tabuleiro[i][j].getConteudo() - 1); //Atualiza valor demonstrando a diminuicao do rastro que ocorre com o tempo
		        }
		  	}
		}
	}
}
