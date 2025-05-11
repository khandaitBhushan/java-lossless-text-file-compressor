import java.util.Scanner;
public class Start {
    public void callCompressionLogic() {
        try (Scanner sc = new Scanner(System.in);) {
            System.out.println("Enter a directory of your file :");
            String oldLocation = sc.next();
            // create Object of CompressioLogic class
            CompressionLogic logic = new CompressionLogic(oldLocation);
            logic.readFileCharacters();
            System.out.println("Hurry ! Compression process done Successfully...!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deCompressionLogic() {
        try (Scanner sc = new Scanner(System.in);) {
            DecompressFile dObject = new DecompressFile();
            System.out.println("Enter directory of Compressed file : ");
            String compressFilePath = sc.next();
            dObject.generateRootOfTree(compressFilePath);
            System.out.println("End");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Start start = new Start();
        boolean flag = true;
        System.out.println("Enter Your choice \n\t1. Compress\n\t2. Decompress : ");
        String choice = sc.next();
        while (flag) {
            if (choice.equalsIgnoreCase("Compress")) {
                start.callCompressionLogic();
            } else if (choice.equalsIgnoreCase("Decompress")) {
                start.deCompressionLogic();
            } else {
                System.out.println("Enter either (Compress / Decompress) or (1/2) : ");
                sc.nextLine();
                choice = sc.next();
                continue;
            }
            flag = false;
        }
        sc.close();
    }
}
