package br.ufal.ic.p2.bikeshare.modelo.pagamento;

import br.ufal.ic.p2.bikeshare.util.Texto;

// Pagamento por Pix, então guarda a chave

public class Pix extends MetodoPagamento
{

    private static final long serialVersionUID = 1L;

    private final String chave;

    public Pix(String chave)
    {
        this.chave = Texto.exigir(chave, "Chave pix nao pode ser nula.");
    }

    @Override
    public String getNome()
    {
        return "pix";
    }

    @Override
    public String getChavePix()
    {
        return chave;
    }
}
