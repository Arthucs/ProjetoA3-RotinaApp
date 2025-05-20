# ProjetoA3 - OrganizApp
O projeto consiste no desenvolvimento do **OrganizApp**, uma aplicação simples de controle e organização de rotina, desenvolvida em Java com o uso de Maven como gerenciador de dependências. O projeto também utiliza de JUnit e Mockito para realização de testes unitários.

## Usando o OrganizApp
Para fazer uso do aplicação, primeiro certifique-se que ela foi clonada e inicializada corretamente, siga os seguintes passos:
1. Clone o repositório na sua máquina (usando "git clone <link-do-repositório>").
2. Procura a classe RotinaApp, dentro da pasta Demo/.../java/com/example.
3. Execute-a clicando no botão "Run", no canto superior direito (no VS Code).
4. Pronto! O OrganizApp estará em execução.

Após isso, você irá se deparar com uma **Tela de Login**, cujas credenciais são:
- Usuário: **admin**
- Senha: **1234**

A Tela de Login vai direcioná-lo para o **Menu Principal**, que possui as seguintes funcionalidades:
1. *Adicionar Tarefa:* Adiciona uma *tarefa* com nome, descrição mais detalhada, data e horário.
2. *Listar Tarefas:* Exibe todas as tarefas adicionadas em ordem e seu status (se foram concluídas ou não), através de dois colchetes [ ] (tarefas concluídas possuem um [ X ] entre os colchetes).
3. *Concluir Tarefa:* Exibe novamente a lista de tarefas e permite que o usuário digite o número correspondente da tarefa que deseja concluir (marcar com um X). Caso a tarefa escolhida já esteja marcada, ela é desmarcada, voltando ao normal.
4. *Excluir Tarefa:* Semelhante a *Concluir Tarefa*, porém, a tarefa correspondente ao número inserido é excluída. Digitar "Limpar" ao invés de um número faz todas as tarefas serem excluídas da lista.
5. *Sair:* Encerra a aplicação.  