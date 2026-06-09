package com.example.delta;

import com.example.delta.service.DeltaService;
import com.example.delta.service.DeltaSharingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/delta")
@RequiredArgsConstructor
public class DeltaController {

    private final DeltaService deltaService;
    private final DeltaSharingService deltaSharingService;

    @GetMapping("/tables")
    public ResponseEntity<List<Map<String, Object>>> listDeltaTables() {
        List<Map<String, Object>> tables = deltaService.listDeltaTables();
        return ResponseEntity.ok(tables);
    }

    @GetMapping("/table/{tableName}")
    public ResponseEntity<Map<String, Object>> readDeltaTable(@PathVariable String tableName) {
        Map<String, Object> result = deltaService.readDeltaTable(tableName);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/sharing/tables")
    public ResponseEntity<List<Map<String, String>>> listSharingTables() {
        List<Map<String, String>> tables = deltaSharingService.listSharedTables();
        return ResponseEntity.ok(tables);
    }

    @GetMapping("/sharing/profile")
    public ResponseEntity<Map<String, String>> getSharingProfile() {
        Map<String, String> profile = deltaSharingService.getProfileInfo();
        return ResponseEntity.ok(profile);
    }
}
