package br.ufal.ic.p2.bikeshare.excecao;

// Entrada inválida recebida pelo sistema: campo vazio, valor não numérico, data mal formatada, atributo inexistente

public class ValidacaoException extends BikeShareException
{

    private static final long serialVersionUID = 1L;

    public ValidacaoException(String mensagem)
    {
        super(mensagem);
    }
}
