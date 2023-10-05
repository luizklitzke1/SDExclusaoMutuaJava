//Alunos: Arthur B Pinotti, Kaue Reblin, Luiz Gustavo Klitzke

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Coordenador 
{
    public Map<Recurso, Queue<Solicitacao>> solicitacoesRecursos = new ConcurrentHashMap<>();
    
    public Coordenador() 
    {
        solicitacoesRecursos.put(new Recurso(6), new ConcurrentLinkedQueue<>());
        solicitacoesRecursos.put(new Recurso(9), new ConcurrentLinkedQueue<>());
    }

    public void solicitarAcessoRecurso(Solicitacao solicitacao) 
    {
        Recurso recurso = solicitacao.getRecurso();
        Queue<Solicitacao> filaSolicitacoes = this.solicitacoesRecursos.computeIfAbsent(recurso, k -> new ConcurrentLinkedQueue<>());

        if (solicitacao.getRecurso().usando() == false)
        {
            System.out.println("[ACEITO] Processo: " + solicitacao.getProcesso().getId() + " | Recurso: " + solicitacao.getRecurso().getId());
            solicitacao.getProcesso().Processa(recurso);
        } 
        else 
        {
             System.out.println("[NEGADO] Processo: " + solicitacao.getProcesso().getId() + " | Recurso: " + solicitacao.getRecurso().getId());
            filaSolicitacoes.add(solicitacao);

             String mensagemFila = "[FILA] Para o recurso " + solicitacao.getRecurso().getId() + ": ";

            for (Solicitacao solicitacaoFila : filaSolicitacoes) 
                mensagemFila += solicitacaoFila.getProcesso().getId() + ", ";

            System.out.println(mensagemFila);
        }
    }

    public void liberarRecurso(Solicitacao solicitacao) 
    {
        Recurso recurso = solicitacao.getRecurso();
        recurso.setUsando(false);
        
        System.out.println("[LIBERADO] Processo: " + solicitacao.getProcesso().getId() + "| Recurso: " + solicitacao.getRecurso().getId());

        Queue<Solicitacao> filaSolicitacoes = this.solicitacoesRecursos.get(recurso);

        if (filaSolicitacoes != null && !filaSolicitacoes.isEmpty())
        {
            Solicitacao proximaSolicitacao = filaSolicitacoes.remove();
            proximaSolicitacao.getProcesso().Processa(proximaSolicitacao.getRecurso());
        }
    }

    public Recurso obterRecursoAleatorio() 
    {
        if (this.solicitacoesRecursos.isEmpty()) 
            return null;

        List<Recurso> recursos = new ArrayList<>(this.solicitacoesRecursos.keySet());
        int índiceAleatório = new Random().nextInt(recursos.size());

        return recursos.get(índiceAleatório);
    }
}
