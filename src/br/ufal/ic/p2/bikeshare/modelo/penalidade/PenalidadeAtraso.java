package br.ufal.ic.p2.bikeshare.modelo.penalidade;

import java.math.BigDecimal;

// Lançada automaticamente quando um aluguel passa do tempo máximo

public class PenalidadeAtraso extends Penalidade
{

    private static final long serialVersionUID = 1L;

    private final String idAluguel;

    public PenalidadeAtraso(BigDecimal valor, String idAluguel)
    {
        super(valor);
        this.idAluguel = idAluguel;
    }

    public String getIdAluguel()
    {
        return idAluguel;
    }
}
