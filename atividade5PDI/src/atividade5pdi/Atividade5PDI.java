/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividade5pdi;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author SAMSUNG
 */
public class Atividade5PDI {

    /**
     * @param args the command line arguments
     */
    
    public static int lim;
    public static int linha;
    public static int coluna;
    
    public static int[][] criaMatriz(Scanner i){
        
        String lixo = i.nextLine();
        //System.out.println(lixo);
        lixo = i.nextLine();
        //System.out.println(lixo);
        
        linha = i.nextInt();
        coluna = i.nextInt();
        
        
        int[][] Matriz = new int[linha + 1][coluna + 1];
        
        
        lim = i.nextInt();
        
        for(int x = 0; x<linha; x++){
            for(int y = 0; y<coluna; y++)
            {
                Matriz[x][y] = i.nextInt();
            }
            
        }
        System.out.println("Colunas: "+coluna+" Linhas: "+linha);
        return Matriz;
    }
    
    public static void salvarImagem(int[][] imagem, String nameFile){
        
        
        
        try {
        FileWriter fw = new FileWriter(nameFile);
        fw.write("P2");
        fw.write("\n");
        fw.write(Integer.toString(linha));
        fw.write(" ");
        fw.write(Integer.toString(coluna));
        fw.write("\n");
        fw.write(Integer.toString(lim));
        fw.write("\n");
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                    fw.write(imagem[i][j] + " ");
            }
            //fw.write("\n");
        }
        fw.flush();
        } catch (IOException e) {}
    }
    
    public static int[][] filtroLaplaciano(int [][] matriz,  int altura, int largura, int filtro, int tipo){
        int j, i; 
        double novopx = 0;
        double novamatriz[][] = new double[largura][altura];
        int [][] matrizFiltro1 = {{ 0, -1,  0}, {-1, 4, -1}, { 0, -1, 0}};
        int [][] matrizFiltro2 = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};
        int vetorpx[] = new int[altura*largura];
        int [][] matrizFiltro;
        int aux; //deixa a distancia negativa para posicionar a leitura no lugar correto
        int aux1;
        int aux2;
        
        int [][] resultadoFinal;
        
        if(filtro == 0)
            matrizFiltro = matrizFiltro1;
        else
            matrizFiltro = matrizFiltro2;
        int distancia = matrizFiltro.length/2; //distancia do centro da matriz ate a borda
        for(i = distancia; i < altura; i++){
            for(j = distancia; j < largura; j++){
                //aux = -distancia; //para i e j da matriz original, atribui os valores da distancia ao aux
                aux1 = distancia * (-1);
                for(int x = 0; x < (distancia*2) + 1; x++){
                    aux2 = distancia * (-1);
                    for(int y = 0; y < (distancia*2) + 1; y++){
                        
                      novopx += (int)(matriz[i+aux1][j+aux2] * matrizFiltro[x][y]);
                      aux2++; 
                    }
                    aux1++;
                }
                novamatriz[j][i] = novopx;
                novopx=0;
            }
        }		       
        if(tipo == 1)
            novamatriz = somaLaplaciano(matriz, novamatriz);   
        int k = 0;
        for(i = 0; i < largura; i++){
            for(j = 0; j < altura; j++){
                vetorpx[k] = (int)novamatriz[i][j];
                k++;
            }
        }
        
        resultadoFinal = convertArraytoMatriz(vetorpx);
        
        return resultadoFinal;
    }
    
    public static double[][] somaLaplaciano(int [][] original, double [][] resultante){
        double [][] novamatriz = resultante;
        for (int i = 0; i < novamatriz.length; i++) {
            for (int j = 0; j < novamatriz[1].length; j++) {
                if(resultante[i][j] + original[i][j] < 256 && resultante[i][j] + original[i][j] >= 0)
                    novamatriz[i][j] = resultante[i][j] + original[i][j];
                else if (resultante[i][j] + original[i][j] > 255)
                    novamatriz[i][j] = 255;
                else if (resultante[i][j] + original[i][j] < 0)
                    novamatriz[i][j] = 0;
            }
        }
        return novamatriz;
    }
    
    public static int[][] convertArraytoMatriz(int [] arranjo){
        int[][] matriz = new int[linha][coluna];
        
        int k = 0;
        
        for(int i = 0; i<linha; i++)
        {
            for(int j = 0; j<coluna; j++)
            {
                matriz[i][j] = arranjo[k];
                k++;
            }
        }
        
        return matriz;
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        
        Scanner in = new Scanner(new FileReader("lua.pgm"));
        
        int[][] imagem;
        int[][] laplaciano1;
        int[][] laplaciano2;
        
        
        imagem = criaMatriz(in);
        
        laplaciano1 = filtroLaplaciano(imagem, linha, coluna, 2, 0);
        
        laplaciano2 = filtroLaplaciano(imagem, linha, coluna, 0, 0);
        
        salvarImagem(laplaciano1, "laplaciano1.pgm");
        salvarImagem(laplaciano2, "laplaciano2.pgm");
        
        //salvarImagem(imagem, "copia.pgm");
        
        
    }
    
}
