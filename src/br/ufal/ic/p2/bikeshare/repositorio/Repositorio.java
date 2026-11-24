package br.ufal.ic.p2.bikeshare.repositorio;

import br.ufal.ic.p2.bikeshare.excecao.BikeShareException;
import br.ufal.ic.p2.bikeshare.util.Texto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

// Armazenamento em memória de entidades indexadas por identificador

public abstract class Repositorio<T extends Serializable> implements Serializable
{

    private static final long serialVersionUID = 1L;

    private final Map<String, T> itens = new LinkedHashMap<>();

    // Busca uma entidade pelo id @throws BikeShareException se o id estiver vazio ou não existir

    public T buscar(String id)
    {
        Texto.exigir(id, mensagemIdentificacaoNula());
        T item = itens.get(id);
        if(item == null)
        {
            throw naoEncontrado();
        }
        return item;
    }

    public boolean existe(String id)
    {
        return itens.containsKey(id);
    }

    protected void guardar(String id, T item)
    {
        itens.put(id, item);
    }

    public void remover(String id)
    {
        itens.remove(id);
    }

    protected Collection<T> todos()
    {
        return Collections.unmodifiableCollection(itens.values());
    }

    protected abstract String mensagemIdentificacaoNula();

    protected abstract BikeShareException naoEncontrado();
}
