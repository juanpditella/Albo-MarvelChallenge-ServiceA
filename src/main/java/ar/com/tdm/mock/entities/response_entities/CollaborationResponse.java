package ar.com.tdm.mock.entities.response_entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaborationResponse {
    private String lastSync;
    private List<String> editors;
    private List<String> writers;
    private List<String> colorists;

    public boolean addEditor(String name) {
        if (!editors.contains(name)) {
            return editors.add(name);
        }
        return false;
    }

    public boolean addWriter(String name) {
        if (!writers.contains(name)) {
            return writers.add(name);
        }
        return false;
    }

    public boolean addColorist(String name) {
        if (!colorists.contains(name)) {
            return colorists.add(name);
        }
        return false;
    }
}
