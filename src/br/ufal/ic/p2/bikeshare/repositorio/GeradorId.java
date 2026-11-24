package br.ufal.ic.p2.bikeshare.repositorio;

import java.io.Serializable;

// Gera identificadores sequenciais com prefixo

public class GeradorId implements Serializable
{

    private static final long serialVersionUID = 1L;

    private final String prefixo;
    private int proximo = 1;

    public GeradorId(String prefixo)
    {
        this.prefixo = prefixo;
    }

    public String espiar()
    {
        return prefixo + proximo;
    }

    public void avancar()
    {
        proximo++;
    }
}
