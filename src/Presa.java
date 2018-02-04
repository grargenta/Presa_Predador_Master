import javax.swing.ImageIcon;
import java.util.Random;
import java.util.ArrayList;


public class Presa {

	private int velocidade;
	private int iteracoes;
	private boolean fugindo;
	private boolean fugindoFuturo;
	private int QE;
	private int IE;
	private int linha, coluna;	
	private int linhaFuturo;
	private int colunaFuturo;
	private boolean morreu;
	private Random random;
	private int direcao;
	
	public Presa(){
		
	}
	
	public Presa(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
		this.QE = 1;
		this.IE = 1;
				 
	}

	public int getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
	}

	public int getIteracoes() {
		return iteracoes;
	}

	public void setIteracoes(int iteracoes) {
		this.iteracoes = iteracoes;
	}

	public boolean isFugindo() {
		return fugindo;
	}

	public void setFugindo(boolean fugindo) {
		this.fugindo = fugindo;
	}

	public int getQE() {
		return QE;
	}

	public void setQE(int qE) {
		QE = qE;
	}

	public int getIE() {
		return IE;
	}

	public void setIE(int iE) {
		IE = iE;
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
	
	public int getDirecao() {
		return direcao;
	}
	
	public boolean isFugindoFuturo() {
		return fugindoFuturo;
	}

	public void setFugindoFuturo(boolean fugindoFuturo) {
		this.fugindoFuturo = fugindoFuturo;
	}

	public boolean isMorreu() {
		return morreu;
	}

	public void setMorreu(boolean morreu) {
		this.morreu = morreu;
	}

	public void analiseMovimento(Casa[][] tabuleiro){
		
		int dir, npresaslivres = 0, npresasfugindo = 0, npredadores = 0, pos, nit = 0, iteracoes = 0; 
		boolean presasfugindo[][] = new boolean[3][3];
		boolean predadores[][] = new boolean[3][3];
		
		//verifica posiçoes dentro do campo de percepçao da presa
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
					case 10: //predador
						npredadores++;
						predadores[i+1][j+1] = true; //armazena pos do predador encontrado
						this.fugindo = true;
						break;
					case 20: //presa
						if(tabuleiro[linham][colunam].getPresa().fugindo == true)
						{
							npresasfugindo++;
							presasfugindo[i+1][j+1]=true; //armazena pos da presa fugindo encontrada
						}
						else
						{
							npresaslivres++;
						}
						break;
					}
		    	}
			}
		}
				
		//testa se morreu
		if (npredadores>=4)
			{this.setMorreu(true);}
		
		//Atualiza estado emocional 
		//Quando detecta outra presa livre, QE++. 
		QE = QE + npresaslivres; 
		//Quando detecta um predador, QE-2 IE+2, dois ou mais predadores ao mesmo tempo, QE-3 IE+3. 
		if (npredadores == 1)
		{
			QE = QE-2;
			IE = IE+2;
		}
		if (npredadores>=2)
		{
			QE = QE-3;
			IE = IE+3;
		}
		//- Quando detecta outra presa fugindo, QE-1 e IE++. (presa fugindo detecta presa livre também). 
 		if (npresasfugindo>=1)
 		{
 			QE--;
 			IE++;
 		}
		//- Se QE<0, comportamento de fuga.
 		if (QE<0)
 		{
 			fugindo=true;
 		}
		//- Após n interações (a definir) sem detectar nada, QE++ até 1 e IE-- até 0.
		if((npresasfugindo==0)&&(npresaslivres==0)&&(npredadores==0))
		{
			nit++; //4 iteracoes sem detectar nada
			if (nit >= 4)
			{
				if(QE<=0)
				{
					QE++;
				}
				if(IE>=1)
				{
					IE--;
				}
				if((IE==0)&&(QE==1))
				{
					this.setFugindo(false);
					nit = 0;
				}
				else
				{
					this.setFugindo(true);
				}
				
			}
		}
		if (QE<-3)
			{QE=-3;}
		if (QE>3)
			{QE=3;}
		if (IE<0)
			{IE=0;}
		if (IE>3)
			{IE=3;}
		
		// QE = 1, IE = 1  velocidade de uma celula por iteracao
	    // QE = -3, IE = 3 -- velocidade máxima
				int rv = IE - QE;
				switch(rv)
				{
				case 6: 
					this.setVelocidade(4);
					if (iteracoes == 0)
					{
						iteracoes = 8;
					}
					break;
				case 4: 
					this.setVelocidade(3);
					break;
				case 2:
					this.setVelocidade(2);
					break;
				default:
					this.setVelocidade(1);
					break;
				}
				if (velocidade == 4)
				{
					iteracoes--;
					if (iteracoes == 0)
					{
						QE++; //mantem a velocidade maxima por oito iteracoes
					}
				}
				else
				{
					iteracoes = 0;
				}

	    if((fugindo == false)&&(morreu == false)){ // Se bem belo passeando
			
			random = new Random();
			dir = random.nextInt(5);
			this.direcao = dir;
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
	    if((fugindo == true)&&(morreu == false)){ //Se esta fugindo
		//a) se ela detecta um predador, ela se desloca na direção inversa ao predador mais próximo dela.
		//b) se ela detecta uma presa que mudou de cor, ela procura uma posição afastada dela.
			random = new Random();
			int testapos = 0;
			ArrayList<Integer> avaliadirecoes = new ArrayList<Integer>(); // 0-d 1-e 2-c 3-b 
			ArrayList<Integer> direcoes = new ArrayList<Integer>(); // 0-d 1-e 2-c 3-b 
			boolean ok = false;
			
			//avalia os movimentos nas quatro direcoes
			avaliadirecoes.clear();
			//avalia a direita
			if ((predadores[0][2]==false)&&(predadores[1][2]==false)&&(predadores[2][2]==false))
			{
				if (((predadores[0][2]==true)||(predadores[2][2]==true))&&(predadores[1][2]==false))
				{
					avaliadirecoes.add(1);
				}
				else
				{
					avaliadirecoes.add(2);
				}
			}
			else
			{
				avaliadirecoes.add(0);
			}
			
			//avalia esquerda
			if ((predadores[0][0]==false)&&(predadores[1][0]==false)&&(predadores[2][0]==false))
			{
				if (((predadores[0][0]==true)||(predadores[2][0]==true))&&(predadores[1][0]==false))
				{
					avaliadirecoes.add(1);
				}
				else
				{
					avaliadirecoes.add(2);
				}
			}
			else
			{
				avaliadirecoes.add(0);
			}
			
			//avalia cima
			if ((predadores[0][0]==false)&&(predadores[0][1]==false)&&(predadores[0][2]==false))
			{
				if (((predadores[0][0]==true)||(predadores[0][2]==true))&&(predadores[0][1]==false))
				{
					avaliadirecoes.add(1);
				}
				else
				{
					avaliadirecoes.add(2);
				}
			}
			else
			{
				avaliadirecoes.add(0);
			}
			
			//avalia baixo
			if ((predadores[2][0]==false)&&(predadores[2][1]==false)&&(predadores[2][2]==false))
			{
				if (((predadores[2][0]==true)||(predadores[2][2]==true))&&(predadores[2][1]==false))
				{
					avaliadirecoes.add(1);
				}
				else
				{
					avaliadirecoes.add(2);
				}
			}
			else
			{
				avaliadirecoes.add(0);
			}
			
			do
			{
				//define a melhor direção
				direcoes.clear();
				for(int i=0; i<=3; i++)
				{
					if (avaliadirecoes.get(i)==2)
					{
						direcoes.add(i);
					}
				}
				if(direcoes.isEmpty())
				{
					for(int i=0; i<=3; i++)
					{
						if (avaliadirecoes.get(i)==1)
						{
							direcoes.add(i);
						}
					}
				}
				if (direcoes.isEmpty())
				{
					this.setMorreu(true);
					ok = true;
				}
				else
				{
					dir = random.nextInt(direcoes.size());
					this.direcao = direcoes.get(dir);
					switch (direcoes.get(dir))
					{
					case 0: //direita
						
							linhaFuturo = this.getLinha();
							colunaFuturo = this.getColuna()+velocidade;
							if (linhaFuturo < 0)
								linhaFuturo = linhaFuturo+30;
							if (linhaFuturo > 29)
								linhaFuturo = linhaFuturo-30;
							if (colunaFuturo < 0)
								colunaFuturo = colunaFuturo+30;
							if (colunaFuturo > 29)
								colunaFuturo = colunaFuturo-30;
							for (int i=1; i<=velocidade+1; i++)
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
								
								if ((tabuleiro[linhaf][colunaf].getConteudo()==20)||(tabuleiro[linhaf][colunaf].getConteudo()==10))
								{
									testapos++;
								}
							}
							if (testapos!=0)
							{
								testapos = 0;
								avaliadirecoes.set(0,0);
							}
							else
							{
								ok = true;
							}
							break;
						
					case 1: //esquerda
						
							linhaFuturo = this.getLinha();
							colunaFuturo = this.getColuna()-velocidade;
							if (linhaFuturo < 0)
								linhaFuturo = linhaFuturo+30;
							if (linhaFuturo > 29)
								linhaFuturo = linhaFuturo-30;
							if (colunaFuturo < 0)
								colunaFuturo = colunaFuturo+30;
							if (colunaFuturo > 29)
								colunaFuturo = colunaFuturo-30;
							for (int i=1; i<=velocidade+1; i++)
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
							
								if ((tabuleiro[linhaf][colunaf].getConteudo()==20)||(tabuleiro[linhaf][colunaf].getConteudo()==10))
								{
									testapos++;
								}
							}
							if (testapos!=0)
							{
								testapos = 0;
								avaliadirecoes.set(1,0);
							}
							else
							{
								ok = true;
							}
							break;
					case 2: //cima
					
							linhaFuturo = this.getLinha()-velocidade;
							colunaFuturo = this.getColuna();
							if (linhaFuturo < 0)
								linhaFuturo = linhaFuturo+30;
							if (linhaFuturo > 29)
								linhaFuturo = linhaFuturo-30;
							if (colunaFuturo < 0)
								colunaFuturo = colunaFuturo+30;
							if (colunaFuturo > 29)
								colunaFuturo = colunaFuturo-30;
							for (int i=1; i<=velocidade+1; i++)
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
							
								if ((tabuleiro[linhaf][colunaf].getConteudo()==20)||(tabuleiro[linhaf][colunaf].getConteudo()==10))
								{
									testapos++;
								}
							}
							if (testapos!=0)
							{
								testapos = 0;
								avaliadirecoes.set(2,0);
							}
							else
							{
								ok = true;
							}
							break;
					case 3: //baixo
					
							linhaFuturo = this.getLinha()+velocidade;
							colunaFuturo = this.getColuna();
							if (linhaFuturo < 0)
								linhaFuturo = linhaFuturo+30;
							if (linhaFuturo > 29)
								linhaFuturo = linhaFuturo-30;
							if (colunaFuturo < 0)
								colunaFuturo = colunaFuturo+30;
							if (colunaFuturo > 29)
								colunaFuturo = colunaFuturo-30;
							for (int i=1; i<=velocidade+1; i++)
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
							
								if ((tabuleiro[linhaf][colunaf].getConteudo()==20)||(tabuleiro[linhaf][colunaf].getConteudo()==10))
								{
									testapos++;
								}
							}
							if (testapos!=0)
							{
								testapos = 0;
								avaliadirecoes.set(3,0);
							}
							else
							{
								ok = true;
							}
							break;
					default:
							break;
					}	
				}
				
			}while(ok == false);
			
				
	}}
	
	public void aplicaMovimento(Casa[][] tabuleiro, int [][] tabuleiroFuturo){
		
		tabuleiro[this.getLinha()][this.getColuna()].setConteudo(0); //20 = presa
		tabuleiro[this.getLinha()][this.getColuna()].setPresa(this);
		/* Se for igual a 1 somente este agente movimentou para esta casa sendo assim um movimento valido  */
		if(tabuleiroFuturo[this.getLinhaFuturo()][this.getColunaFuturo()] == 1){  
			this.linha = this.linhaFuturo;
			this.coluna = this.colunaFuturo;
		}
		tabuleiro[this.getLinha()][this.getColuna()].setConteudo(20); //20 = presa
		tabuleiro[this.getLinha()][this.getColuna()].setPresa(this);
		
		if(fugindo){
			tabuleiro[this.getLinha()][this.getColuna()].setIcon(new ImageIcon("images/ovelhaVermelha.jpg"));
			if(iteracoes > 0)
			{
				this.iteracoes--; //Decrementa o nr de iteracoes
			}
			else 
				fugindo = false; //Acabou as iteracoes acabou a fuga
		}
		else{
			tabuleiro[this.getLinha()][this.getColuna()].setIcon(new ImageIcon("images/ovelha.jpg"));
			if(fugindoFuturo){ //Verifica se o agente encontrou predador e ira entrar no estado de fuga
				this.fugindo = this.fugindoFuturo;
				this.fugindoFuturo = false;
				this.iteracoes = 8; //Numero de iteracoes de fuga
			}
		}
				 	
		/* Ajusta dados dos atributos internos */
		this.linhaFuturo = 0;
		this.colunaFuturo = 0;
	}		
	
	}

