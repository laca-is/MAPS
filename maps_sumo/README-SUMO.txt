Este trabalho utilizou a vers�o 0.27.1 da ferramenta SUMO. A ferramenta pode ser obtida atrav�s dos links abaixo (utilize a vers�o referente ao seu sistema operacional):
�	32 bits: http://prdownloads.sourceforge.net/sumo/sumo-win32-0.27.1.zip
�	64 bits: http://prdownloads.sourceforge.net/sumo/sumo-win64-0.27.1.zip

Para obter instru��es de instala��o de outras vers�es ou em plataforma Linux acesse:
�	http://sumo.dlr.de/wiki/Installing

As vers�es anteriores e os c�digos fonte da ferramenta podem ser acessados atrav�s do endere�o:
�	http://sumo.dlr.de/wiki/Downloads

Ap�s efetuar o download da ferramenta, o arquivo .zip deve ser extra�do em um diret�rio qualquer, por exemplo em C:\. O arquivo pode ser extra�do utilizando a ferramenta 7zip (http://www.7-zip.org/) ou outra similar. O pacote cont�m os seguintes arquivos:
�	Bin�rios: arquivos execut�veis, devem ser acessados via linha de comando, com exce��o do sumo-gui que executa a simula��o gr�fica;
�	DLLs: bibliotecas necess�rias para a ferramenta;
�	Exemplos: arquivos contendo exemplos b�sicos, avan�ados e tutoriais relacionas � simula��o;
�	Ferramentas extras, como o TraCI;
�	Documenta��o em formato HTML.

Para a chamada da simula��o e outras ferramentas relacionadas via linha de comando, � necess�rio adicionar a vari�vel SUMO_HOME �s vari�veis de ambiente do sistema. Qualquer linha de comando aberta deve ser finalizada para que as atualiza��es sejam aplicadas com sucesso.
Os seguintes passos (referente ao Windows 10) devem ser seguidos:
1.	 Abrir o �Painel de Controles�;
2.	 Acessar �Sistema e Seguran�a�;
3.	 Acessar �Sistema�;
4.	 Ao lado esquerdo, acessar �Configura��es avan�adas do sistema�;
5.	 Na aba �Avan�ado�, acessar �Vari�veis de Ambiente...�;
6.	 Clicar em �Novo...�:
	a.	Nome da vari�vel: SUMO_HOME
	b.	Valor da vari�vel: local de instala��o do SUMO, por exemplo: C:\sumo\sumo-0.27.1
7.	 Selecionar �Ok�;
8.	 Na vari�vel �Path�, clicar em �Editar...�;
9.	 Adicionar �Novo...�:
	a.	Referenciar o mesmo endere�o adicionado em SUMO_HOME;
10.	 Selecionar �Ok�, �Ok� e �Ok�. 

A partir deste momento, qualquer execut�vel relacionado ao SUMO pode ser acessado via linha de comando. Considere que o diret�rio atual na linha de comando seja a pasta de instala��o do SUMO. No c�digo X � poss�vel verificar uma chamada � simula��o gr�fica do SUMO, utilizando um exemplo b�sico incluso na ferramenta, onde �--net-file� indica o arquivo da rede e �--route-files� indica os arquivos de rotas.

� Exemplo de chamada � simula��o gr�fica via linha de comando
sumo-gui --net-file docs\examples\sumo\vehicle_stops\net.net.xml --route-files docs\examples\sumo\vehicle_stops\input_routes.rou.xml


- O c�digo abaixo demonstra o comando necess�rio para executar a simula��o utilizando os arquivos usados por este projeto.
sumo-gui --net-file parking.net.xml --route-files parking.rou.xml

