package br.ufal.ic.p2.bikeshare.sistema;

import br.ufal.ic.p2.bikeshare.servico.ServicoAlugueis;
import br.ufal.ic.p2.bikeshare.servico.ServicoEstacoes;
import br.ufal.ic.p2.bikeshare.servico.ServicoUsuarios;

// Agrupa os serviços que operam sobre um  EstadoSistema É recriado sempre que o estado é trocado (zerar, carregar, restaurar),garantindo que os serviços nunca apontem para um estado antigo

public class Servicos
{

    private final ServicoUsuarios usuarios;
    private final ServicoEstacoes estacoes;
    private final ServicoAlugueis alugueis;

    public Servicos(EstadoSistema estado)
    {
        this.usuarios = new ServicoUsuarios(estado.getUsuarios(), estado.getPlanos());
        this.estacoes = new ServicoEstacoes(estado.getEstacoes(), estado.getBicicletas(), estado.getConfiguracao());
        this.alugueis = new ServicoAlugueis(estado.getAlugueis(), estado.getUsuarios(), estado.getEstacoes(), estado.getBicicletas(), estado.getConfiguracao());
    }

    public ServicoUsuarios usuarios()
    {
        return usuarios;
    }

    public ServicoEstacoes estacoes()
    {
        return estacoes;
    }

    public ServicoAlugueis alugueis()
    {
        return alugueis;
    }
}
