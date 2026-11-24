package br.ufal.ic.p2.bikeshare.repositorio;

import br.ufal.ic.p2.bikeshare.excecao.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecao.EstacaoException;
import br.ufal.ic.p2.bikeshare.modelo.estacao.Estacao;

// Estações cadastradas, com ids

public class RepositorioEstacoes extends RepositorioComId<Estacao>
{

    private static final long serialVersionUID = 1L;

    public RepositorioEstacoes()
    {
        super("E");
    }

    @Override
    protected String mensagemIdentificacaoNula()
    {
        return "Identificacao da estacao nao pode ser nula.";
    }

    @Override
    protected BikeShareException naoEncontrado()
    {
        return new EstacaoException("Estacao nao existe.");
    }
}
