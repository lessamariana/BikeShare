package br.ufal.ic.p2.bikeshare.excecao;

// Violação de regra envolvendo bicicletas (inexistente, sem bateria, fora da estação)

public class BicicletaException extends BikeShareException
{

    private static final long serialVersionUID = 1L;

    public BicicletaException(String mensagem)
    {
        super(mensagem);
    }
}
