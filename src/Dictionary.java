import IO.FileOperation;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Dictionary {

    //Bu map ler key olarak kelimeyi value olarak ise diğer dildeki karşılığını tutacak.
    //Eğer aynı kelimeden birden fazla varsa bunların value ları , ile ayrılarak tutulacak
    //ÖRNEK: {"şimdi":"present,now,currently"}
    private Map<String,String> tr2en;
    private Map<String,String> en2tr;

    private Scanner scanner = new Scanner(System.in);

    private final String turkishTxt = "tureng.txt";
    private final String englishTxt = "engtur.txt";

    private String rootPath = System.getProperty("user.dir");

    private void txtToMap(){
        //first read files and store words
        try {
            //turkish - english
            tr2en = toMap( FileOperation.fo.readFile(rootPath + "/asset/" + turkishTxt));
            //english - turkish
            en2tr = toMap(FileOperation.fo.readFile(rootPath + "/asset/" + englishTxt));
        } catch (IOException e) {
            System.out.println("Kelimeleri okuma esnasında bir hata meydana geldi!!");
        }
    }

    private void mainMenu(){
        System.out.println("Kullanmak istediğiniz sözlük için aşağıdaki seçeneklerden devam ediniz: ");
        System.out.println("1 - Türkçe-İngilizce");
        System.out.println("2 - İngilizce-Türkçe");
        System.out.println("3 - Yeni Kelime Ekle");
        System.out.println("0 - Çıkış");
    }

    private void case1(){
        System.out.println("##### Türkçe-İngilizce Sözlük #####");
        while(true) {
            System.out.print("Kelime : ");
            String word = scanner.nextLine();
            if(word.equals("0")) break;
            else if(tr2en.containsKey(word)){
                System.out.println("İngilizce Karşılığı : " + tr2en.get(word));
            }
            else{
                System.out.println("Sözlükte bu kelime mevcut değil.");
            }
        }
    }

    private void case2(){
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

    private void case3(){
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

    private Map<String,String> toMap(List<String> list){
        return list.stream()
                .map(str -> str.split("/", 2))
                .collect(Collectors.groupingBy(
                        arr -> arr[0],
                        Collectors.mapping(arr -> arr[1], Collectors.joining(","))
                ));
    }

    public void startDictionary() {
        txtToMap();
        while(true){
            mainMenu();
            int dictionaryType = scanner.nextInt();
            scanner.nextLine();
            if (dictionaryType == 1){
                case1();
            }
            else if (dictionaryType == 2){
                case2();
            }
            else if(dictionaryType == 3){
                case3();
            }
            else if (dictionaryType == 0){
                break;
            }
            else{
                System.out.println("Lütfen geçerli bir seçenek seçiniz...");
            }
        }
    }

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        dictionary.startDictionary();
    }
}
