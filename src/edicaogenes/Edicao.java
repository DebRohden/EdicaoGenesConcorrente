package edicaogenes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
    
    /**
     * Seta o conteudo
     * 
     * @throws IOException 
     */
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
    
    /**
     * Faz as edições necessárias para o processamento
     * 
     * @throws IOException 
     */
    public void editaGene() throws IOException {
        /* contagem dos gaps iniciais*/
        this.contagemGaps(0, this.aSequencia1);
        Collections.reverse(this.aSequencia1);
        /* contagem dos gaps finais */
        this.contagemGaps(1, this.aSequencia1);
        Collections.reverse(this.aSequencia1);
        
        /* remocao das letras que estao no lugar dos gaps na sequencia 2 */
        this.editaSequenciaSaida(0, this.aSequencia2);
        Collections.reverse(this.aSequencia2);
        this.editaSequenciaSaida(1, this.aSequencia2);
        Collections.reverse(this.aSequencia2);
        
        /* salva em um novo arquivo */
        this.salvaArquivoAlterado();
    }
    
    /**
     * Salva arquivo
     * 
     * @throws IOException 
     */
    private void salvaArquivoAlterado() throws IOException{
        BufferedWriter Buffer = getBuffer();
        for (int iLinha = 0; iLinha < this.aSequencia1.size(); iLinha++) {
            if(!this.aSequencia1.get(iLinha).equals("")){
                Buffer.write(this.aSequencia1.get(iLinha));
                Buffer.newLine();
                Buffer.flush();
            }
        }
        
        for (int iLinha = 0; iLinha < this.aSequencia2.size(); iLinha++) {
            if(!this.aSequencia2.get(iLinha).equals("")){
                Buffer.write(this.aSequencia2.get(iLinha));
                Buffer.newLine();
                Buffer.flush();
            } 
        }      
    }
    
    /**
     * Edita a sequencia 2 removendo a quantia de letras de acordo com a quantia de -
     * 
     * @param iTipo
     * @param aSequencia2 
     */
    private void editaSequenciaSaida(int iTipo, ArrayList<String> aSequencia2){
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
                    this.tracos[iTipo] = 0;
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
    
    /**
     * Inverte a String
     * 
     * @param sLinha
     * @return 
     */
    private String inverteString(String sLinha){
        StringBuilder linha = new StringBuilder();
        return linha.append(sLinha).reverse().toString();
    }
    
    /**
     * Faz a iteração da sequencia 1 para a contagem de - 
     * 
     * @param iTipo
     * @param aSequencia 
     */
    private void contagemGaps(int iTipo, ArrayList<String> aSequencia){
        /* pegar a quantidade da primera linha*/
        for (int iLinha = 0; iLinha < aSequencia.size(); iLinha++) {
            String sLinha = aSequencia.get(iLinha);
            if(!this.isCabecalho(sLinha)){
                if(sLinha.contains("-")){ /* possibilidade de utilizar expressao regular*/ 
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
    
    /**
     * Conta a quantidade de - na String
     * @param sLinha
     * @return 
     */
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

    /**
     * Retorna o Buffer para gravar os arquivos
     * 
     * @return BufferedWriter
     * @throws IOException 
     */
    private BufferedWriter getBuffer() throws IOException{
        BufferedWriter Buffer = new BufferedWriter(new FileWriter("arquivosgerados/"+this.nome+".fasta"));
            
        return Buffer;
    }
}