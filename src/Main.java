import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Main {

    final static String repATrier = "EML_a_trier/";//"test/";

    public static void main(String[] args) throws FileNotFoundException {


        //Results arrays
        List<String> mailOk = new ArrayList<>();
        List<String> mailKo = new ArrayList<>();

        Vector v0 = new Vector<>();
        //Vocabulary definition
        List<String> vocabulaire = new ArrayList<>();
        vocabulaire.add("entreprise");
        v0.add(1);
        //vocabulaire.add("developpeur");
        //vocabulaire.add("back-end");
        //vocabulaire.add("front end");
        //vocabulaire.add("client");
        vocabulaire.add("expertise");
        v0.add(1);
        vocabulaire.add("poste");
        v0.add(1);
        vocabulaire.add("profil");
        v0.add(1);
        vocabulaire.add("offre");
        v0.add(1);
//        vocabulaire.add("collaborateur");
        vocabulaire.add("emploi");
        v0.add(1);
        vocabulaire.add("stage");
        v0.add(1);
      //  vocabulaire.add("recrute");
        //v0.add(1);
/*        vocabulaire.add("java");
        vocabulaire.add("python");
        vocabulaire.add("php");
        vocabulaire.add("cobol");
        vocabulaire.add("ruby");
        vocabulaire.add("sql");
        vocabulaire.add("offre");*/
        vocabulaire.add("stage");
        v0.add(1);
        vocabulaire.add("professionnel");
        v0.add(1);
        vocabulaire.add("leader");
        v0.add(1);
        //vocabulaire.add("ingenieur");
//        vocabulaire.add("mission");
//        vocabulaire.add("projet");

        //Vector initialization
        /*Vector v0 = new Vector<>();
         (String voc : vocabulaire){
            if (voc=="poste"){
                v0.add(3);
            }else{
                v0.add(1);
            }
        }*/

        //Gets all eml files in an array
        File folder = new File(repATrier);
        File[] emlFiles = folder.listFiles();

        //System.out.println("emlFiles size : " + emlFiles.length);

        //Loop on eml files
        for (File emlFile : emlFiles) {
        //Gets the file content
            String content = new Scanner(emlFile).useDelimiter("\\Z").next();
            //content is set to lowercase
            content = content.toLowerCase();
            //removes spcial char
            content = replaceSpecialChar(content);
            //punctuation characters are replaces by space
            content = deletePunctuation(content);

            //Vector creation
            Vector v1 = new Vector();
            //Loop on words
            for (String word : vocabulaire){
                int nb = countOccurence(content, word);
                v1.add(nb);
            }

            //We classify the email
            if (classifier(v0, v1) == "OK"){
                mailOk.add(emlFile.getName());
            }else{
                mailKo.add(emlFile.getName());
            }
            //System.out.println(v1);

        }

        System.out.println("MailOK size : " + mailOk.size());
        for (String mail1 : mailOk){
            System.out.println(mail1);
        }

        System.out.println("*********************************");
        System.out.println("*********************************");
        System.out.println("*********************************");
        System.out.println("*********************************");
        System.out.println("*********************************");
        System.out.println("*********************************");
        System.out.println("*********************************");
        System.out.println("*********************************");
        System.out.println("*********************************");
        System.out.println("*********************************");
        System.out.println("MailKO size : " + mailKo.size());
        for (String mail2 : mailKo){
            System.out.println(mail2);
        }
    }

    public static String replaceSpecialChar(String str){
        str = str.replace("é","e");
        str = str.replace("è","e");
        str = str.replace("ê","e");
        str = str.replace("à","a");
        str = str.replace("ù","u");
        str = str.replace("ç","c");
        str = str.replace("ô","o");
        str = str.replace("î","i");
        str = str.replace("ï","i");
        str = str.replace("û","u");

        return str;
    }

    public static String deletePunctuation(String str){
        str = str.replace(","," ");
        str = str.replace(";"," ");
        str = str.replace(":"," ");
        str = str.replace("!"," ");
        str = str.replace("?"," ");
        str = str.replace("."," ");
        str = str.replace("/"," ");
        str = str.replace("<"," ");
        str = str.replace(">"," ");
        str = str.replace("%"," ");
        str = str.replace("\\"," ");
        str = str.replace("*"," ");
        str = str.replace("-"," ");
        str = str.replace("+"," ");
        str = str.replace("="," ");
        str = str.replace("'"," ");
        str = str.replace("\""," ");
        str = str.replace(")"," ");
        str = str.replace("("," ");
        str = str.replace("&"," ");
        str = str.replace("{"," ");
        str = str.replace("["," ");
        str = str.replace("_"," ");
        str = str.replace("]"," ");
        str = str.replace("}"," ");
        str = str.replace("#"," ");
        str = str.replace("\r"," ");
        str = str.replace("\n"," ");
        str = str.replace("\t"," ");
        return str;
    }

    public static int countOccurence(String str, String findStr){
        int count = 0;
        String[] words = str.split(" ");

        for (String w:words){
            if (w.equals(findStr)){
                count++;
            }
        }
        return count;
    }

    public static String classifier(Vector v0, Vector v1){

        double v0Norm = 0.0;
        double v1Norm = 0.0;
        double scalarDot = 0.0;
        int n=v0.size();
        for (int i = 0; i < n; i++) {
            v0Norm += Math.pow((int) v0.get(i),2);
            v1Norm += Math.pow((int) v1.get(i),2);
            scalarDot += ((int) v0.get(i) * (int) v1.get(i));
        }
        v0Norm = Math.sqrt(v0Norm);
        v1Norm = Math.sqrt(v1Norm);
        double cos =0.0;
        if (v1Norm != 0){
            cos = scalarDot/(v0Norm*v1Norm);
        }

        //System.out.println("cos" + cos);
        if (cos<=0.8){
            return "KO";
        }else{
            return "OK";
        }
    }

}
