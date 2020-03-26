Este trabalho utilizou a versão 0.27.1 da ferramenta SUMO. A ferramenta pode ser obtida através dos links abaixo (utilize a versão referente ao seu sistema operacional):
•	32 bits: http://prdownloads.sourceforge.net/sumo/sumo-win32-0.27.1.zip
•	64 bits: http://prdownloads.sourceforge.net/sumo/sumo-win64-0.27.1.zip

Para obter instruções de instalação de outras versões ou em plataforma Linux acesse:
•	http://sumo.dlr.de/wiki/Installing

As versões anteriores e os códigos fonte da ferramenta podem ser acessados através do endereço:
•	http://sumo.dlr.de/wiki/Downloads

Após efetuar o download da ferramenta, o arquivo .zip deve ser extraído em um diretório qualquer, por exemplo em C:\. O arquivo pode ser extraído utilizando a ferramenta 7zip (http://www.7-zip.org/) ou outra similar. O pacote contém os seguintes arquivos:
•	Binários: arquivos executáveis, devem ser acessados via linha de comando, com exceção do sumo-gui que executa a simulação gráfica;
•	DLLs: bibliotecas necessárias para a ferramenta;
•	Exemplos: arquivos contendo exemplos básicos, avançados e tutoriais relacionas à simulação;
•	Ferramentas extras, como o TraCI;
•	Documentação em formato HTML.

Para a chamada da simulação e outras ferramentas relacionadas via linha de comando, é necessário adicionar a variável SUMO_HOME às variáveis de ambiente do sistema. Qualquer linha de comando aberta deve ser finalizada para que as atualizações sejam aplicadas com sucesso.
Os seguintes passos (referente ao Windows 10) devem ser seguidos:
1.	 Abrir o “Painel de Controles”;
2.	 Acessar “Sistema e Segurança”;
3.	 Acessar “Sistema”;
4.	 Ao lado esquerdo, acessar “Configurações avançadas do sistema”;
5.	 Na aba “Avançado”, acessar “Variáveis de Ambiente...”;
6.	 Clicar em “Novo...”:
	a.	Nome da variável: SUMO_HOME
	b.	Valor da variável: local de instalação do SUMO, por exemplo: C:\sumo\sumo-0.27.1
7.	 Selecionar “Ok”;
8.	 Na variável “Path”, clicar em “Editar...”;
9.	 Adicionar “Novo...”:
	a.	Referenciar o mesmo endereço adicionado em SUMO_HOME;
10.	 Selecionar “Ok”, “Ok” e “Ok”. 

A partir deste momento, qualquer executável relacionado ao SUMO pode ser acessado via linha de comando. Considere que o diretório atual na linha de comando seja a pasta de instalação do SUMO. No código X é possível verificar uma chamada à simulação gráfica do SUMO, utilizando um exemplo básico incluso na ferramenta, onde “--net-file” indica o arquivo da rede e “--route-files” indica os arquivos de rotas.

– Exemplo de chamada à simulação gráfica via linha de comando
sumo-gui --net-file docs\examples\sumo\vehicle_stops\net.net.xml --route-files docs\examples\sumo\vehicle_stops\input_routes.rou.xml


- O código abaixo demonstra o comando necessário para executar a simulação utilizando os arquivos usados por este projeto.
sumo-gui --net-file parking.net.xml --route-files parking.rou.xml

