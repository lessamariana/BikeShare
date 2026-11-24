package br.ufal.ic.p2.bikeshare.servico;

import br.ufal.ic.p2.bikeshare.config.Configuracao;
import br.ufal.ic.p2.bikeshare.modelo.estacao.Bicicleta;
import br.ufal.ic.p2.bikeshare.modelo.estacao.Estacao;
import br.ufal.ic.p2.bikeshare.modelo.estacao.TipoBicicleta;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioEstacoes;

// Casos de uso de estações, bicicletas e disponibilidade (US5, US6 e US7)

public class ServicoEstacoes
{

    private final RepositorioEstacoes estacoes;
    private final RepositorioBicicletas bicicletas;
    private final Configuracao configuracao;

    public ServicoEstacoes(RepositorioEstacoes estacoes, RepositorioBicicletas bicicletas, Configuracao configuracao)
    {
        this.estacoes = estacoes;
        this.bicicletas = bicicletas;
        this.configuracao = configuracao;
    }


    public String cadastrar(String nome, String endereco, String capacidade)
    {
        return estacoes.cadastrar(id -> new Estacao(id, nome, endereco, capacidade)).getId();
    }

    public void adicionarBicicleta(String idEstacao, String idBicicleta, String tipo)
    {
        Estacao estacao = estacoes.buscar(idEstacao);
        bicicletas.exigirIdentificacaoLivre(idBicicleta);
        guardarEm(estacao, TipoBicicleta.deNome(tipo).criar(idBicicleta));
    }

    public void adicionarBicicleta(String idEstacao, String idBicicleta, String tipo, String nivelBateria)
    {
        Estacao estacao = estacoes.buscar(idEstacao);
        bicicletas.exigirIdentificacaoLivre(idBicicleta);
        guardarEm(estacao, TipoBicicleta.deNome(tipo).criar(idBicicleta, nivelBateria));
    }

    private void guardarEm(Estacao estacao, Bicicleta bicicleta)
    {
        estacao.receberNova(bicicleta);
        bicicletas.adicionar(bicicleta);
    }

    // Remove do sistema, o mesmo id pode ser cadastrado de novo depois

    public void removerBicicleta(String idEstacao, String idBicicleta)
    {
        Estacao estacao = estacoes.buscar(idEstacao);
        Bicicleta bicicleta = bicicletas.buscar(idBicicleta);
        estacao.liberar(bicicleta);
        bicicletas.remover(bicicleta.getId());
    }

    public String getAtributoBicicleta(String idBicicleta, String atributo)
    {
        return bicicletas.buscar(idBicicleta).getAtributo(atributo);
    }

    public String getEstadoBicicleta(String idBicicleta)
    {
        return bicicletas.buscar(idBicicleta).getEstado().getNome();
    }

    public String getVagasDisponiveis(String idEstacao)
    {
        return String.valueOf(estacoes.buscar(idEstacao).getVagasDisponiveis());
    }

    public String getBicicletasDisponiveis(String idEstacao)
    {
        return String.valueOf(estacoes.buscar(idEstacao).getBicicletasDisponiveis(configuracao));
    }
}
