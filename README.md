# Teste Dev./Operacional

## Cenário:
<p>Uma empresa solicitou um sistema simples de compra. Nesse sistema o cliente pode fazer compras em algumas empresas de acordo com os produtos que as mesmas têm disponível em estoque, além disso ele também pode ver as suas compras. Por sua vez a empresa pode ver as suas vendas e os seus produtos. Algumas das regras de negócios são:</p>

- Cada empresa tem sua taxa (comissão do sistema) para as transações
- Além do administrador e a própria empresa, nenhum outro usuário poderá ver informações da empresa (além do nome)
- Ao finalizar uma compra o cliente deve ver um resumo da mesma
- O saldo da empresa deve ser alterado já refletindo as taxas
- A empresa deve vender apenas produtos que ela esteja relacionada
- A empresa poderá ver a taxa de comissão de sistema em cada venda (ao listar suas vendas)

## Teste
1. Clone o projeto em sua máquina e descreva os erros que você encontrou. Obs.: Os erros podem ser desde código, estrutura de dados, boas práticas, experiência do usuário e regras de negócio.
2. Descreva como se estivesse repassando os ajustes para um programador.
3. Em caso de erros na regra de negócio, faça um relato para a empresa que solicitou o sistema, neste relato deve ser informando o erro e porquê acontece o erro.
4. Faça o máximo de ajustes no código, de forma que as falhas sejam corrigidas. Siga a seguinte ordem para o ajuste: regra de negócio, código, boas práticas, estruturade dados e experiência do usuário.
5. Suba os ajustes no seu github (caso tenha feito apenas os descritivos, por favor desconsiderar).

## Problemas identificados

### Regras de Negócio

- Atualizar a quantidade de produto em estoque não atualiza após a compra efetuada.
- Ao Errar a senha após uma compra feita por usuário, retorna as Compras Efetuadas anteriormente, só ocorre na primeira vez do erro.
- Funcionalidade de Admin não implementada.
- É possível selecionar produtos de outras empresas no processo de compra.

### Experiência do Usuário

- Ao escolher o produto para a compra, poderia informar o preço.
- Depois de terminar o processo de realizar compras ou ver compras não deslogar o usuário, manter a sessão do usuário ativa.
- Ao selecionar uma opção de realizar compra e escolher uma opção inválida, poderia retornar a mensagem de opção inválida.
- Tratar o retorno de valores do tipo double para duas casas decimais.

## Bugs

- Ocorre um erro ao digitar opções incorretas, por exemplo, quando o usuário deve digitar um número, mas insere um caractere, o sistema para de funcionar.
