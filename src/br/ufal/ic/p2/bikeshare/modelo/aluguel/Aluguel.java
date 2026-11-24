package br.ufal.ic.p2.bikeshare.modelo.aluguel;

import br.ufal.ic.p2.bikeshare.config.Configuracao;
import br.ufal.ic.p2.bikeshare.excecao.AluguelException;
import br.ufal.ic.p2.bikeshare.modelo.estacao.Bicicleta;
import br.ufal.ic.p2.bikeshare.modelo.estacao.Estacao;
import br.ufal.ic.p2.bikeshare.modelo.penalidade.PenalidadeAtraso;
import br.ufal.ic.p2.bikeshare.modelo.usuario.Usuario;
import br.ufal.ic.p2.bikeshare.util.Atributos;
import br.ufal.ic.p2.bikeshare.util.Datas;
import br.ufal.ic.p2.bikeshare.util.Dinheiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Map.entry;

// Uso de uma bicicleta por um usuário, da retirada até a devolução
// Guarda referências para usuário, bicicleta e estações: mesmo depois que o usuário é removido, o aluguel encerrado continua consultável

public class Aluguel implements Serializable
{

    private static final long serialVersionUID = 1L;

    private final String id;
    private final Usuario usuario;
    private final Bicicleta bicicleta;
    private final Estacao origem;
    private final LocalDateTime inicio;
    private EstadoAluguel estado = EstadoAluguel.EM_ANDAMENTO;

    // preenchidos na devolução
    private LocalDateTime fim;
    private Estacao destino;
    private BigDecimal valorCobrado;

    // As condições da retirada já foram conferidas por quem cria o aluguel
    public Aluguel(String id, Usuario usuario, Bicicleta bicicleta, Estacao origem, LocalDateTime inicio)
    {
        this.id = id;
        this.usuario = usuario;
        this.bicicleta = bicicleta;
        this.origem = origem;
        this.inicio = inicio;
    }

    // Efetiva a retirada: a bicicleta sai da estação, entra em uso e fica vinculada ao usuário
    public void iniciar()
    {
        origem.liberar(bicicleta);
        bicicleta.iniciarUso();
        usuario.iniciarAluguel(id);
    }

    /*
     * Encerra a corrida na estação de destino.
     * Tudo que pode recusar a devolução (estado, data, vaga, saldo) é conferido
     * antes de qualquer alteração: se falhar, o aluguel segue em andamento
     */

    public void devolver(Estacao estacaoDestino, LocalDateTime momento, Configuracao configuracao)
    {
        estado.exigirEmAndamento();
        exigirPosteriorAoInicio(momento);
        estacaoDestino.exigirVagaParaDevolucao();
        long minutos = Duration.between(inicio, momento).toMinutes();
        encerrarConforme(minutos, estacaoDestino, configuracao);
        estacaoDestino.receberDevolucao(bicicleta);
        usuario.encerrarAluguel();
        this.fim = momento;
        this.destino = estacaoDestino;
    }

    private void exigirPosteriorAoInicio(LocalDateTime momento)
    {
        if(momento.isBefore(inicio))
        {
            throw new AluguelException("Data de devolucao anterior a data de retirada.");
        }
    }

    // Passou do tempo máximo, então acaba. Se voltar rápido à mesma estação, cancela, senão cobra

    private void encerrarConforme(long minutos, Estacao estacaoDestino, Configuracao configuracao)
    {
        if(minutos > configuracao.getTempoMaximoMinutos())
        {
            expirar(configuracao);
        }else if(estacaoDestino == origem && minutos <= configuracao.getCarenciaMinutos())
        {
            cancelar();
        }else
        {
            finalizar(minutos, configuracao);
        }
    }

    // o usuário recebe penalidade e a bicicleta vai para manutenção

    private void expirar(Configuracao configuracao)
    {
        usuario.receberPenalidade(new PenalidadeAtraso(configuracao.getValorPenalidadeAtraso(), id));
        bicicleta.enviarParaManutencao();
        registrarResultado(EstadoAluguel.EXPIRADO, BigDecimal.ZERO);
    }

    private void cancelar()
    {
        bicicleta.disponibilizar();
        registrarResultado(EstadoAluguel.CANCELADO, BigDecimal.ZERO);
    }

    // Valor = tarifa do plano + adicional da bicicleta
    // pagamento vem antes de mudar o estado

    private void finalizar(long minutos, Configuracao configuracao)
    {
        BigDecimal valor = usuario.calcularTarifa(minutos).add(bicicleta.calcularAdicional(minutos, configuracao));
        usuario.pagar(valor);
        bicicleta.disponibilizar();
        registrarResultado(EstadoAluguel.FINALIZADO, valor);
    }

    private void registrarResultado(EstadoAluguel novoEstado, BigDecimal valor)
    {
        this.estado = novoEstado;
        this.valorCobrado = valor;
    }

    public String getId()
    {
        return id;
    }

    public String getAtributo(String atributo)
    {
        return Atributos.consultar(leitoresDeAtributos(), atributo);
    }

    private Map<String, Supplier<String>> leitoresDeAtributos()
    {
        return Map.ofEntries(entry("estado", estado::getNome), entry("usuario", usuario::getId), entry("bicicleta", bicicleta::getId), entry("estacaoOrigem", origem::getId), entry("inicio", () -> Datas.formatar(inicio)), entry("fim", () -> dadoDeEncerramento(() -> Datas.formatar(fim))), entry("estacaoDestino", () -> dadoDeEncerramento(() -> destino.getId())), entry("valorCobrado", () -> dadoDeEncerramento(() -> Dinheiro.formatar(valorCobrado))));
    }

    //Fim, destino e valor só podem ser lidos depois da devolução

    private String dadoDeEncerramento(Supplier<String> leitor)
    {
        estado.exigirEncerrado();
        return leitor.get();
    }
}
