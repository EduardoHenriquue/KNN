import sun.misc.FloatingDecimal;

import java.io.*;
import java.util.*;

import static jdk.nashorn.internal.objects.Global.print;

/**
 * Created by Eduardo on 05/05/2016.
 */
public class KNN {



    public static List<Flor> floresTreinamento = new ArrayList<>(); // Armazena os Objetos Flor com os dados lidos no arquivo
    public static List<String> dadosTeste = new ArrayList<>(); // Armazena a linha com os dados de teste
    public static List<Integer> rotulos = new ArrayList<>();
    public static List<Integer> rotulosTeste = new ArrayList<>();

    public static void carregarDadosTreinamento(String nomeDoArquivo) throws IOException {
        BufferedReader leitor = null;
        try{
            leitor = new BufferedReader(new FileReader(nomeDoArquivo));
            String linha = null;
            linha = leitor.readLine(); // Primeira linha com as palavras que serão ignoradas
            do{
                linha = leitor.readLine();
                if(linha != null) {

                    String[] medidas = linha.split(",");
                    System.out.println(medidas[0] + " " + medidas[1] + " " + medidas[2] + " " + medidas[3] + " " + medidas[4]);
                    double sepaL = Double.parseDouble(medidas[0]);
                    double sepaW = Double.parseDouble(medidas[1]);
                    double petaL = Double.parseDouble(medidas[2]);
                    double petaW = Double.parseDouble(medidas[3]);
                    int rotulo = Integer.parseInt(medidas[4]);
                    floresTreinamento.add(new Flor(sepaL, sepaW, petaL, petaW, rotulo));
                }
            } while(linha != null);

        } finally {
            if(leitor != null){
                leitor.close();
            }
        }
    }

    public static void carregarDadosTeste(String nomeDoArquivo) throws IOException {
        BufferedReader leitor = null;
        try{
            leitor = new BufferedReader(new FileReader(nomeDoArquivo));

            String linha = null;
            linha = leitor.readLine();
            do{
                linha = leitor.readLine();
                if(linha != null) {
                    System.out.println(linha);
                    dadosTeste.add(linha);
                }

            } while(linha != null);

        } finally {
            if(leitor != null){
                leitor.close();
            }
        }
    }

    public static void carregarRotulosTeste(String nomeDoArquivo) throws IOException {
        BufferedReader leitor = null;
        try {
            // Lê o arquivo com os rótulos de teste
            leitor = new BufferedReader(new FileReader(nomeDoArquivo));
            String linha = null;
            do{
                linha = leitor.readLine();
                if(linha != null) {
                    rotulosTeste.add(Integer.parseInt(linha));
                }
            }while (linha != null);
        }finally {
            if(leitor != null){
                leitor.close();
            }
        }
    }


    public static double calcularDistancia(Flor florX, String florY){
        String[] flor = florY.split(",");
        double sepaLXY = Math.pow((florX.getSepallength() - Double.parseDouble(flor[0])),2);
        double sepaWXY = Math.pow((florX.getSepalwidth() - Double.parseDouble(flor[1])),2);
        double petaLXY = Math.pow((florX.getPetallength() - Double.parseDouble(flor[2])),2);
        double petaWXY = Math.pow((florX.getPetalwidth() - Double.parseDouble(flor[3])),2);

        return Math.sqrt(sepaLXY + sepaWXY + petaLXY + petaWXY);
    }

    public static void classificar(String florY, int k){

        List<Integer> vizinhos;
        List<Vizinho> listAux = new ArrayList<>();

        for (int i = 0; i < floresTreinamento.size(); i++) {
            double distancia = calcularDistancia(floresTreinamento.get(i), florY);
            listAux.add(new Vizinho(floresTreinamento.get(i).getRotulo(), distancia));
        }

        vizinhos = getVizinhos(listAux, k); // Obtém os vizinhos da flor
        int rotuloFlor = getRotulo(vizinhos); // Obtém o rótulo que mais se repete
        rotulos.add(rotuloFlor);
    }


    public static List<Integer> getVizinhos(List<Vizinho> listV, int k){
        List<Integer> arrayAux = new ArrayList<>();

        Collections.sort(listV);
        for(int i = 0; i < k; i++){
            arrayAux.add(listV.get(i).getRotulo());
        }

        return arrayAux;
    }


    public static int getRotulo(List<Integer> vizinhos){
        int contRot0 = 0;
        int contRot1 = 0;
        int contRot2 = 0;
        for (int i = 0; i < vizinhos.size(); i++){
            int rotulo = vizinhos.get(i);
            if(rotulo == 0){
                contRot0++;
            }else if(rotulo == 1){
                contRot1++;
            }else if(rotulo == 2){
                contRot2++;
            }
        }

        if(contRot0 > contRot1 && contRot0 > contRot2){
            return 0;
        } else if(contRot1 > contRot0 && contRot1 > contRot2){
            return 1;
        } else{
            return 2;
        }
    }

    public static int calcularPorcentagem(){
        // Comparar os arquivos e calcular a porcentagem
        return 0;
    }

    public static void gravarDados(List<Integer> rotulos, String nomeArquivo) throws IOException {
        BufferedWriter gravador = null;
        try{
            gravador = new BufferedWriter(new FileWriter(nomeArquivo));
            for(Integer i: rotulos){
                gravador.write(i+"\n");
            }
        } finally {
            if (gravador != null){
                gravador.close();
            }
        }
    }


    public static void main(String[] args){
        try {
            // Carregar dados de aprendizagem
            carregarDadosTreinamento("treinamento.csv");
            // Carregar dados de teste
            carregarDadosTeste("teste.csv");
            carregarRotulosTeste("rotulos-teste.txt");
            int k = 11;

            for(int i = 0; i < dadosTeste.size(); i++){
                classificar(dadosTeste.get(i), k);
            }
            System.out.println(dadosTeste.size());
            gravarDados(rotulos, "rotulosClassificados.txt");
            

        }catch (Exception e){
            e.printStackTrace();
        }



    }


}
