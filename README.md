# Bikeshare | Programação 02

Lógica de negócio de um sistema de bicicletas compartilhadas, feita como projeto da disciplina
de Programação 02 (UFAL). O sistema é acessado só pela `Facade` e validado por testes de aceitação
automatizados (EasyAccept), sem interface gráfica e sem banco de dados.

## Status

| Milestone | User Stories | Situação |
|---|---|---|
| Milestone 1 | US1 a US9 | ✅ Concluída: `us1` a `us9_1` passam |
| Milestone 2 | US10 a US16 | ⏳ Ainda não implementada |

**O que a Milestone 1 cobre:**

- **US1–US4:** cadastro, consulta, alteração e remoção de usuários (pessoa física e jurídica), planos avulso e assinante, métodos de pagamento (cartão, Pix e saldo pré-carregado).
- **US5–US7:** cadastro de estações, bicicletas convencionais e elétricas, consulta de vagas e de bicicletas disponíveis.
- **US8–US9:** retirada e devolução, com cálculo de tarifa, carência, expiração e penalidade.

## Como executar

1. Abra a pasta do projeto no IntelliJ IDEA.
2. Confira se `lib/easyaccept.jar` está nas dependências do módulo (*File → Project Structure → Modules → Dependencies*).
3. Rode a classe `Main`, que fica em `src/Main.java`. Ela executa os scripts de `tests/` em ordem.

Os testes `usN_1.txt` verificam a persistência: eles leem o que o `usN.txt` gravou ao chamar
`encerrarSistema`, por isso precisam rodar logo depois dele (o `Main` já faz isso). O estado é salvo em
`bikeshare.dat`, um arquivo gerado na execução que não vai para o repositório.

## Estrutura

```
src/br/ufal/ic/p2/bikeshare
├── Facade.java      ponto de entrada dos testes: só recebe a chamada e delega
├── sistema/         Sistema (coordenador), EstadoSistema, Servicos, Persistencia
├── servico/         ServicoUsuarios, ServicoEstacoes, ServicoAlugueis
├── repositorio/     Repositorio<T>, RepositorioComId, GeradorId e um repositório por entidade
├── modelo/
│   ├── usuario/     Usuario, PessoaFisica, PessoaJuridica, Email, TipoUsuario, TipoMetodoPagamento
│   ├── plano/       Plano, PlanoAvulso, PlanoAssinatura, CatalogoPlanos
│   ├── pagamento/   MetodoPagamento, Cartao, Pix, SaldoPreCarregado, SemMetodoPagamento
│   ├── estacao/     Estacao, Bicicleta, BicicletaConvencional, BicicletaEletrica, TipoBicicleta, EstadoBicicleta
│   ├── aluguel/     Aluguel, EstadoAluguel
│   └── penalidade/  Penalidade, PenalidadeAtraso
├── excecao/         BikeShareException (abstrata) e 8 exceções por categoria
├── config/          Configuracao (tarifas e limites)
└── util/            Texto, Numeros, Dinheiro, Datas, Atributos, Nomeado
```

## Decisões de projeto

- **Facade enxuta:** cada método tem uma linha e repassa a chamada ao `Sistema`.
- **Restauração em caso de falha:** antes de toda operação que altera dados, o `Sistema` guarda uma cópia
  do estado. Se algo der errado no meio, o estado anterior volta e o erro é repassado. Isso fica em um
  único lugar, sem `try/catch` repetido.
- **Polimorfismo no lugar de `instanceof`:** atributos e comportamentos que só existem em um subtipo
  (CPF, CNPJ, bateria, chave Pix, saldo, mensalidade) têm um comportamento padrão na classe base, que
  recusa com a mensagem do teste, e só a subclasse certa sobrescreve.
- **Validação no próprio objeto:** `Email`, `Cartao`, `Estacao`, `BicicletaEletrica` etc. validam os
  próprios dados antes de alterar qualquer coisa.
- **Exceções por categoria:** uma exceção base abstrata e uma subclasse por área (usuário, plano,
  pagamento, estação, bicicleta, aluguel, validação, persistência). Nenhuma exceção genérica.
- **Parâmetros configuráveis:** tarifas, franquia, carência, tempo máximo, bateria mínima e valor da
  penalidade ficam em `Configuracao`, nunca espalhados pelo código.
- **Persistência:** `java.io.Serializable`, isolada em `Persistencia.java`.

## Regras de tarifação (Milestone 1)

| Regra | Valor |
|---|---|
| Plano avulso | R$ 0,50 por minuto |
| Plano assinante | 60 minutos grátis por corrida e R$ 0,30 por minuto excedente (mensalidade padrão: R$ 30,00) |
| Bicicleta elétrica | + R$ 0,20 por minuto, mesmo dentro da franquia; só pode ser retirada com bateria a partir de 15% |
| Carência | devolver na mesma estação em até 2 minutos cancela o aluguel, sem cobrança |
| Tempo máximo | acima de 24 h (1440 min) o aluguel expira: não há tarifa, o usuário recebe penalidade de R$ 10,00 e a bicicleta vai para manutenção |
