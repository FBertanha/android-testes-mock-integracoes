package br.com.alura.leilao.ui;

import android.support.v7.widget.RecyclerView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import br.com.alura.leilao.database.dao.UsuarioDAO;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by felipebertanha on 21/May/2020
 */

@RunWith(MockitoJUnitRunner.class)
public class AtualizadorDeUsuarioTest {

    @Mock
    private UsuarioDAO dao;
    @Mock
    private ListaUsuarioAdapter adapter;
    @Mock
    private RecyclerView recyclerView;

    @Test
    public void deve_AtualizarListaDeUsuarios_QuandoSalvarUsuario() {
        AtualizadorDeUsuario atualizadorDeUsuario = new AtualizadorDeUsuario(dao, adapter, recyclerView);

        Usuario user = new Usuario("Felipe");
        when(dao.salva(user)).thenReturn(new Usuario(1, "Felipe"));
        when(adapter.getItemCount()).thenReturn(1);
        atualizadorDeUsuario.salva(user);

        verify(dao).salva(new Usuario("Felipe"));
        verify(adapter).adiciona(new Usuario(1, "Felipe"));
        verify(recyclerView).smoothScrollToPosition(0);

    }
}