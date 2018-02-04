import java.util.Random;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Predador {
	
	private int velocidade;
	private int iteracoes;
	private boolean atacando;
	private int linha;
	private int coluna;
	private int linhaFuturo;
	private int colunaFuturo;
	private int passos;
	private int direcao;
	private boolean atacandoFuturo;
	private Random random;
	
	public Predador(){
		
	}
	
	public Predador(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}

	public int getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
	}

	public int getPassos()
	{
		return passos;
	}
	
	public void setPassos(int p)
	{
		this.passos = p;
	}
	
	public int getDirecao()
	{
		return direcao;
	}
	
	public void setDirecao(int d)
	{
		this.direcao = d;
	}
	
	public int getIteracoes() {
		return iteracoes;
	}

	public void setIteracoes(int iteracoes) {
		this.iteracoes = iteracoes;
	}

	public boolean isAtacando() {
		return atacando;
	}

	public void setAtacando(boolean atacando) {
		this.atacando = atacando;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public int getLinhaFuturo() {
		return linhaFuturo;
	}

	public void setLinhaFuturo(int linhaFuturo) {
		this.linhaFuturo = linhaFuturo;
	}

	public int getColunaFuturo() {
		return colunaFuturo;
	}

	public void setColunaFuturo(int colunaFuturo) {
		this.colunaFuturo = colunaFuturo;
	}
	
	public boolean isAtacandoFuturo() {
		return atacandoFuturo;
	}

	public void setAtacandoFuturo(boolean atacandoFuturo) {
		this.atacandoFuturo = atacandoFuturo;
	}

	public void analiseMovimento(Casa[][] tabuleiro){
		
		int dir, dirataque = 0, pos, testapos = 0;
		ArrayList<Presa> npresas = new ArrayList<Presa>();
		boolean presas[][] = new boolean[3][3];
		boolean predadores[][] = new boolean[3][3];
		boolean rastros[][] = new boolean[3][3];
		//verifica posiçoes dentro do campo de percepçao do predador
	    //0 - vazio, 1 a 4 rastro, 10 predador, 20 presa
		for (int i=-1; i<=1; i++)
		{
		    for(int j=-1; j<=1; j++)
			{
		    	if ((i==0)&&(j==0));
		    	else
		    	{
		    		int linham = this.linha+i;
		    		int colunam = this.coluna+j;
		    		if (linham < 0)
						linham = linham+30;
					if (linham > 29)
						linham = linham-30;
					if (colunam < 0)
						colunam = colunam+30;
					if (colunam > 29)
						colunam = colunam-30;

		    		pos = tabuleiro[linham][colunam].getConteudo();	
					switch (pos)
					{
					case 1: //rastro
						rastros[i+1][j+1] = true;	
						break;
					case 2: //rastro
						rastros[i+1][j+1] = true;
						break;
					case 3: //rastro
						rastros[i+1][j+1] = true;
						break;
					case 4: //rastro
						rastros[i+1][j+1] = true;
						break;
					case 10: //predador
						predadores[i+1][j+1] = true; //armazena pos do predador encontrado
						break;
					case 20: //presa
						presas[i+1][j+1]=true; //armazena pos da presa encontrada
						npresas.add(tabuleiro[linham][colunam].getPresa());
						if (iteracoes == 0)
						{
							this.iteracoes = 4;
							this.atacando = true;
						}
						break;
					}
		    	}
			}
		}
		
		if(!atacando){ // Se bem belo passeando
			
			velocidade = 1;
			random = new Random();
			dir = random.nextInt(5);
			switch (dir){
			case 0: //parado
				linhaFuturo = this.getLinha();
				colunaFuturo = this.getColuna();			
	     		break;
			case 1: //direita
				linhaFuturo = this.getLinha();
				colunaFuturo = this.getColuna()+velocidade;	
				break;
			case 2: //esquerda
				linhaFuturo = this.getLinha();
				colunaFuturo = this.getColuna()-velocidade;
				break;
			case 3: //cima
				linhaFuturo = this.getLinha()-velocidade;
				colunaFuturo = this.getColuna();
				break;
			case 4: //baixo
				linhaFuturo = this.getLinha()+velocidade;
				colunaFuturo = this.getColuna();
				break;
			default:
		    	break;
			}
			if (linhaFuturo < 0)
				linhaFuturo = linhaFuturo+30;
			if (linhaFuturo > 29)
				linhaFuturo = linhaFuturo-30;
			if (colunaFuturo < 0)
				colunaFuturo = colunaFuturo+30;
			if (colunaFuturo > 29)
				colunaFuturo = colunaFuturo-30;

		}
		else{ // Se esta caçando
			//mantem a velocidade igual a da presa por 4 iteracoes
			random = new Random();
			if (npresas.isEmpty())
				{
				dirataque = 4;
				this.iteracoes = 0;
				}
			else
			{
				Presa presaselec = npresas.get(random.nextInt(npresas.size()));
				//velocidade da presa
				velocidade = presaselec.getVelocidade();
				//direcao da presa
				dirataque = presaselec.getDirecao();
				this.setDirecao(dirataque);
			}
			
			switch(dirataque){
			case 0: //direita
				for (int i=1; i<=velocidade+1; i++)
				{
					int linhaf = this.getLinha();
					int colunaf = this.getColuna()+i;
					boolean bloqueio = false;
					if (linhaf < 0)
						linhaf = linhaf+30;
					if (linhaf > 29)
						linhaf = linhaf-30;
					if (colunaf < 0)
						colunaf = colunaf+30;
					if (colunaf > 29)
						colunaf = colunaf-30;
					
					if ((tabuleiro[linhaf][colunaf].getConteudo()!=20)||(tabuleiro[linhaf][colunaf].getConteudo()!=10)&&(bloqueio == false))
					{
						testapos++;
					}
					else
					{
						bloqueio = true;
					}
				}
				if (presas[1][2] == true) //para nao sobrepor a presa no movimento futuro
				{
					testapos--;
				}
				
				linhaFuturo = this.getLinha();
				colunaFuturo = this.getColuna()+testapos;
				if (linhaFuturo < 0)
					linhaFuturo = linhaFuturo+30;
				if (linhaFuturo > 29)
					linhaFuturo = linhaFuturo-30;
				if (colunaFuturo < 0)
					colunaFuturo = colunaFuturo+30;
				if (colunaFuturo > 29)
					colunaFuturo = colunaFuturo-30;
				this.setPassos(testapos);
				testapos = 0;
				break;
			case 1: //esquerda
				for (int i=1; i<=velocidade+1; i++)
				{
					int linhaf = this.getLinha();
					int colunaf = this.getColuna()-i;
					boolean bloqueio = false;
					if (linhaf < 0)
						linhaf = linhaf+30;
					if (linhaf > 29)
						linhaf = linhaf-30;
					if (colunaf < 0)
						colunaf = colunaf+30;
					if (colunaf > 29)
						colunaf = colunaf-30;
					
					if ((tabuleiro[linhaf][colunaf].getConteudo()!=20)||(tabuleiro[linhaf][colunaf].getConteudo()!=10)&&(bloqueio == false))
					{
						testapos++;
					}
					else
					{
						bloqueio = true;
					}
				}
				if (presas[1][0] == true) //para nao sobrepor a presa no movimento futuro
				{
					testapos--;
				}
				linhaFuturo = this.getLinha();
				colunaFuturo = this.getColuna()-testapos;
				if (linhaFuturo < 0)
					linhaFuturo = linhaFuturo+30;
				if (linhaFuturo > 29)
					linhaFuturo = linhaFuturo-30;
				if (colunaFuturo < 0)
					colunaFuturo = colunaFuturo+30;
				if (colunaFuturo > 29)
					colunaFuturo = colunaFuturo-30;
				this.setPassos(testapos);
				testapos = 0;
				break;
			case 2: //cima
				for (int i=1; i<=velocidade+1; i++)
				{
					int linhaf = this.getLinha()-i;
					int colunaf = this.getColuna();
					boolean bloqueio = false;
					if (linhaf < 0)
						linhaf = linhaf+30;
					if (linhaf > 29)
						linhaf = linhaf-30;
					if (colunaf < 0)
						colunaf = colunaf+30;
					if (colunaf > 29)
						colunaf = colunaf-30;
					
					if ((tabuleiro[linhaf][colunaf].getConteudo()!=20)||(tabuleiro[linhaf][colunaf].getConteudo()!=10)&&(bloqueio == false))
					{
						testapos++;
					}
					else
					{
						bloqueio = true;
					}
				}
				if (presas[0][1] == true) //para nao sobrepor a presa no movimento futuro
				{
					testapos--;
				}
				linhaFuturo = this.getLinha()-testapos;
				colunaFuturo = this.getColuna();
				if (linhaFuturo < 0)
					linhaFuturo = linhaFuturo+30;
				if (linhaFuturo > 29)
					linhaFuturo = linhaFuturo-30;
				if (colunaFuturo < 0)
					colunaFuturo = colunaFuturo+30;
				if (colunaFuturo > 29)
					colunaFuturo = colunaFuturo-30;
				this.setPassos(testapos);
				testapos = 0;
				break;
			case 3: //baixo
				for (int i=1; i<=velocidade+1; i++)
				{
					int linhaf = this.getLinha()+i;
					int colunaf = this.getColuna();
					boolean bloqueio = false;
					if (linhaf < 0)
						linhaf = linhaf+30;
					if (linhaf > 29)
						linhaf = linhaf-30;
					if (colunaf < 0)
						colunaf = colunaf+30;
					if (colunaf > 29)
						colunaf = colunaf-30;
					
					if ((tabuleiro[linhaf][colunaf].getConteudo()!=20)||(tabuleiro[linhaf][colunaf].getConteudo()!=10)&&(bloqueio == false))
					{
						testapos++;
					}
					else
					{
						bloqueio = true;
					}
				}
				if (presas[2][1] == true) //para nao sobrepor a presa no movimento futuro
				{
					testapos--;
				}
				linhaFuturo = this.getLinha()+testapos;
				colunaFuturo = this.getColuna();
				if (linhaFuturo < 0)
					linhaFuturo = linhaFuturo+30;
				if (linhaFuturo > 29)
					linhaFuturo = linhaFuturo-30;
				if (colunaFuturo < 0)
					colunaFuturo = colunaFuturo+30;
				if (colunaFuturo > 29)
					colunaFuturo = colunaFuturo-30;
				this.setPassos(testapos);
				testapos = 0;
				break;				
			case 4:
				linhaFuturo = this.getLinha();
				colunaFuturo = this.getColuna();
				if (linhaFuturo < 0)
					linhaFuturo = linhaFuturo+30;
				if (linhaFuturo > 29)
					linhaFuturo = linhaFuturo-30;
				if (colunaFuturo < 0)
					colunaFuturo = colunaFuturo+30;
				if (colunaFuturo > 29)
					colunaFuturo = colunaFuturo-30;
				this.setPassos(0);
		    	break;
		    default:
		    		break;
			}
		}
	}
	public void aplicaMovimento(Casa[][] tabuleiro, int [][] tabuleiroFuturo){
		if ((this.velocidade>0)&&(this.atacando == true))
		{	
			switch(this.direcao){
			case 0: //direita
				
				for(int i = 0; i<=this.getPassos();i++)
				{
					int linhaf = this.getLinha();
					int colunaf = this.getColuna()+i;
					if (linhaf < 0)
						linhaf = linhaf+30;
					if (linhaf > 29)
						linhaf = linhaf-30;
					if (colunaf < 0)
						colunaf = colunaf+30;
					if (colunaf > 29)
						colunaf = colunaf-30;

					tabuleiro[linhaf][colunaf].setConteudo(5);
					tabuleiro[linhaf][colunaf].setPredador(this);
				}
				break;
			case 1: //esquerda
				for(int i = 0; i<=this.getPassos();i++)
				{
					int linhaf = this.getLinha();
					int colunaf = this.getColuna()-i;
					if (linhaf < 0)
						linhaf = linhaf+30;
					if (linhaf > 29)
						linhaf = linhaf-30;
					if (colunaf < 0)
						colunaf = colunaf+30;
					if (colunaf > 29)
						colunaf = colunaf-30;
					tabuleiro[linhaf][colunaf].setConteudo(5);
					tabuleiro[linhaf][colunaf].setPredador(this);
				}
				break;
			case 2: //cima
				for(int i = 0; i<=this.getPassos();i++)
				{
					int linhaf = this.getLinha()-i;
					int colunaf = this.getColuna();
					if (linhaf < 0)
						linhaf = linhaf+30;
					if (linhaf > 29)
						linhaf = linhaf-30;
					if (colunaf < 0)
						colunaf = colunaf+30;
					if (colunaf > 29)
						colunaf = colunaf-30;
					tabuleiro[linhaf][colunaf].setConteudo(5);
					tabuleiro[linhaf][colunaf].setPredador(this);
				}
				break;
			case 3: //baixo
				for(int i = 0; i<=this.getPassos();i++)
				{
					int linhaf = this.getLinha()+i;
					int colunaf = this.getColuna();
					if (linhaf < 0)
						linhaf = linhaf+30;
					if (linhaf > 29)
						linhaf = linhaf-30;
					if (colunaf < 0)
						colunaf = colunaf+30;
					if (colunaf > 29)
						colunaf = colunaf-30;
					tabuleiro[linhaf][colunaf].setConteudo(5);
					tabuleiro[linhaf][colunaf].setPredador(this);
				}
				break;				
			case 4:
				break;
			default:
		    	break;
			}
			
			if(iteracoes>0)
			{
				this.iteracoes--; //Decrementa o nr de iteracoes
				System.out.println(iteracoes);
			}
			else 
				atacando = false; //Acabaram as iteracoes acabou o ataque
		}
		else
			{
			tabuleiro[this.getLinha()][this.getColuna()].setConteudo(0);
			tabuleiro[this.getLinha()][this.getColuna()].setPredador(this);
			}
		
		/* Se for igual a 1 somente este agente movimentou para esta casa sendo assim um movimento valido  */
		if(tabuleiroFuturo[this.getLinhaFuturo()][this.getColunaFuturo()] == 1){  
			this.linha = this.linhaFuturo;
			this.coluna = this.colunaFuturo;
		}
		tabuleiro[this.getLinha()][this.getColuna()].setConteudo(10); //10 = predador
		tabuleiro[this.getLinha()][this.getColuna()].setPredador(this);
		tabuleiro[this.getLinha()][this.getColuna()].setIcon(new ImageIcon("images/tigre.png"));
			
		/* Ajusta dados dos atributos internos */
		this.linhaFuturo = 0;
		this.colunaFuturo = 0;
		}
}

