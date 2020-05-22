package br.com.alura.leilao.ui;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

/**
 * Created by felipebertanha on 14/May/2020
 */

@RunWith(MockitoJUnitRunner.class)
public class AtualizadorDeLeiloesTest {
    @Mock
    private ListaLeilaoAdapter adapter;
    @Mock
    private LeilaoWebClient client;
    @Mock
    private AtualizadorDeLeiloes.ErroCarregaLeiloesListener erroListener;

    @Test
    public void deve_AtualizarListaLeiloes_QuandoBuscarLeiloesDaApi() {
        AtualizadorDeLeiloes atualizador = new AtualizadorDeLeiloes();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                RespostaListener<List<Leilao>> argument = invocation.getArgument(0);

                argument.sucesso(new ArrayList<Leilao>(Arrays.asList(
                        new Leilao("Panela de pressao"),
                        new Leilao("Carro")
                )));
                return null;
            }
        }).when(client).todos(ArgumentMatchers.any(RespostaListener.class));

        atualizador.buscaLeiloes(adapter, client, erroListener);
        verify(client).todos(ArgumentMatchers.any(RespostaListener.class));
        verify(adapter).atualiza(new ArrayList<Leilao>(Arrays.asList(
                new Leilao("Panela de pressao"),
                new Leilao("Carro")
        )));

    }

    @Test
    public void deve_ExibirMensagemDeFalha_QuandoFalharAoBuscarLeiloes() {
        AtualizadorDeLeiloes atualizador = Mockito.spy(new AtualizadorDeLeiloes());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RespostaListener<List<Leilao>> listener = invocation.getArgument(0);
                listener.falha(anyString());
                return null;
            }
        }).when(client).todos(ArgumentMatchers.any(RespostaListener.class));


        atualizador.buscaLeiloes(adapter, client, erroListener);

        verify(erroListener).erroAoCarregar(anyString());
    }

}