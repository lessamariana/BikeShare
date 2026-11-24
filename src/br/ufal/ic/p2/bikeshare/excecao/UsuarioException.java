package br.ufal.ic.p2.bikeshare.excecao;

// Violação de regra envolvendo usuários (inexistente, email repetido, tipo errado)

public class UsuarioException extends BikeShareException
{

    private static final long serialVersionUID = 1L;

    public UsuarioException(String mensagem)
    {
        super(mensagem);
    }
}
