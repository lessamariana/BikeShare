package br.ufal.ic.p2.bikeshare.util;

import br.ufal.ic.p2.bikeshare.excecao.ValidacaoException;

// Conversão de textos para números inteiros, com as mensagens de cada campo
public final class Numeros
{
    private static final String FORMATO_INTEIRO = "-?\\d{1,9}";

    private Numeros() {
    }

    public static int inteiro(String texto, String mensagemVazio, String mensagemInvalido)
    {
        String valor = Texto.exigir(texto, mensagemVazio).trim();
        if(!valor.matches(FORMATO_INTEIRO))
        {
            throw new ValidacaoException(mensagemInvalido);
        }
        return Integer.parseInt(valor);
    }
}
