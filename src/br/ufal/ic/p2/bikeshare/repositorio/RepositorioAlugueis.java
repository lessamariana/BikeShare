package br.ufal.ic.p2.bikeshare.repositorio;

import br.ufal.ic.p2.bikeshare.excecao.AluguelException;
import br.ufal.ic.p2.bikeshare.excecao.BikeShareException;
import br.ufal.ic.p2.bikeshare.modelo.aluguel.Aluguel;

// Aluguéis (em andamento e encerrados), com ids

public class RepositorioAlugueis extends RepositorioComId<Aluguel>
{

    private static final long serialVersionUID = 1L;

    public RepositorioAlugueis()
    {
        super("A");
    }

    @Override
    protected String mensagemIdentificacaoNula()
    {
        return "Identificacao do aluguel nao pode ser nula.";
    }

    @Override
    protected BikeShareException naoEncontrado()
    {
        return new AluguelException("Aluguel nao existe.");
    }
}
