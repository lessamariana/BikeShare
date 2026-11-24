package br.ufal.ic.p2.bikeshare.modelo.plano;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Plano de tarifação contratado por um usuário.
 *
 * <p>Todo plano é descrito por uma franquia de minutos gratuitos e um valor por
 * minuto excedente (o avulso é simplesmente franquia 0). O que muda entre os
 * subtipos é a mensalidade: o avulso não tem, a assinatura tem.</p>
 *
 * <p>Planos são imutáveis: trocar de plano é trocar o objeto. Por isso o mesmo
 * modelo do {@link CatalogoPlanos} pode ser compartilhado com segurança.
 * Não é um enum porque a US12 cria planos novos em tempo de execução.</p>
 */
public abstract class Plano implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String nome;
    private final int franquiaMinutos;
    private final BigDecimal valorMinutoExcedente;

    protected Plano(String nome, int franquiaMinutos, BigDecimal valorMinutoExcedente) {
        this.nome = nome;
        this.franquiaMinutos = franquiaMinutos;
        this.valorMinutoExcedente = valorMinutoExcedente;
    }

    public String getNome() {
        return nome;
    }

    protected int getFranquiaMinutos() {
        return franquiaMinutos;
    }

    protected BigDecimal getValorMinutoExcedente() {
        return valorMinutoExcedente;
    }

    /**
     * Valor da corrida pelo plano: minutos além da franquia vezes o valor do minuto.
     * Vale para todos os planos (no avulso a franquia é zero), por isso fica na base.
     */
    public BigDecimal calcularTarifa(long minutos) {
        long minutosCobrados = Math.max(0, minutos - franquiaMinutos);
        return valorMinutoExcedente.multiply(BigDecimal.valueOf(minutosCobrados));
    }

    /** Mensalidade contratada; planos sem mensalidade recusam a consulta. */
    public abstract BigDecimal getMensalidade();

    /**
     * Cria o contrato deste plano para um usuário a partir da mensalidade digitada.
     * Cada subtipo valida a mensalidade do seu jeito (polimorfismo).
     */
    public abstract Plano contratar(String mensalidade);
}
