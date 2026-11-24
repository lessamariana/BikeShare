package br.ufal.ic.p2.bikeshare.excecao;

// Falha ao gravar ou ler o estado do sistema

public class PersistenciaException extends BikeShareException
{

    private static final long serialVersionUID = 1L;

    public PersistenciaException(String mensagem, Throwable causa)
    {
        super(mensagem, causa);
    }
}
