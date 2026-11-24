package br.ufal.ic.p2.bikeshare.modelo.estacao;

import br.ufal.ic.p2.bikeshare.excecao.BicicletaException;
import br.ufal.ic.p2.bikeshare.util.Nomeado;

public enum TipoBicicleta implements Nomeado
{

    CONVENCIONAL("convencional") {
        @Override
        public Bicicleta criar(String id)
        {
            return new BicicletaConvencional(id);
        }

        @Override
        public Bicicleta criar(String id, String nivelBateria)
        {
            throw Bicicleta.semBateria();
        }
    },

    ELETRICA("eletrica") {
        @Override
        public Bicicleta criar(String id)
        {
            return criar(id, null);
        }

        @Override
        public Bicicleta criar(String id, String nivelBateria)
        {
            return new BicicletaEletrica(id, nivelBateria);
        }
    };

    private final String nome;

    TipoBicicleta(String nome)
    {
        this.nome = nome;
    }

    @Override
    public String getNome()
    {
        return nome;
    }

    public static TipoBicicleta deNome(String nome)
    {
        return Nomeado.buscar(values(), nome, () -> new BicicletaException("Tipo invalido."));
    }

    public abstract Bicicleta criar(String id);

    public abstract Bicicleta criar(String id, String nivelBateria);
}
