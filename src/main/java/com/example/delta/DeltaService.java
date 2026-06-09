package com.example.delta.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeltaService {

    private final SparkSession sparkSession;

    public List<Map<String, Object>> listDeltaTables() {
        log.info("Listing Delta Lake tables");
        try {
            String deltaPath = System.getProperty("delta.lake.path", "file:///tmp/delta");
            log.info("Using Delta Lake path: {}", deltaPath);

            List<Map<String, Object>> tables = new ArrayList<>();
            Map<String, Object> info = new HashMap<>();
            info.put("message", "To use Delta Lake tables, configure the delta.lake.path property");
            info.put("default_path", deltaPath);
            info.put("note", "Place your Delta Lake tables at: " + deltaPath);
            tables.add(info);

            return tables;
        } catch (Exception e) {
            log.error("Error listing Delta tables", e);
            return Collections.singletonList(Map.of(
                    "error", e.getMessage(),
                    "status", "failed"
            ));
        }
    }

    public Map<String, Object> readDeltaTable(String tableName) {
        log.info("Reading Delta table: {}", tableName);
        Map<String, Object> result = new HashMap<>();

        try {
            String deltaPath = System.getProperty("delta.lake.path", "file:///tmp/delta");
            String fullPath = deltaPath + "/" + tableName;

            DataFrame df = sparkSession.read().format("delta").load(fullPath);
            
            result.put("table", tableName);
            result.put("schema", df.schema().json());
            result.put("row_count", df.count());

            List<Map<String, Object>> rows = new ArrayList<>();
            Row[] sampleRows = df.take(10);
            
            for (Row row : sampleRows) {
                Map<String, Object> rowMap = new HashMap<>();
                String[] columns = df.columns();
                for (int i = 0; i < columns.length; i++) {
                    rowMap.put(columns[i], row.get(i));
                }
                rows.add(rowMap);
            }

            result.put("sample_data", rows);
            result.put("status", "success");

            return result;
        } catch (Exception e) {
            log.error("Error reading Delta table: {}", tableName, e);
            result.put("error", e.getMessage());
            result.put("status", "failed");
            result.put("table", tableName);
            result.put("hint", "Ensure Delta Lake table exists at: file:///tmp/delta/" + tableName);
            return result;
        }
    }

    public Map<String, Object> executeSql(String sql) {
        log.info("Executing SQL: {}", sql);
        Map<String, Object> result = new HashMap<>();

        try {
            DataFrame df = sparkSession.sql(sql);
            
            List<Map<String, Object>> rows = new ArrayList<>();
            Row[] sampleRows = df.take(20);
            
            for (Row row : sampleRows) {
                Map<String, Object> rowMap = new HashMap<>();
                String[] columns = df.columns();
                for (int i = 0; i < columns.length; i++) {
                    rowMap.put(columns[i], row.get(i));
                }
                rows.add(rowMap);
            }

            result.put("data", rows);
            result.put("status", "success");
            return result;
        } catch (Exception e) {
            log.error("Error executing SQL", e);
            result.put("error", e.getMessage());
            result.put("status", "failed");
            return result;
        }
    }
}
