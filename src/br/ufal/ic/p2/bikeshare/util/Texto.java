package br.ufal.ic.p2.bikeshare.util;

import br.ufal.ic.p2.bikeshare.excecao.ValidacaoException;

//Validações de texto

public final class Texto
{

    private Texto() {
    }

    // Nulo, vazio ou só com espaços conta como vazio
    public static boolean vazio(String valor)
    {
        return valor == null || valor.isBlank();
    }

    public static String exigir(String valor, String mensagemSeVazio)
    {
        if(vazio(valor))
        {
            throw new ValidacaoException(mensagemSeVazio);
        }
        return valor;
    }
}
