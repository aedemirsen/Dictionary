import IO.FileOperation;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Dictionary {

    //Bu map ler key olarak kelimeyi value olarak ise diğer dildeki karşılığını tutacak.
    //Eğer aynı kelimeden birden fazla varsa bunların value ları , ile ayrılarak tutulacak
    //ÖRNEK: {"şimdi":"present,now,currently"}
    static private Map<String,String> tr2en;
    static private Map<String,String> en2tr;

    private final static String turkishTxt = "tureng.txt";
    private final static String englishTxt = "engtur.txt";

    private static void mainMenu(){
        System.out.println("Kullanmak istediğiniz sözlük için aşağıdaki seçeneklerden devam ediniz: ");
        System.out.println("1 - Türkçe-İngilizce");
        System.out.println("2 - İngilizce-Türkçe");
        System.out.println("3 - Yeni Kelime Ekle");
        System.out.println("0 - Çıkış");
    }

    private static void toMap(List<String> list,String lang){
        if (lang.equals("tr")){
            tr2en = list.stream()
                    .map(str -> str.split("/", 2))
                    .collect(Collectors.groupingBy(
                            arr -> arr[0],
                            Collectors.mapping(arr -> arr[1], Collectors.joining(","))
                    ));
        }
        else{
            en2tr = list.stream()
                    .map(str -> str.split("/", 2))
                    .collect(Collectors.groupingBy(
                            arr -> arr[0],
                            Collectors.mapping(arr -> arr[1], Collectors.joining(","))
                    ));
        }

    }

    private static String keyContains(Map<String,String> map,String key){
        Optional<Map.Entry<String, String>> val = map.entrySet().stream()
                .filter(entry -> entry.getKey().contains(key) && entry.getKey().contains(","))
                .findFirst();
        if (val.isPresent()) {
            Map.Entry<String, String> entry = val.get();
            return entry.getValue();
        }
        else return "";
    }

    public static void main(String[] args) {
        String rootPath = System.getProperty("user.dir");
        //first read files and store words
        try {
            //turkish - english
            toMap(FileOperation.fo.readFile(rootPath + "/asset/" + turkishTxt),"tr");
            //english - turkish
            toMap(FileOperation.fo.readFile(rootPath + "/asset/" + englishTxt),"en");
        } catch (IOException e) {
            System.out.println("Kelimeleri okuma esnasında bir hata meydana geldi!!");
        }
        Scanner scanner = new Scanner(System.in);
        while(true){
            mainMenu();
            int dictionaryType = scanner.nextInt();
            scanner.nextLine();
            if (dictionaryType == 1){
                System.out.println("##### Türkçe-İngilizce Sözlük #####");
                while(true) {
                    System.out.print("Kelime : ");
                    String word = scanner.nextLine();
                    if(word.equals("0")) break;
                    else if (word.contains(",")){
                        //if key is seperated with comma, we should check every key separately
                        //key1, key2 / value
                        String val = keyContains(tr2en,word);
                        System.out.println("İngilizce Karşılığı : " + val);
                    }
                    else if(tr2en.containsKey(word)){
                        System.out.println("İngilizce Karşılığı : " + tr2en.get(word));
                    }
                    else{
                        System.out.println("Sözlükte bu kelime mevcut değil.");
                    }
                }
            }
            else if (dictionaryType == 2){
                System.out.println("##### İngilizce-Türkçe Sözlük #####");
                while(true) {
                    System.out.print("Kelime : ");
                    String word = scanner.next();
                    if(word.equals("0")) break;
                    if(en2tr.containsKey(word)){
                        System.out.println("Türkçe Karşılığı : " + en2tr.get(word));
                    }
                    else{
                        System.out.println("Sözlükte bu kelime mevcut değil.");
                    }
                }
            }
            else if(dictionaryType == 3){
                while(true) {
                    System.out.println("##### Yeni Kelime Ekle #####");
                    System.out.println("Eklemek istediğiniz kelime türkçe ise 1 ingilizce ise 2 ye basınız...");
                    int i = scanner.nextInt();
                    scanner.nextLine();
                    if (i == 1) {
                        System.out.print("Eklemek istediğiniz türkçe kelime: ");
                        String tWord = scanner.nextLine();
                        System.out.print("Eklemek istediğiniz kelimenin ingilizce karşılığı: ");
                        String eWord = scanner.nextLine();
                        if(tr2en.containsKey(tWord)){
                            System.out.println("Bu kelime sözlükte ekli, "+eWord+" yeni bir ingilizce karşılık olarak eklenecek.");
                        }
                        try {
                            FileOperation.fo.writeFile(rootPath + "/asset/" + turkishTxt,tWord+"/"+eWord);
                            System.out.println(tWord + " Sözlüğe Eklendi!");
                        } catch (IOException e) {
                            System.out.println("Dosyaya yazma işleminde bir hata oluştu!");
                        }
                    }
                    else if (i == 2) {
                        System.out.print("Eklemek istediğiniz ingilizce kelime: ");
                        String eWord = scanner.nextLine();
                        System.out.print("Eklemek istediğiniz kelimenin türkçe karşılığı: ");
                        String tWord = scanner.nextLine();
                        if(en2tr.containsKey(eWord)){
                            System.out.println("Bu kelime sözlükte ekli, "+tWord+" yeni bir türkçe karşılık olarak eklenecek.");
                        }
                        try {
                            FileOperation.fo.writeFile(rootPath + "/asset/" + englishTxt,eWord+"/"+tWord);
                            System.out.println(eWord + " Sözlüğe Eklendi!");
                        } catch (IOException e) {
                            System.out.println("Dosyaya yazma işleminde bir hata oluştu!");
                        }
                    }
                    else if(i == 0) break;
                }
            }
            else if (dictionaryType == 0){
                break;
            }
            else{
                System.out.println("Lütfen geçerli bir seçenek seçiniz...");
            }
        }

    }
}
