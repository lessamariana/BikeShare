package br.ufal.ic.p2.bikeshare;

import br.ufal.ic.p2.bikeshare.sistema.Sistema;

//Ponto de entrada usado pelos testes de aceitação (EasyAccept) para acessar as operações do BikeShare

public class Facade
{

    private final Sistema sistema = new Sistema();

    // US1 - Cadastro de usuario
    public String criarUsuario(String nome, String email, String senha, String tipo, String cpfOuCnpj)
    {
        return sistema.executarComRetorno(s -> s.usuarios().cadastrar(nome, email, senha, tipo, cpfOuCnpj));
    }

    public String criarUsuario(String nome, String email, String senha, String tipo, String cnpj, String razaoSocial)
    {
        return sistema.executarComRetorno(s -> s.usuarios().cadastrar(nome, email, senha, tipo, cnpj, razaoSocial));
    }

    public String criarUsuario(String nome, String email, String senha, String tipo, String cpf, String plano, String mensalidade)
    {
        return sistema.executarComRetorno(s -> s.usuarios().cadastrar(nome, email, senha, tipo, cpf, plano, mensalidade));
    }

    public String criarUsuario(String nome, String email, String senha, String tipo, String cnpj, String razaoSocial, String plano, String mensalidade)
    {
        return sistema.executarComRetorno(s -> s.usuarios().cadastrar(nome, email, senha, tipo, cnpj, razaoSocial, plano, mensalidade));
    }

    // US2 - Consulta de usuario
    public String getAtributoUsuario(String usuario, String atributo)
    {
        return sistema.consultar(s -> s.usuarios().getAtributo(usuario, atributo));
    }

    public String getUsuarioPorEmail(String email)
    {
        return sistema.consultar(s -> s.usuarios().getIdPorEmail(email));
    }

    // US3 - Alteracao de usuario
    // Altera nome, email, senha, plano, recarrega saldo ou escolhe
    public void alterarUsuario(String usuario, String atributo, String valor)
    {
        sistema.executar(s -> s.usuarios().alterar(usuario, atributo, valor));
    }

    public void alterarUsuario(String usuario, String atributo, String valor1, String chave)
    {
        sistema.executar(s -> s.usuarios().alterar(usuario, atributo, valor1, chave));
    }

    public void alterarUsuario(String usuario, String atributo, String valor1, String numero, String validade)
    {
        sistema.executar(s -> s.usuarios().alterar(usuario, atributo, valor1, numero, validade));
    }
    // US4 - Remocao de usuario
    public void removerUsuario(String usuario)
    {
        sistema.executar(s -> s.usuarios().remover(usuario));
    }

    // US5 - Cadastro de estacao
    public String criarEstacao(String nome, String endereco, String capacidade)
    {
        return sistema.executarComRetorno(s -> s.estacoes().cadastrar(nome, endereco, capacidade));
    }

    // US6 - Gestao de bicicletas na estacao
    public void adicionarBicicleta(String estacao, String bicicleta, String tipo)
    {
        sistema.executar(s -> s.estacoes().adicionarBicicleta(estacao, bicicleta, tipo));
    }

    public void adicionarBicicleta(String estacao, String bicicleta, String tipo, String nivelBateria)
    {
        sistema.executar(s -> s.estacoes().adicionarBicicleta(estacao, bicicleta, tipo, nivelBateria));
    }

    public void removerBicicleta(String estacao, String bicicleta)
    {
        sistema.executar(s -> s.estacoes().removerBicicleta(estacao, bicicleta));
    }

    public String getAtributoBicicleta(String bicicleta, String atributo)
    {
        return sistema.consultar(s -> s.estacoes().getAtributoBicicleta(bicicleta, atributo));
    }

    // US7 - Consulta de disponibilidade
    public String getBicicletasDisponiveis(String estacao)
    {
        return sistema.consultar(s -> s.estacoes().getBicicletasDisponiveis(estacao));
    }

    public String getVagasDisponiveis(String estacao)
    {
        return sistema.consultar(s -> s.estacoes().getVagasDisponiveis(estacao));
    }

    public String getEstadoBicicleta(String bicicleta)
    {
        return sistema.consultar(s -> s.estacoes().getEstadoBicicleta(bicicleta));
    }

    // US8 - Retirada de bicicleta
    public String retirarBicicleta(String usuario, String estacao, String bicicleta, String data)
    {
        return sistema.executarComRetorno(s -> s.alugueis().retirar(usuario, estacao, bicicleta, data));
    }
    public String getAtributoAluguel(String aluguel, String atributo)
    {
        return sistema.consultar(s -> s.alugueis().getAtributo(aluguel, atributo));
    }

    // US9 - Devolucao de bicicleta
    public void devolverBicicleta(String aluguel, String estacao, String data)
    {
        sistema.executar(s -> s.alugueis().devolver(aluguel, estacao, data));
    }

    // Apaga todos os dados e reinicia os contadores de identificadores
    public void zerarSistema() {
        sistema.zerar();
    }

    // Grava o estado atual para que a próxima execução continue de onde parou
    public void encerrarSistema() {
        sistema.encerrar();
    }
}
