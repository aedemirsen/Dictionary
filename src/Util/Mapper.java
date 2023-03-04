package Util;

import java.util.*;
import java.util.stream.Collectors;

public class Mapper {

    // Bu sınıfta dosyadan okunan satırların bir map e aktarılması gerçekleştirilir.
    // 2 farklı yöntemle bu iş gerçekleştirilebilir : Stream API ya da standart döngü kullanarak.

    public static Mapper m = new Mapper();

    private final String SEPERATION_CHARACTER = "/";
    private final String KEY_SEPERATION_CHARACTER = ",";

    public Map<String, List<String>> toMap(List<String> lines) {
        return lines.stream()
                .map(line -> line.split(SEPERATION_CHARACTER))
                .flatMap(arr -> {
                    String[] keys = arr[0].split(KEY_SEPERATION_CHARACTER);
                    String value = arr[1].trim();
                    return Arrays.stream(keys)
                            .map(String::trim)
                            .map(key -> new AbstractMap.SimpleEntry<>(key, value));
                })
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

    /*public Map<String,List<String>> toMap(List<String> lines){
        Map<String,List<String>> map = new HashMap<>();
        for (String line : lines){
            String[] splittedLine = line.split(SEPERATION_CHARACTER);
            String key = splittedLine[0];
            String value = splittedLine[1];
            if(key.contains(KEY_SEPERATION_CHARACTER)){
                //virgüle göre ayırdıktan sonra kelimenin başı ve sonunda boşluk varsa temizleyelim.
                String[] splittedKey = Arrays.stream(key.split(KEY_SEPERATION_CHARACTER)).map(String::trim)
                        .toArray(String[]::new);
                for (String s : splittedKey){
                    if(map.containsKey(s)){
                        map.get(s).add(value);
                    }
                    else{
                        var l = new ArrayList<String>();
                        l.add(value);
                        map.put(s, l);
                    }
                }
            }
            else{
                if(map.containsKey(key)){
                    map.get(key).add(value);
                }
                else {
                    var l = new ArrayList<String>();
                    l.add(value);
                    map.put(key, l);
                }
            }
        }
        return map;
    }*/
}
