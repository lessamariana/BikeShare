package br.ufal.ic.p2.bikeshare.modelo.usuario;

import br.ufal.ic.p2.bikeshare.excecao.PagamentoException;
import br.ufal.ic.p2.bikeshare.modelo.pagamento.Cartao;
import br.ufal.ic.p2.bikeshare.modelo.pagamento.Pix;
import br.ufal.ic.p2.bikeshare.util.Nomeado;

// Métodos de pagamento que o usuário pode escolher e os dados que cada um exige
// Cartão exige número e validade, Pix exige a chave, saldo pré-carregado não exige nada

public enum TipoMetodoPagamento implements Nomeado
{

    CARTAO("cartao") {
        @Override
        public void aplicar(Usuario usuario, String numero, String validade)
        {
            usuario.usarMetodoPagamento(new Cartao(numero, validade));
        }
    },

    PIX("pix") {
        @Override
        public void aplicar(Usuario usuario, String chave)
        {
            usuario.usarMetodoPagamento(new Pix(chave));
        }
    },

    SALDO_PRE_CARREGADO("saldoPreCarregado") {
        @Override
        public void aplicar(Usuario usuario)
        {
            usuario.usarSaldoPreCarregado();
        }
    };

    private final String nome;

    TipoMetodoPagamento(String nome)
    {
        this.nome = nome;
    }

    @Override
    public String getNome()
    {
        return nome;
    }

    public static TipoMetodoPagamento deNome(String nome)
    {
        return Nomeado.buscar(values(), nome, TipoMetodoPagamento::valorInvalido);
    }

    public void aplicar(Usuario usuario)
    {
        throw valorInvalido();
    }

    public void aplicar(Usuario usuario, String dado)
    {
        throw valorInvalido();
    }

    public void aplicar(Usuario usuario, String dado, String complemento)
    {
        throw valorInvalido();
    }

    private static PagamentoException valorInvalido()
    {
        return new PagamentoException("Valor de metodo de pagamento invalido.");
    }
}
