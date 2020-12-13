package program.currencyConverterAPI;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import program.currencyConverterAPI.services.CurrencyConverterService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static program.constants.Constants.FILES_PATH;
import static program.constants.Constants.FILE_EXTENSION_TXT;

@Controller
public class FileParser {

    @Value("${file.name}") private String fileName;
    @Autowired private final CurrencyConverterService currencyConverterService;
    private File file;

    public FileParser(CurrencyConverterService currencyConverterService) {
        this.currencyConverterService = currencyConverterService;
    }

    public void convertFileFromCurrencyValues() throws IOException, InterruptedException, JSONException {
        String fromCurrencyType = "", toCurrencyType = "";
        int numberOfIterations = 0;
        ArrayList<Float> fromCurrencyValues = new ArrayList<>();

        System.out.println("before: ");
        Path filePath = Paths.get(FILES_PATH, getFileName() + FILE_EXTENSION_TXT);
        setFile(new File(filePath.toString()));
        try (Scanner reader = new Scanner(getFile())) {
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
                if (numberOfIterations == 0) {
                    fromCurrencyType = data;
                }
                else if (numberOfIterations == 1) {
                    toCurrencyType = data;
                }
                else
                    fromCurrencyValues.add(Float.parseFloat(data));

                numberOfIterations++;
            }
        }

        float rate = getCurrencyConverterService().getSpecificRateBetween(fromCurrencyType, toCurrencyType);

        System.out.println("after: ");
        for (float fromValue : fromCurrencyValues) {
            System.out.printf("%.2f\n", fromValue * rate);
        }
    }

    public String getFileName() {
        return this.fileName;
    }

    private File getFile() {
        return file;
    }

    private CurrencyConverterService getCurrencyConverterService() {
        return currencyConverterService;
    }

    private void setFile(File file) {
        this.file = file;
    }
}
