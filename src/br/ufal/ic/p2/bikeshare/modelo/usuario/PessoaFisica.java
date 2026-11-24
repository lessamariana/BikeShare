package br.ufal.ic.p2.bikeshare.modelo.usuario;

import br.ufal.ic.p2.bikeshare.util.Texto;

// Usuário identificado por CPF

public class PessoaFisica extends Usuario
{

    private static final long serialVersionUID = 1L;

    private final String cpf;

    public PessoaFisica(String id, DadosUsuario dados, String cpf)
    {
        super(id, dados);
        this.cpf = Texto.exigir(cpf, "CPF nao pode ser nulo.");
    }

    @Override
    public TipoUsuario getTipo()
    {
        return TipoUsuario.PESSOA_FISICA;
    }

    @Override
    protected String getCpf()
    {
        return cpf;
    }
}
