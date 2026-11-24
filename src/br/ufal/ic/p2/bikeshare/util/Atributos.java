package br.ufal.ic.p2.bikeshare.util;

import br.ufal.ic.p2.bikeshare.excecao.ValidacaoException;

import java.util.Map;
import java.util.function.Supplier;

//Consulta de atributos por nome

public final class Atributos
{

    private Atributos() {
    }

    public static String consultar(Map<String, Supplier<String>> leitores, String atributo)
    {
        Supplier<String> leitor = atributo == null ? null : leitores.get(atributo);
        if(leitor == null)
        {
            throw new ValidacaoException("Atributo nao existe.");
        }
        return leitor.get();
    }
}
