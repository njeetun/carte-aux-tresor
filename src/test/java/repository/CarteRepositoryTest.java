package repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class CarteRepositoryTest {

    private CarteRepository carteRepository;

    @Before
    public void init() {
        carteRepository = new CarteRepository();
    }


    @Test
    public void createFileWithContentTest() throws IOException {

        String filename = "carte-aux-tresors";

        String contents = "C - 3 - 4\n" +
                "M - 1 - 0\n" +
                "M - 2 - 1";

        File file = carteRepository.createFileWithContent(filename, contents);
        Assert.assertNotNull(file);

    }
}