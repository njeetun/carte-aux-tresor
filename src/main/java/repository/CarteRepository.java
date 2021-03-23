package repository;

import exceptions.RepositoryCarteException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarteRepository {

    /**
     * Lecture du fichier en entrée
     *
     * @param filename nom du fichier
     * @return une liste de string : contenu du fichier
     * @throws RepositoryCarteException
     */
    public List<String> readFile(String filename) throws RepositoryCarteException {

        List<String> fileContent = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(filename))) {

            if (stream != null) {
                fileContent = stream.collect(Collectors.toList());
            }

        } catch (IOException e) {
            String messageError = "Une erreur s'est produite lors de la lecture du fichier : " + filename;
            throw new RepositoryCarteException(messageError, e);
        }

        return fileContent;

    }


    /**
     * Ecriture du contenu dans un fichier
     *
     * @param fileName le nom du fichier
     * @param content  le contenu
     * @return un fichier
     * @throws IOException
     */
    public File createFileWithContent(String fileName, String content) throws IOException {

        File file = new File(fileName);
        try (Writer writer = new FileWriter(file); BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            if (!file.exists() && !file.createNewFile()) {
                return null;
            }
            bufferedWriter.write(content);
        }
        return file;
    }
}
