package br.ufal.ic.p2.bikeshare.modelo.usuario;

import br.ufal.ic.p2.bikeshare.excecao.UsuarioException;
import br.ufal.ic.p2.bikeshare.modelo.pagamento.MetodoPagamento;
import br.ufal.ic.p2.bikeshare.modelo.pagamento.SaldoPreCarregado;
import br.ufal.ic.p2.bikeshare.modelo.pagamento.SemMetodoPagamento;
import br.ufal.ic.p2.bikeshare.modelo.penalidade.Penalidade;
import br.ufal.ic.p2.bikeshare.modelo.plano.Plano;
import br.ufal.ic.p2.bikeshare.util.Atributos;
import br.ufal.ic.p2.bikeshare.util.Dinheiro;
import br.ufal.ic.p2.bikeshare.util.Texto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Map.entry;

// Pessoa cadastrada que pode alugar bicicletas

public abstract class Usuario implements Serializable
{

    private static final long serialVersionUID = 1L;

    private static final String ATIVO = "ativo";
    private static final String INADIMPLENTE = "inadimplente";
    private static final String NENHUM = "nenhum";

    private final String id;
    private String nome;
    private Email email;
    private String senha;
    private Plano plano;
    private MetodoPagamento metodoPagamento = new SemMetodoPagamento();
    private final SaldoPreCarregado saldoPreCarregado = new SaldoPreCarregado();
    private String idAluguelAtivo;
    private final List<Penalidade> penalidades = new ArrayList<>();

    protected Usuario(String id, DadosUsuario dados)
    {
        this.id = id;
        alterarNome(dados.getNome());
        alterarEmail(new Email(dados.getEmail()));
        alterarSenha(dados.getSenha());
        this.plano = dados.getPlano();
    }

    public final void alterarNome(String novoNome)
    {
        this.nome = Texto.exigir(novoNome, "Nome nao pode ser nulo.");
    }

    public final void alterarEmail(Email novoEmail)
    {
        this.email = novoEmail;
    }

    public final void alterarSenha(String novaSenha)
    {
        this.senha = Texto.exigir(novaSenha, "Senha nao pode ser nula.");
    }

    // Escolher o plano que já tem preserva a mensalidade contratada

    public void alterarPlano(Plano modelo)
    {
        if(!plano.getNome().equals(modelo.getNome()))
        {
            plano = modelo;
        }
    }

    public void usarMetodoPagamento(MetodoPagamento novoMetodo)
    {
        this.metodoPagamento = novoMetodo;
    }

    public void usarSaldoPreCarregado()
    {
        this.metodoPagamento = saldoPreCarregado;
    }

    public void recarregarSaldo(BigDecimal valor)
    {
        metodoPagamento.recarregar(valor);
    }

    public String getId()
    {
        return id;
    }

    public Email getEmail()
    {
        return email;
    }

    public String getAtributo(String atributo)
    {
        return Atributos.consultar(leitoresDeAtributos(), atributo);
    }

    private Map<String, Supplier<String>> leitoresDeAtributos()
    {
        return Map.ofEntries(entry("nome", () -> nome), entry("email", email::toString), entry("senha", () -> senha), entry("tipo", () -> getTipo().getNome()), entry("estado", () -> estaInadimplente() ? INADIMPLENTE : ATIVO), entry("plano", plano::getNome), entry("mensalidade", () -> Dinheiro.formatar(plano.getMensalidade())), entry("aluguelAtivo", () -> temAluguelAtivo() ? idAluguelAtivo : NENHUM), entry("totalPendencias", () -> Dinheiro.formatar(getTotalPendencias())), entry("metodoPagamento", metodoPagamento::getNome), entry("numeroCartao", metodoPagamento::getNumeroCartao), entry("validadeCartao", metodoPagamento::getValidadeCartao), entry("chavePix", metodoPagamento::getChavePix), entry("saldo", () -> Dinheiro.formatar(metodoPagamento.getSaldo())), entry("cpf", this::getCpf), entry("cnpj", this::getCnpj), entry("razaoSocial", this::getRazaoSocial));
    }

    private boolean temAluguelAtivo()
    {
        return idAluguelAtivo != null;
    }

    // Regras do usuário para iniciar uma corrida

    public void validarNovaRetirada()
    {
        if(estaInadimplente())
        {
            throw new UsuarioException("Usuario inadimplente.");
        }
        if(temAluguelAtivo())
        {
            throw new UsuarioException("Usuario ja possui um aluguel em andamento.");
        }
    }

    public void autorizarRetirada()
    {
        metodoPagamento.autorizarRetirada();
    }

    public void iniciarAluguel(String idAluguel)
    {
        this.idAluguelAtivo = idAluguel;
    }

    public void encerrarAluguel()
    {
        this.idAluguelAtivo = null;
    }

    public BigDecimal calcularTarifa(long minutos)
    {
        return plano.calcularTarifa(minutos);
    }

    public void pagar(BigDecimal valor)
    {
        metodoPagamento.cobrar(valor);
    }

    public void receberPenalidade(Penalidade penalidade)
    {
        penalidades.add(penalidade);
    }

    private boolean estaInadimplente()
    {
        return getTotalPendencias().signum() > 0;
    }

    public void validarRemocao()
    {
        if(temAluguelAtivo())
        {
            throw new UsuarioException("Nao e possivel remover usuario com aluguel em andamento.");
        }
    }

    public BigDecimal getTotalPendencias()
    {
        return penalidades.stream().map(Penalidade::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public abstract TipoUsuario getTipo();

    protected String getCpf()
    {
        throw new UsuarioException("Usuario nao eh pessoa fisica.");
    }

    protected String getCnpj()
    {
        throw naoEhPessoaJuridica();
    }

    protected String getRazaoSocial()
    {
        throw naoEhPessoaJuridica();
    }

    private UsuarioException naoEhPessoaJuridica()
    {
        return new UsuarioException("Usuario nao eh pessoa juridica.");
    }
}
