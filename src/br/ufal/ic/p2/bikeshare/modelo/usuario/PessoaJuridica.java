package br.ufal.ic.p2.bikeshare.modelo.usuario;

import br.ufal.ic.p2.bikeshare.util.Texto;

// Usuário identificado por CNPJ e razão social

public class PessoaJuridica extends Usuario
{

    private static final long serialVersionUID = 1L;

    private final String cnpj;
    private final String razaoSocial;

    public PessoaJuridica(String id, DadosUsuario dados, String cnpj, String razaoSocial)
    {
        super(id, dados);
        this.cnpj = Texto.exigir(cnpj, "CNPJ nao pode ser nulo.");
        this.razaoSocial = Texto.exigir(razaoSocial, "Razao social nao pode ser nula.");
    }

    @Override
    public TipoUsuario getTipo()
    {
        return TipoUsuario.PESSOA_JURIDICA;
    }

    @Override
    protected String getCnpj()
    {
        return cnpj;
    }

    @Override
    protected String getRazaoSocial()
    {
        return razaoSocial;
    }
}
