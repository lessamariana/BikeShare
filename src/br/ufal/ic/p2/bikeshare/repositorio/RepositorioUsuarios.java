package br.ufal.ic.p2.bikeshare.repositorio;

import br.ufal.ic.p2.bikeshare.excecao.BikeShareException;
import br.ufal.ic.p2.bikeshare.excecao.UsuarioException;
import br.ufal.ic.p2.bikeshare.modelo.usuario.Email;
import br.ufal.ic.p2.bikeshare.modelo.usuario.Usuario;
import br.ufal.ic.p2.bikeshare.util.Texto;

// Usuários cadastrados, com ids

public class RepositorioUsuarios extends RepositorioComId<Usuario>
{

    private static final long serialVersionUID = 1L;

    public RepositorioUsuarios()
    {
        super("U");
    }

    public Usuario buscarPorEmail(String email)
    {
        Texto.exigir(email, "Email nao pode ser nulo.");
        return todos().stream().filter(usuario -> usuario.getEmail().corresponde(email)).findFirst().orElseThrow(this::naoEncontrado);
    }

    public boolean emailEmUso(Email email, Usuario dono)
    {
        return todos().stream().anyMatch(usuario -> usuario != dono && usuario.getEmail().mesmoEndereco(email));
    }

    @Override
    protected String mensagemIdentificacaoNula()
    {
        return "Identificacao do usuario nao pode ser nula.";
    }

    @Override
    protected BikeShareException naoEncontrado()
    {
        return new UsuarioException("Usuario nao existe.");
    }
}
