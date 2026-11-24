package br.ufal.ic.p2.bikeshare.util;

import br.ufal.ic.p2.bikeshare.excecao.BikeShareException;

import java.util.function.Supplier;

public interface Nomeado
{

    String getNome();

    static <T extends Nomeado> T buscar(T[] valores, String nome, Supplier<BikeShareException> erro)
    {
        for(T valor : valores)
        {
            if(valor.getNome().equals(nome))
            {
                return valor;
            }
        }
        throw erro.get();
    }
}
