import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, Set<String>> groups = new HashMap<>();
        URL url = new URL("https://github.com/PeacockTeam/new-job/releases/download/v1.0/lng-4.txt.gz");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(url.openStream())));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            for (String part : parts) {
                if (!part.isEmpty()) {
                    groups.computeIfAbsent(part, k -> new HashSet<>()).add(line);
                }
            }
        }

        List<Set<String>> result = new ArrayList<>();
        for (Set<String> group : groups.values()) {
            if (group.size() > 1) {
                result.add(group);
            }
        }

        result.sort((set1, set2) -> Integer.compare(set2.size(), set1.size()));

        try (PrintWriter writer = new PrintWriter("output.txt")) {
            writer.println("Количество групп с более чем одним элементом: " + result.size());
            for (int i = 0; i < result.size(); i++) {
                writer.println("Группа " + (i + 1) + ", количество элементов: " + result.get(i).size());
                for (String str : result.get(i)) {
                    writer.println(str);
                }
            }
        }
    }
}

