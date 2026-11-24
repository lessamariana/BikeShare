package br.ufal.ic.p2.bikeshare.modelo.usuario;

import br.ufal.ic.p2.bikeshare.excecao.UsuarioException;
import br.ufal.ic.p2.bikeshare.util.Texto;

import java.io.Serializable;
import java.util.regex.Pattern;

public final class Email implements Serializable
{

    private static final long serialVersionUID = 1L;

    private static final Pattern FORMATO = Pattern.compile("[^@\\s]+@[^@\\s]+");

    private final String endereco;

    public Email(String endereco)
    {
        String texto = Texto.exigir(endereco, "Email nao pode ser nulo.").trim();
        if(!FORMATO.matcher(texto).matches())
        {
            throw new UsuarioException("Email invalido.");
        }
        this.endereco = texto;
    }

    public boolean corresponde(String outro)
    {
        return outro != null && endereco.equalsIgnoreCase(outro.trim());
    }

    public boolean mesmoEndereco(Email outro)
    {
        return corresponde(outro.endereco);
    }

    @Override
    public String toString()
    {
        return endereco;
    }
}
