package br.ufal.ic.p2.bikeshare.modelo.usuario;

import br.ufal.ic.p2.bikeshare.excecao.UsuarioException;
import br.ufal.ic.p2.bikeshare.util.Nomeado;

// Os tipos fixos de usuário e como cada um é construído

public enum TipoUsuario implements Nomeado
{

    PESSOA_FISICA("pessoaFisica") {
        @Override
        public Usuario criar(String id, DadosUsuario dados, String cpf)
        {
            return new PessoaFisica(id, dados, cpf);
        }
    },

    PESSOA_JURIDICA("pessoaJuridica") {
        @Override
        public Usuario criar(String id, DadosUsuario dados, String cnpj, String razaoSocial)
        {
            return new PessoaJuridica(id, dados, cnpj, razaoSocial);
        }
    };

    private final String nome;

    TipoUsuario(String nome)
    {
        this.nome = nome;
    }

    @Override
    public String getNome()
    {
        return nome;
    }

    public static TipoUsuario deNome(String nome)
    {
        return Nomeado.buscar(values(), nome, TipoUsuario::tipoInvalido);
    }

    public Usuario criar(String id, DadosUsuario dados, String documento)
    {
        throw tipoInvalido();
    }

    public Usuario criar(String id, DadosUsuario dados, String documento, String complemento)
    {
        throw tipoInvalido();
    }

    private static UsuarioException tipoInvalido()
    {
        return new UsuarioException("Tipo invalido.");
    }
}
