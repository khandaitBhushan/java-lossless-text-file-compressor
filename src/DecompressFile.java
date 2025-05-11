import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
public class DecompressFile {
    int actualEncodeLength = 0;
    TreeNode root;
    StringBuilder binary;
    StringBuilder actual;

    public void generateRootOfTree(String location) {
        try (ObjectInputStream obj = new ObjectInputStream(new FileInputStream(location));) {
            root = (TreeNode) obj.readObject();
            actualEncodeLength = (int) obj.readInt();
            byte[] buffer = new byte[obj.available()];
            buffer = obj.readAllBytes();
            binary = new StringBuilder();
            for (byte b : buffer) {
                int val = b & 0xFF;
                binary.append(String.format("%8s", Integer.toBinaryString(val)).replace(' ', '0'));
            }
            generateActualFile();
        } catch (Exception forObject) {
            System.out.println(forObject.getMessage());
        }
    }

    private void generateActualFile() {
        actual = new StringBuilder();
        TreeNode temp = root;
        for (int i = 0; i < actualEncodeLength; i++) {
            if (binary.charAt(i) == '0') {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
            if (temp.left == null && temp.right == null) {
                actual.append(temp.character);
                temp = root;
            }
        }
        generateDecompressFile();
    }

    private void generateDecompressFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Decompress.txt"));) {
            writer.write(actual.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        finally {
            System.out.println("You file has been restored successfully...!");
        }
    }
}
