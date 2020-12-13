package program.currencyConverterAPI;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import program.currencyConverterAPI.services.CurrencyConverterService;
import program.currencyConverterAPI.services.FilePrinterService;

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
    @Autowired private final FilePrinterService filePrinterService;
    private File file;

    public FileParser(CurrencyConverterService currencyConverterService, FilePrinterService filePrinterService) {
        this.currencyConverterService = currencyConverterService;
        this.filePrinterService = filePrinterService;
    }

    public ArrayList<Float> getConvertedFromCurrencyValues() throws IOException, InterruptedException, JSONException {
        String fromCurrencyType = "", toCurrencyType = "";
        int numberOfIterations = 0;
        ArrayList<Float> fromCurrencyValues = new ArrayList<>(), toCurrencyValues = new ArrayList<>();

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
        for (float fromValue : fromCurrencyValues) {
            toCurrencyValues.add(fromValue * rate);
        }

        return toCurrencyValues;
    }

    public String getFileName() {
        return this.fileName;
    }

    public FilePrinterService getFilePrinterService() {
        return filePrinterService;
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
