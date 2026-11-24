package br.ufal.ic.p2.bikeshare.servico;

import br.ufal.ic.p2.bikeshare.excecao.UsuarioException;
import br.ufal.ic.p2.bikeshare.modelo.plano.CatalogoPlanos;
import br.ufal.ic.p2.bikeshare.modelo.plano.Plano;
import br.ufal.ic.p2.bikeshare.modelo.usuario.DadosUsuario;
import br.ufal.ic.p2.bikeshare.modelo.usuario.Email;
import br.ufal.ic.p2.bikeshare.modelo.usuario.TipoMetodoPagamento;
import br.ufal.ic.p2.bikeshare.modelo.usuario.TipoUsuario;
import br.ufal.ic.p2.bikeshare.modelo.usuario.Usuario;
import br.ufal.ic.p2.bikeshare.repositorio.RepositorioUsuarios;
import br.ufal.ic.p2.bikeshare.util.Dinheiro;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

// Casos de uso de usuário (US1, US2, US3 e US4)


public class ServicoUsuarios
{

    private static final String METODO_PAGAMENTO = "metodoPagamento";

    private final RepositorioUsuarios usuarios;
    private final CatalogoPlanos planos;

    private final Map<String, BiConsumer<Usuario, String>> alteracoes;

    public ServicoUsuarios(RepositorioUsuarios usuarios, CatalogoPlanos planos)
    {
        this.usuarios = usuarios;
        this.planos = planos;
        this.alteracoes = Map.of("nome", Usuario::alterarNome, "email", this::alterarEmail, "senha", Usuario::alterarSenha, "plano", (usuario, nomePlano) -> usuario.alterarPlano(planos.buscar(nomePlano)), "saldo", (usuario, valor) -> usuario.recarregarSaldo(Dinheiro.valorPositivo(valor)), METODO_PAGAMENTO, (usuario, tipo) -> TipoMetodoPagamento.deNome(tipo).aplicar(usuario));
    }

    // Pessoa física no plano padrão (avulso)
    public String cadastrar(String nome, String email, String senha, String tipo, String cpf)
    {
        DadosUsuario dados = new DadosUsuario(nome, email, senha, planos.padrao());
        return registrar(id -> TipoUsuario.deNome(tipo).criar(id, dados, cpf));
    }

    // Pessoa jurídica no plano padrão (avulso)
    public String cadastrar(String nome, String email, String senha, String tipo, String cnpj, String razaoSocial)
    {
        DadosUsuario dados = new DadosUsuario(nome, email, senha, planos.padrao());
        return registrar(id -> TipoUsuario.deNome(tipo).criar(id, dados, cnpj, razaoSocial));
    }

    //Pessoa física com plano escolhido
    public String cadastrar(String nome, String email, String senha, String tipo, String cpf, String plano, String mensalidade)
    {
        DadosUsuario dados = new DadosUsuario(nome, email, senha, contratar(plano, mensalidade));
        return registrar(id -> TipoUsuario.deNome(tipo).criar(id, dados, cpf));
    }

    // Pessoa jurídica com plano escolhido
    public String cadastrar(String nome, String email, String senha, String tipo, String cnpj, String razaoSocial, String plano, String mensalidade)
    {
        DadosUsuario dados = new DadosUsuario(nome, email, senha, contratar(plano, mensalidade));
        return registrar(id -> TipoUsuario.deNome(tipo).criar(id, dados, cnpj, razaoSocial));
    }

    private Plano contratar(String plano, String mensalidade)
    {
        return planos.buscar(plano).contratar(mensalidade);
    }

    // Cria o usuário com o próximo id, só confirmando se o email estiver livre
    private String registrar(Function<String, Usuario> fabrica)
    {
        return usuarios.cadastrar(id -> exigirEmailLivre(fabrica.apply(id))).getId();
    }

    private Usuario exigirEmailLivre(Usuario usuario)
    {
        exigirEmailLivre(usuario.getEmail(), usuario);
        return usuario;
    }

    private void exigirEmailLivre(Email email, Usuario dono)
    {
        if(usuarios.emailEmUso(email, dono))
        {
            throw new UsuarioException("Email ja cadastrado.");
        }
    }

    public String getAtributo(String usuario, String atributo)
    {
        return usuarios.buscar(usuario).getAtributo(atributo);
    }

    public String getIdPorEmail(String email)
    {
        return usuarios.buscarPorEmail(email).getId();
    }

    // Alteração de nome, email, senha, plano, recarga de saldo ou escolha de saldo
    public void alterar(String idUsuario, String atributo, String valor)
    {
        Usuario usuario = usuarios.buscar(idUsuario);
        BiConsumer<Usuario, String> alteracao = atributo == null ? null : alteracoes.get(atributo);
        if(alteracao == null)
        {
            throw atributoNaoAlteravel();
        }
        alteracao.accept(usuario, valor);
    }

    public void alterar(String idUsuario, String atributo, String tipo, String chave)
    {
        Usuario usuario = usuarios.buscar(idUsuario);
        exigirMetodoPagamento(atributo);
        TipoMetodoPagamento.deNome(tipo).aplicar(usuario, chave);
    }
    public void alterar(String idUsuario, String atributo, String tipo, String numero, String validade)
    {
        Usuario usuario = usuarios.buscar(idUsuario);
        exigirMetodoPagamento(atributo);
        TipoMetodoPagamento.deNome(tipo).aplicar(usuario, numero, validade);
    }

    //Remove o usuário
    public void remover(String idUsuario)
    {
        Usuario usuario = usuarios.buscar(idUsuario);
        usuario.validarRemocao();
        usuarios.remover(usuario.getId());
    }

    private void alterarEmail(Usuario usuario, String endereco)
    {
        Email novoEmail = new Email(endereco);
        exigirEmailLivre(novoEmail, usuario);
        usuario.alterarEmail(novoEmail);
    }

    private void exigirMetodoPagamento(String atributo)
    {
        if(!METODO_PAGAMENTO.equals(atributo))
        {
            throw atributoNaoAlteravel();
        }
    }

    private UsuarioException atributoNaoAlteravel()
    {
        return new UsuarioException("Atributo nao pode ser alterado.");
    }
}
