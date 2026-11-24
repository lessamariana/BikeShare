package br.ufal.ic.p2.bikeshare.repositorio;

import br.ufal.ic.p2.bikeshare.excecao.BicicletaException;
import br.ufal.ic.p2.bikeshare.excecao.BikeShareException;
import br.ufal.ic.p2.bikeshare.modelo.estacao.Bicicleta;
import br.ufal.ic.p2.bikeshare.util.Texto;

// Bicicletas cadastradas. O id é informado pelo operador

public class RepositorioBicicletas extends Repositorio<Bicicleta>
{

    private static final long serialVersionUID = 1L;

    // Garante que o id foi informado e ainda não está em uso

    public void exigirIdentificacaoLivre(String id)
    {
        Texto.exigir(id, mensagemIdentificacaoNula());
        if(existe(id))
        {
            throw new BicicletaException("Bicicleta ja existe.");
        }
    }

    public void adicionar(Bicicleta bicicleta)
    {
        guardar(bicicleta.getId(), bicicleta);
    }

    @Override
    protected String mensagemIdentificacaoNula()
    {
        return "Identificacao da bicicleta nao pode ser nula.";
    }

    @Override
    protected BikeShareException naoEncontrado()
    {
        return new BicicletaException("Bicicleta nao existe.");
    }
}
