package mobile.shop.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import mobile.shop.holub.datastorage.table.Table;
import mobile.shop.holub.sqlengine.Database;
import mobile.shop.holub.sqlengine.text.ParseFailure;
import mobile.shop.holub.tools.FilePath;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {
    @GetMapping("/tables")
    public String hello() throws IOException, ParseFailure {
        Database database = new Database();
        BufferedReader sql = new BufferedReader(
                new FileReader(FilePath.resourceFilePath + "/createQuery.sql"));

        String test;
        while ((test = sql.readLine()) != null) {
            test = test.trim();
            if (test.length() == 0) {
                continue;
            }

            while (test.endsWith("\\")) {
                test = test.substring(0, test.length() - 1);
                test += sql.readLine().trim();
            }

            System.out.println("Parsing: " + test);
            Table result = database.execute(test);

            if (result != null)    // it was a SELECT of some sort
            {
                System.out.println(result.toString());
            }
        }
        return "Hello, Spring!";
    }
}
