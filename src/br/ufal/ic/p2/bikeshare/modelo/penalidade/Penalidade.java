package br.ufal.ic.p2.bikeshare.modelo.penalidade;

import java.io.Serializable;
import java.math.BigDecimal;

// Cobrança adicional. Enquanto houver penalidade pendente, o usuário fica inadimplente e não pode iniciar novas corridas

public abstract class Penalidade implements Serializable
{

    private static final long serialVersionUID = 1L;

    private final BigDecimal valor;

    protected Penalidade(BigDecimal valor)
    {
        this.valor = valor;
    }

    public BigDecimal getValor()
    {
        return valor;
    }
}
