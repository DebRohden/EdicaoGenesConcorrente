/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edicaoUmPorUm;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author debor
 */
public class EdicaoGenesExecutor  {

    public static void main(String[] args)  {
        boolean isFinalizado = false;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        
        long tempoInicial = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            EdicaoGenesTarefa edicao = new EdicaoGenesTarefa(""+i);
            executor.execute(edicao);
        }       
        executor.shutdown();
        
        while(!isFinalizado){
            isFinalizado = executor.isTerminated();
            
            if(isFinalizado){
                System.out.println("O método foi executado em " + (System.currentTimeMillis() - tempoInicial));
            }
        }
       
    }
}
