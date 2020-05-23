package br.com.alura.leilao.api;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by felipebertanha on 23/May/2020
 */

@RunWith(MockitoJUnitRunner.class)
public class EnviadorDeLanceTest {
    @Mock
    private LeilaoWebClient client;
    @Mock
    private EnviadorDeLance.LanceProcessadoListener listener;
    @Mock
    private Context context;
    @Mock
    private AvisoDialogManager manager;
    @Mock
    private Leilao leilao;

    @Test
    public void deve_mostrarMensagemDeFalha_QuandoLanceForMenorQueUltimoLance() {

        EnviadorDeLance enviador = new EnviadorDeLance(client,
                listener,
                context,
                manager);

        doThrow(LanceMenorQueUltimoLanceException.class)
                .when(leilao).propoe(ArgumentMatchers.any(Lance.class));

        enviador.envia(leilao, new Lance(new Usuario("Evelyn"), 50));

        verify(manager).mostraAvisoLanceMenorQueUltimoLance(context);


    }

    @Test
    public void deve_mostrarMensagemDeFalha_QuandoUsuarioComCincoLancesDerNovoLance() {
        EnviadorDeLance enviador = new EnviadorDeLance(client,
                listener,
                context,
                manager);


        doThrow(UsuarioJaDeuCincoLancesException.class)
                .when(leilao).propoe(ArgumentMatchers.any(Lance.class));

        enviador.envia(leilao, new Lance(new Usuario("Evelyn"), 50));

        verify(manager).mostraAvisoUsuarioJaDeuCincoLances(context);
    }

}