package com.example.otends.utils;

import com.example.otends.entities.OThread;
import com.example.otends.entities.RawDataCfg;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DataParser {
    public static float MAX = Float.NEGATIVE_INFINITY;
    public static ObjectMapper mapper = new ObjectMapper();
    public DataParser() {

    }

    public static List<List<String>> getData(RawDataCfg cfg, UUID id) {
        List<List<String>> json;
        if(cfg.apiURL != null) {
            json = getDataFromApi(cfg.apiURL);
        } else {
            json = getDataFromSystem(id);
        }
        return json;
    }

    public static List<List<String>> getDateFilteredData() {
        Date today = new Date();
        int index = 0;
        int[] currentDate = new int[3];
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
        for(String patt: formatter.format(today).split("-")) {
            currentDate[index] = Integer.parseInt(patt);
            index++;
        }
        return null;
    }

    public static List<List<String>> getDataFromApi(String url) {
        try {
            String jsonApi = APIDowloader.get(url);
            return mapper.readValue(jsonApi, new TypeReference<List<List<String>>>() {});
        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<List<String>> getDataFromSystem(UUID id) {
        try {
            File foodMind = new File(OThread.BRAIN_BASEURL, "food/" + id + ".json");
            if(foodMind.exists()) {
                String[][] mappedFood = mapper.readValue(foodMind, String[][].class);
                return Arrays.stream(mappedFood)
                        .map(Arrays::asList)
                        .collect(Collectors.toList());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double[][] renderData(List<List<String>> rawData, RawDataCfg cfg) {
        if(rawData == null) {
            return null;
        }

        double[][] renderedData = renderTxtData(rawData);
        if(cfg.isBinary) {
            DataParser.meanParse(renderedData);
        }
        return renderedData;
    }

    public static double[][] renderTxtData(List<List<String>> data) {
        double[][] parseData = new double[data.size()][data.get(0).size()];
        for(int i = 0; i < data.size(); i++) {
            for(int j = 0; j < data.get(0).size(); j++) {
                if(!data.get(i).get(j).matches("-?\\d+(\\.\\d+)?")) {
                    parseData[i][j] = DataParser.strParse(data.get(i).get(j));
                } else {
                    parseData[i][j] = Double.parseDouble(data.get(i).get(j));
                }
            }
        }
        return parseData;
    }

    public static double[][] renderOverOutput(double[][] data, RawDataCfg cfg) {
        if(isOutOfRange01(data, cfg)) {
            double[] max = new double[cfg.outputRows];
            for(double[] row: data) {
                int index = 0;
                for(int i = cfg.inputRows; i < cfg.inputRows + cfg.outputRows; i++) {
                    if(max[index] < row[i]) {
                        max[index] = row[i];
                    }
                    index++;
                }
            }

            for(double[] row: data) {
                int index = 0;
                for(int i = cfg.inputRows; i < cfg.inputRows + cfg.outputRows; i++) {
                    row[i] /= max[index];
                    index++;
                }
            }
        }
        return data;
    }

    private static boolean isOutOfRange01(double[][] data, RawDataCfg cfg) {
        for(double[] row: data) {
            for(int i = cfg.inputRows; i < cfg.inputRows + cfg.outputRows; i++) {
                if(row[i] > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static double[][] meanParse(double[][] data) {
        DataParser.setMAX(data);
        for(int i = 0; i < data.length; i++) {
            data[i] = DataParser.meanParse(data[i]);
        }
        return data;
    }

    public static double[] meanParse(double[] data) {
        if(DataParser.MAX == Float.NEGATIVE_INFINITY) {
            DataParser.setMAX(data);
        }

        for(int i = 0; i < data.length; i++) {
            data[i] = data[i]/DataParser.MAX;
        }
        return data;
    }

    private static void setMAX(double[][] data) {
        float max = 0;
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[i].length; j++) {
                if(max < data[i][j]) {
                    max = (float) data[i][j];
                }
            }
        }
        DataParser.MAX = max;
    }

    private static void setMAX(double[] data) {
        float max = 0;
        for(int i = 0; i < data.length; i++) {
            if(data[i] > max) {
                max = (float) data[i];
            }
        }
        DataParser.MAX = max;
    }

    public static double strParse(String data) {
        BigInteger bigInt = new BigInteger(data.getBytes());
        return bigInt.doubleValue();
    }
}
