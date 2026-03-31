package framework.utils;

import org.testng.ITestContext;

import java.util.ArrayList;
import java.util.List;

public class ExcelDataProvider {

    private static final String EXCEL_PATH = "testdata/login_data.xlsx";

    public static Object[][] getSmokeCases() {
        ExcelReader reader = new ExcelReader(EXCEL_PATH);

        try {
            List<String[]> rows = reader.readSheet(
                    "SmokeCases",
                    "username", "password", "expected_url", "description"
            );

            Object[][] data = new Object[rows.size()][4];
            for (int i = 0; i < rows.size(); i++) {
                data[i][0] = rows.get(i)[0];
                data[i][1] = rows.get(i)[1];
                data[i][2] = rows.get(i)[2];
                data[i][3] = rows.get(i)[3];
            }
            return data;
        } finally {
            reader.close();
        }
    }

    public static Object[][] getNegativeAndBoundaryCases() {
        ExcelReader reader = new ExcelReader(EXCEL_PATH);

        try {
            List<String[]> allRows = new ArrayList<>();

            allRows.addAll(reader.readSheet(
                    "NegativeCases",
                    "username", "password", "expected_error", "description"
            ));

            allRows.addAll(reader.readSheet(
                    "BoundaryCases",
                    "username", "password", "expected_error", "description"
            ));

            Object[][] data = new Object[allRows.size()][4];
            for (int i = 0; i < allRows.size(); i++) {
                data[i][0] = allRows.get(i)[0];
                data[i][1] = allRows.get(i)[1];
                data[i][2] = allRows.get(i)[2];
                data[i][3] = allRows.get(i)[3];
            }
            return data;
        } finally {
            reader.close();
        }
    }

    public static Object[][] getRegressionCases() {
        ExcelReader reader = new ExcelReader(EXCEL_PATH);

        try {
            List<Object[]> allRows = new ArrayList<>();

            List<String[]> smokeRows = reader.readSheet(
                    "SmokeCases",
                    "username", "password", "expected_url", "description"
            );

            for (String[] row : smokeRows) {
                allRows.add(new Object[]{"SMOKE", row[0], row[1], row[2], row[3]});
            }

            List<String[]> negativeRows = reader.readSheet(
                    "NegativeCases",
                    "username", "password", "expected_error", "description"
            );

            for (String[] row : negativeRows) {
                allRows.add(new Object[]{"NEGATIVE", row[0], row[1], row[2], row[3]});
            }

            List<String[]> boundaryRows = reader.readSheet(
                    "BoundaryCases",
                    "username", "password", "expected_error", "description"
            );

            for (String[] row : boundaryRows) {
                allRows.add(new Object[]{"BOUNDARY", row[0], row[1], row[2], row[3]});
            }

            Object[][] data = new Object[allRows.size()][5];
            for (int i = 0; i < allRows.size(); i++) {
                data[i] = allRows.get(i);
            }
            return data;
        } finally {
            reader.close();
        }
    }

    public static boolean isSmokeRun(ITestContext context) {
        for (String group : context.getIncludedGroups()) {
            if ("smoke".equalsIgnoreCase(group)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRegressionRun(ITestContext context) {
        for (String group : context.getIncludedGroups()) {
            if ("regression".equalsIgnoreCase(group)) {
                return true;
            }
        }
        return false;
    }
}