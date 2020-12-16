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

    private String fileName;
    @Autowired private final CurrencyConverterService currencyConverterService;
    @Autowired private final FilePrinterService filePrinterService;
    private float rate;

    public FileParser(@Value("${file.name}") String fileName, CurrencyConverterService currencyConverterService, FilePrinterService filePrinterService) {
        setFileName(fileName);
        this.currencyConverterService = currencyConverterService;
        this.filePrinterService = filePrinterService;
    }

    public ArrayList<Float> getConvertedFromCurrencyValues() throws IOException, InterruptedException, JSONException {
        String fromCurrencyType = "", toCurrencyType = "";
        int numberOfIterations = 0;
        ArrayList<Float> fromCurrencyValues = new ArrayList<>(), toCurrencyValues = new ArrayList<>();

        Path filePath = Paths.get(FILES_PATH, getFileName() + FILE_EXTENSION_TXT);
        File file = new File(filePath.toString());
        try (Scanner reader = new Scanner(file)) {
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

        setRate(getCurrencyConverterService().getSpecificRateBetween(fromCurrencyType, toCurrencyType));

        for (float fromValue : fromCurrencyValues) {
            toCurrencyValues.add(fromValue * getRate());
        }

        return toCurrencyValues;
    }

    public String getFileName() {
        return this.fileName;
    }

    public FilePrinterService getFilePrinterService() {
        return filePrinterService;
    }

    public float getRate() {
        return this.rate;
    }

    private void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private void setRate(float rate) {
        this.rate = rate;
    }

    private CurrencyConverterService getCurrencyConverterService() {
        return currencyConverterService;
    }

}
