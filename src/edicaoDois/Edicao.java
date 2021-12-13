package edicaoDois;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.*;

/**
 *
 * @author debor
 */
public class Edicao {

    private String diretorio;
    private int[] tracos;
    private ArrayList<String> aSequencia1;
    private ArrayList<String> aSequencia2;
    private String nome;
    
    public Edicao(String diretorio, String nome) throws IOException {
        this.diretorio = diretorio;
        this.nome      = nome;
        this.tracos    = new int[2];
        this.aSequencia1 = new ArrayList();
        this.aSequencia2 = new ArrayList();
        this.setContents();
    }
    
    private void setContents() throws IOException{
        BufferedReader Reader = new BufferedReader(new FileReader(this.diretorio));
        while (true) {
            String sLinha = Reader.readLine();
            if(sLinha == null){
                break;
            }
            
            if((this.isCabecalho(sLinha) || this.aSequencia2.size() >= 1) && this.aSequencia1.size() > 1){
                this.aSequencia2.add(sLinha);
            }
            else {
                this.aSequencia1.add(sLinha);
            }

        }
        
    }
    
    public void salvaArquivoAlterado(ArrayList<String> aSequencia1, ArrayList<String> aSequencia2) throws IOException{
        BufferedWriter Buffer = getBuffer();
        for (int iLinha = 0; iLinha < aSequencia1.size(); iLinha++) {
            if(!aSequencia1.get(iLinha).equals("")){
                Buffer.write(aSequencia1.get(iLinha));
                Buffer.newLine();
                Buffer.flush();
            }
        }
        
        for (int iLinha = 0; iLinha < aSequencia2.size(); iLinha++) {
            if(!aSequencia2.get(iLinha).equals("")){
                Buffer.write(aSequencia2.get(iLinha));
                Buffer.newLine();
                Buffer.flush();
            } 
        }      
    }
    
    public void editaSequenciaSaida(int iTipo, ArrayList<String> aSequencia2){
        String sLinha = "";
        for (int iLinha = 0; iLinha < aSequencia2.size(); iLinha++) {
            sLinha = aSequencia2.get(iLinha);
            int iCaracteres = sLinha.length();
            if(!this.isCabecalho(sLinha) && this.tracos[iTipo] > 0){
                if(iCaracteres > this.tracos[iTipo]){
                    if(iTipo == 1){
                        sLinha = this.inverteString(sLinha);
                    }
                    sLinha = sLinha.substring(this.tracos[iTipo]);
                    this.tracos[iTipo] = this.tracos[iTipo] - this.tracos[iTipo];
                }
                else {
                    this.tracos[iTipo] = this.tracos[iTipo] - iCaracteres;
                    sLinha = sLinha.substring(iCaracteres);
                }
                this.aSequencia2.set(iLinha, sLinha);
            }
            else if(this.tracos[iTipo] == 0){
                break;
            }
        }
    }
    
    private String inverteString(String sLinha){
        StringBuilder linha = new StringBuilder();
        return linha.append(sLinha).reverse().toString();
    }
    
    public void contagemGaps(int iTipo, ArrayList<String> aSequencia){
        /* pegar a quantidade da primera linha*/
        for (int iLinha = 0; iLinha < aSequencia.size(); iLinha++) {
            String sLinha = aSequencia.get(iLinha);
            if(!this.isCabecalho(sLinha)){
                if(sLinha.contains("-")){
                    this.setTracos(iTipo, this.contaQuantidade(sLinha)); /* conta a quantidade de - para edicao */
                    sLinha = sLinha.replace("-", ""); /* fazer o replace porq o novo arquivo tem que vir sem os tracos */
                    aSequencia.set(iLinha, sLinha);
                }
                else { /* quer dizer que terminou todos do inicio */
                    break; 
                }
            }
        }
    }

    public ArrayList<String> getSequencia1(){
        return this.aSequencia1;
    }
    
    public ArrayList<String> getSequencia2(){
        return this.aSequencia2;
    }
    
    private int contaQuantidade(String sLinha){
        int pos = -1;
        int contagem = 0;
        while (true) {
            pos = sLinha.indexOf("-", pos + 1); 
            if (pos < 0) {
                break;
            }
            contagem++;
        }
        return contagem;
    }
    
    /**
     * Verifica se é cabecalho
     * @param sLinha
     * @return boolean
     */
    private boolean isCabecalho(String sLinha){
        return sLinha.contains(">");
    }

    /**
     * Seta a quantidade de tracos tanto no inicio como no fim
     * @param tipo
     * @param quantidade 
     */
    private void setTracos(int tipo, int quantidade){
        this.tracos[tipo] += quantidade;
    }

    private BufferedWriter getBuffer() throws IOException{
        BufferedWriter Buffer = new BufferedWriter(new FileWriter("arquivosgerados/"+this.nome+".fasta"));
            
        return Buffer;
    }
}