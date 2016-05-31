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

            do{
                linha = leitor.readLine(); // Primeira linha com as palavras que serão ignoradas
                if(linha != null) {
                    linha = leitor.readLine();
                    System.out.println(linha);

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
            int index = 0;
            String linha = null;
            do{
                linha = leitor.readLine();
                if(linha != null) {
                    linha = leitor.readLine();
                    System.out.println(linha);
                    dadosTeste.add(linha);
                }
                index++;
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

        String[][] vizinhos = null;
        String[][] listAux = null;

        for (int i = 0; i< floresTreinamento.size(); i++) {
            double distancia = calcularDistancia(floresTreinamento.get(i), florY);
            System.out.println(distancia);
            listAux[i][0] = String.valueOf(floresTreinamento.get(i).getRotulo());
            System.out.println(listAux[i][0]);
            listAux[i][1] = distancia+"";
            i++;
        }
        vizinhos = getVizinhos(listAux, k);
        rotulos.add(getRotulo(vizinhos));


    }

    public static String[][] getVizinhos(String[][] array, int k){
        String[][] arrayAux = null;
        double menor = Double.parseDouble(array[0][1]);
        for(int i = 1; i < array.length; i++){
            double aux = Double.parseDouble(array[i][1]);
            // Se o array tiver a quantidade de vizinhos e ainda tiver elemento no array passado por parâmetro
            if(arrayAux.length == k){
                double maior = Double.parseDouble(arrayAux[0][1]);
                int indiceMaior = 0;
                // Procura o elemento com maior distância no arrayAux
                for (int j = 1; j < arrayAux.length; j++){
                    double aux2 = Double.parseDouble(arrayAux[j][1]);
                    if(maior > aux2){
                        maior = aux2;
                        indiceMaior = j;
                    }
                }
                // Se o maior elemento encontrado for maior que o menor elemento do array passado por parâmetro
                if(maior > menor){
                    // Adicione o elemento no lugar do elemento maior
                    arrayAux[indiceMaior][0] = array[i - 1][0];
                    arrayAux[indiceMaior][1] = array[i - 1][1];
                }
              // Vai adicionando os elementos menores no arrayAux
            } else if(arrayAux.length < k) {
                if (aux < menor) {
                    menor = aux;
                    arrayAux[i - 1][0] = array[i - 1][0];
                    arrayAux[i - 1][1] = array[i - 1][1];
                }
            }
        }
        return arrayAux;
    }

    public static int getRotulo(String[][] vizinhos){
        int contRot0 = 0;
        int contRot1 = 0;
        int contRot2 = 0;
        for (int i = 0; i < vizinhos.length; i++){
            double rotulo = Double.parseDouble(vizinhos[i][0]);
            if(rotulo == 0){
                contRot0++;
            }else if(rotulo == 1){
                contRot1++;
            }else if(rotulo == 2){
                contRot2++;
            }
        }

        if(contRot0 > contRot1 && contRot0 > contRot2){
            return contRot0;
        } else if(contRot1 > contRot0 && contRot1 > contRot2){
            return contRot1;
        } else{
            return contRot2;
        }
    }

    public static int calcularPorcentagem(){
        int total = rotulosTeste.size();
        int cont = 0;

        for(int i = 0; i < rotulos.size(); i++){
            if(rotulos.get(i) == rotulosTeste.get(i)){
                cont++;
            }
        }
        return (cont/total)*100;
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
            int k = 30;
            System.out.println(dadosTeste.get(1));
            for(int i = 0; i < dadosTeste.size(); i++){
                classificar(dadosTeste.get(i), k);
            }

            System.out.println(calcularPorcentagem()+"%");
            System.out.println("Rodou?");


        }catch (Exception e){
            e.printStackTrace();
        }



    }


}
