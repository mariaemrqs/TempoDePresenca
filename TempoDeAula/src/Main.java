import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //para ler o arquivo:
        String caminhoArquivo = "C:/Users/maria/IdeaProjects/ExerciciosModulo4Santander/TempoDeAula/dados.csv";
        Map<String, Long> temposDePresenca = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //convertendo o formato da data e hora pro padrao br

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            br.readLine(); //pra pular a primeira linha (cabeçalhos)

            //processa o arquivo linha por linha:
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(","); //ja que o CSV é separado por vírgulas
                String nomeAluno = dados[0]; //dizendo que a primeira linha é o nome dos alunos

                //tenta ler os horários de entrada e saída:
                try {
                    LocalDateTime entrada = LocalDateTime.parse(dados[1], formatter);
                    LocalDateTime saida = LocalDateTime.parse(dados[2], formatter);

                    long minutosPresente = Duration.between(entrada, saida).toMinutes();

                    //acumula o tempo de presença do aluno:
                    temposDePresenca.put(nomeAluno, temposDePresenca.getOrDefault(nomeAluno, 0L) + minutosPresente);

                } catch (DateTimeParseException e) {
                    System.out.println("Erro ao processar os horários do aluno: " + nomeAluno + " - " + e.getMessage());
                }
            }

            //verifica quem atingiu o tempo mínimo de 135 minutos:
            for (Map.Entry<String, Long> entrada : temposDePresenca.entrySet()) {
                String aluno = entrada.getKey();
                long minutos = entrada.getValue();

                if (minutos >= 135) {
                    System.out.println(aluno + " está presente com " + minutos + " minutos.");
                } else {
                    System.out.println(aluno + " está ausente com " + minutos + " minutos.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

