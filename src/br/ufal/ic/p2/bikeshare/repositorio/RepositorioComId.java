package br.ufal.ic.p2.bikeshare.repositorio;

import java.io.Serializable;
import java.util.function.Function;

public abstract class RepositorioComId<T extends Serializable> extends Repositorio<T>
{

    private static final long serialVersionUID = 1L;

    private final GeradorId gerador;

    protected RepositorioComId(String prefixo)
    {
        this.gerador = new GeradorId(prefixo);
    }

    public T cadastrar(Function<String, T> fabrica)
    {
        String id = gerador.espiar();
        T item = fabrica.apply(id);
        guardar(id, item);
        gerador.avancar();
        return item;
    }
}
