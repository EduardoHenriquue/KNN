import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by Eduardo on 05/05/2016.
 */
public class LeitorArquivo {


    private List<Flor> flores;

    public LeitorArquivo() {
    }

    public void carregarDadosFlor(String nomeDoArquivo) throws IOException {
        BufferedReader leitor = null;
        try{
            leitor = new BufferedReader(new FileReader(nomeDoArquivo));
            String nameIgnored = leitor.readLine(); // Primeira linha com as palavras que serão ignoradas
            do{
                String linhaFlor = leitor.readLine();
                String[] medidas = linhaFlor.split(",");
                double sepaL = Double.parseDouble(medidas[0]);
                double sepaW = Double.parseDouble(medidas[1]);
                double petaL = Double.parseDouble(medidas[2]);
                double petaW = Double.parseDouble(medidas[3]);
                flores.add(new Flor(sepaL, sepaW, petaL, petaW));
                
            } while(leitor != null);


        } finally {
            if(leitor != null) {
                leitor.close();
            }
        }
    }
}
