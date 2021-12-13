/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edicaoDois;

import java.util.ArrayList;

/**
 *
 * @author debor
 */
public class EdicaoGenesTarefaPrimeira implements Runnable{
    
    private String name;
    private ArrayList<String> aSequencia1;
    private ArrayList<String> aSequencia2;
    private Edicao edicao;
 
    public EdicaoGenesTarefaPrimeira(String name, ArrayList<String> aSequencia1, ArrayList<String> aSequencia2, Edicao edicao) {
        this.name        = name;
        this.aSequencia1 = aSequencia1;
        this.aSequencia2 = aSequencia2;
        this.edicao      = edicao;
    }
     
    public String getName() {
        return name;
    }

    @Override
    public synchronized void run() {
        System.out.println("Iniciando Edicao Sequencia Inicial");
        edicao.contagemGaps(0, this.aSequencia1);
        edicao.editaSequenciaSaida(0, this.aSequencia2);
        System.out.println("Finalizando Edicao Sequencia Inicial");
    }
}
