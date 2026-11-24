package br.ufal.ic.p2.bikeshare.excecao;

// Violação de regra envolvendo planos de tarifação (plano inexistente, mensalidade indevida)

public class PlanoException extends BikeShareException
{

    private static final long serialVersionUID = 1L;

    public PlanoException(String mensagem)
    {
        super(mensagem);
    }
}
