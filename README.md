# Presa_Predador_Master

Foi gerado um tabuleiro para controle de movimentação e uma
matriz 30x30, através da classe Tela.

Cada célula será preenchida com valores, controlado através da
classe Casa:

* 0- Vazia

* 1 até 4 – Rastro do predador

* 10 predados

* 20 presas

Foram geradas classes de predadores e presas com os atributos
necessários para controle de cada agente.

A geração inicial do tabuleiro é realizada de forma randômica,
validando apenas se a célula da tabela está liberada.

O sistema valida os atributos de cada agente(caçando, fugindo,
rastro, velocidade) e avalia o campo de percepção do mesmo.

O sistema grava em uma matriz auxiliar um ambiente futuro com os
movimentos cada atividade do agente.

No ambiente futuro ele valida se o movimento do agente pode ser
realizado, validando a ocupação da célula, o caminho que
deverá ser percorrido e se existem dois agentes tentando ocupar o
mesmo lugar.

Com base na matriz auxiliar gerada, no caso o ambiente futuro, é
atualizado o ambiente geral através das classes tela e movimentação.
