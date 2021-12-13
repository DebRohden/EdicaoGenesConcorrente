/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edicaoSerial;

import edicaogenes.Edicao;
import java.io.IOException;

/**
 *
 * @author debor
 */
public class EdicaoGenesSerie {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        long tempoInicial = System.currentTimeMillis();
        Edicao edicao;
                
        //for (int i = 0; i < 10000; i++) {
        //    System.out.println( i + ": editando Gene");
         edicao = new Edicao("DENV1-X-gb_A75711.fasta", "DENV1-X-gb_A75711__"+1);
            //edicao = new Edicao("CHIKV_1-X-gb_AB455493.fasta.aln", "CHIKV_1-X-gb_AB455493__");
//            edicao = new Edicao("DENV1-X-gb_A75711.fasta", "DENV1-X-gb_A75711__"+i);
            edicao.editaGene();
            
       // }
        
        System.out.println("O método foi executado em " + (System.currentTimeMillis() - tempoInicial));
    }
    
}
