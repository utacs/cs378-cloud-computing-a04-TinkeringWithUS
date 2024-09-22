package edu.cs.utexas.HadoopEx;

import java.io.FileWriter;

public class LineValidator {

    private static final String ERROR_FILENAME = "error.txt";

    FileWriter errorWriter;

    LineValidator() {
        try {
            errorWriter = new FileWriter(ERROR_FILENAME);

            // erase previous error
            errorWriter.write("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endValidation() {
        try {
            errorWriter.flush();
            errorWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ErrorType validateLine(String line) {
        String[] fields = line.split(",");

        if (isLineInvalid(fields)) {
            return ErrorType.INVALID_LINE;
        }

        final int GPS_START = 6;
        final int GPS_END = 9;

        for (int gpsIndex = GPS_START; gpsIndex <= GPS_END; gpsIndex++) {
            if (fields[gpsIndex].length() == 0) {
                recordError("Empty string when checking gps data. gps index: " +
                        gpsIndex, ErrorType.GPS_ERROR);
                return ErrorType.GPS_ERROR;
            }

            try {
                float gpsData = Float.parseFloat(fields[gpsIndex]);

                if (gpsData == 0) {
                    recordError("GPS Data at index: " + gpsIndex + ". is 0. ", ErrorType.GPS_ERROR);
                    return ErrorType.GPS_ERROR;
                }
            } catch (Exception e) {
                return ErrorType.GPS_ERROR;
            }
        }

        for (int field = 0; field < fields.length; field++) {
            if (fields[field].length() == 0) {
                recordError("Empty field at index: " + field, ErrorType.INVALID_LINE);
                return ErrorType.INVALID_LINE;
            }
        }

        return ErrorType.NO_ERROR;
    }

    private boolean isLineInvalid(String[] fields) {
        if (fields.length != 17) {
            recordError("Number of comma mis match. Num commas: " +
                    fields.length + ".", ErrorType.INVALID_LINE);
            return true;
        }

        final int TRIP_TIME_INDEX = 4;

        try {
            Float.parseFloat(fields[TRIP_TIME_INDEX]);
        } catch (Exception e) {
            return true;
        }

        // check if all money values indices = {16 ... 11} can be casted to a float
        final int MONEY_START_INDEX = 11;
        final int MONEY_END_INDEX = 16;

        final int TOTAL_AMOUNT_MONEY_INDEX = 16;

        float totalAmount = 0f;

        float sumOfAllMoney = 0f;
        for (int money_index = MONEY_START_INDEX; money_index <= MONEY_END_INDEX; money_index++) {
            try {
                sumOfAllMoney += Float.parseFloat(fields[money_index]);
                if (money_index == TOTAL_AMOUNT_MONEY_INDEX) {
                    totalAmount = Float.parseFloat(fields[TOTAL_AMOUNT_MONEY_INDEX]);
                }
            } catch (Exception e) {
                System.out.println("money indice no convert");
                recordError("One of the indices for money cannot be casted to a float. " +
                        " money index: " + money_index, ErrorType.INVALID_LINE);
                return true;
            }
        }

        // sumOfAllMoney = total amount + rest of money
        // remove taxis with total amount > 500 USD
        float ACCEPTABLE_FLOAT_THRESHOLD = 0.0001f;

        if ((sumOfAllMoney / 2) - 2 * totalAmount >= ACCEPTABLE_FLOAT_THRESHOLD) {
            recordError("Mismatch between total amount and rest of dollar amounts. Total Amount: " +
                    totalAmount + ". sum of rest: " + (sumOfAllMoney / 2), ErrorType.INVALID_LINE);
            return true;
        }

        // final float TOTAL_AMOUNT_THRESHOLD = 0.001f;

        // if (totalAmount > TOTAL_AMOUNT_THRESHOLD) {
        //     recordError("Total amount greater than 500 threshold.", ErrorType.INVALID_LINE);
        //     return true;
        // }

        return false;
    }

    private void recordError(String message, ErrorType errorType) {
        String errorTypeOutput = null;

        switch (errorType) {
            case INVALID_LINE:
                errorTypeOutput = "Invalid Line. ";
                break;
            case GPS_ERROR:
                errorTypeOutput = "GPS Error. ";
                break;
            default:
                break;
        }

        try {
            if (errorTypeOutput != null) {
                errorWriter.append(errorTypeOutput);
            }
            errorWriter.append(message);
            errorWriter.write("\n");
            errorWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
