/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edicaoDois;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author debor
 */
public class EdicaoGenesExecutor  {

    public static void main(String[] args) throws InterruptedException, IOException  {
        boolean isFinalizado = false;
        
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        
        long tempoInicial = System.currentTimeMillis();
        
        ArrayList<String> aSequencia1;
        ArrayList<String> aSequencia2;

        Edicao edicao = new Edicao("DENV1-X-gb_A75711.fasta", "DENV1-X-gb_A75711__"+1);
       // Edicao edicao = new Edicao("CHIKV_1-X-gb_AB455493.fasta.aln", "CHIKV_1-X-gb_AB455493__"+1);
        aSequencia1 = edicao.getSequencia1();
        aSequencia2 = edicao.getSequencia2();

        Thread edicao1 = new Thread(new EdicaoGenesTarefaPrimeira("1", aSequencia1, aSequencia2, edicao));
        Thread edicao2 = new Thread(new EdicaoGenesTarefaSegunda("2", aSequencia1, aSequencia2, edicao));

        edicao1.setPriority(10);
        edicao2.setPriority(1);
        
        executor.execute(edicao1);
        executor.execute(edicao2);
          
        executor.shutdown();
        
        while(!isFinalizado){
            isFinalizado = executor.isTerminated();
            
            if(isFinalizado){
                edicao.salvaArquivoAlterado(aSequencia1, aSequencia2);
                System.out.println("O método foi executado em " + (System.currentTimeMillis() - tempoInicial));
            }
        }
       
    }
}
