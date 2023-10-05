//Alunos: Arthur B Pinotti, Kaue Reblin, Luiz Gustavo Klitzke

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Coordenador 
{
    private Map<Recurso, Queue<Solicitacao>> solicitacoesRecursos = new ConcurrentHashMap<>();

    private List<Recurso> recursos; // Apenas referência de controle para os presentes na classe geral
 
    public Coordenador(List<Recurso> recursos) 
    {
        this.recursos = recursos;
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
        if (this.recursos.isEmpty()) 
            return null;

        int indiceAleatorio = new Random().nextInt(this.recursos.size());

        return recursos.get(indiceAleatorio);
    }
}
