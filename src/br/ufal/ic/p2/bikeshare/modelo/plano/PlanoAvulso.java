package br.ufal.ic.p2.bikeshare.modelo.plano;

import br.ufal.ic.p2.bikeshare.excecao.PlanoException;
import br.ufal.ic.p2.bikeshare.util.Texto;

import java.math.BigDecimal;

// Plano sem mensalidade, paga cada minuto da corrid

public class PlanoAvulso extends Plano
{

    private static final long serialVersionUID = 1L;

    public PlanoAvulso(String nome, BigDecimal valorMinuto)
    {
        super(nome, 0, valorMinuto);
    }

    @Override
    public BigDecimal getMensalidade()
    {
        throw semMensalidade();
    }

    // Avulso não aceita mensalidade

    @Override
    public Plano contratar(String mensalidade)
    {
        if(!Texto.vazio(mensalidade))
        {
            throw semMensalidade();
        }
        return this;
    }

    private PlanoException semMensalidade()
    {
        return new PlanoException("Plano nao possui mensalidade.");
    }
}
