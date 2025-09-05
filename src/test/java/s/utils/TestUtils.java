package s.utils;

import org.junit.BeforeClass;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class TestUtils {

	public static String getRandomValue(){
		Random random = new Random();
		int radomInt = random.nextInt(100000);
		return Integer.toString(radomInt);
	}

    public static void checkTestDataFile(String path) throws Exception {


        File file = new File(path);

        if (!file.exists()) {
            throw new IllegalStateException("❌ Test data file not found: " + path);
        }

        long lineCount = Files.lines(Paths.get(path)).count();
        if (lineCount <= 1) { // лише заголовок, без даних
            throw new IllegalStateException("❌ Test data file is empty or contains only headers: " + path);
        }

        System.out.println("✅ Test data file found with " + (lineCount - 1) + " records: " + path);
    }
}
