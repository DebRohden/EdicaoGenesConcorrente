/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edicaoUmPorUm;

import edicaogenes.Edicao;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author debor
 */
public class EdicaoGenesTarefa implements Runnable{
    
    private String name;
 
    public EdicaoGenesTarefa(String name) {
        this.name = name;
    }
     
    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread " + this.name + ": editando Gene");
            Edicao edicao = new Edicao("CHIKV_1-X-gb_AB455493.fasta.aln", "CHIKV_1-X-gb_AB455493__"+this.name);
//            Edicao edicao = new Edicao("DENV1-X-gb_A75711.fasta", "DENV1-X-gb_A75711__"+this.name);
            edicao.editaGene();
        } catch (IOException ex) {
            Logger.getLogger(EdicaoGenesExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
