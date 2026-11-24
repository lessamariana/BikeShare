package br.ufal.ic.p2.bikeshare.modelo.usuario;

import br.ufal.ic.p2.bikeshare.modelo.plano.Plano;

// Dados comuns a qualquer usuário, recebidos no cadastro

public final class DadosUsuario
{

    private final String nome;
    private final String email;
    private final String senha;
    private final Plano plano;

    public DadosUsuario(String nome, String email, String senha, Plano plano)
    {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.plano = plano;
    }

    public String getNome()
    {
        return nome;
    }

    public String getEmail()
    {
        return email;
    }

    public String getSenha()
    {
        return senha;
    }

    public Plano getPlano()
    {
        return plano;
    }
}
