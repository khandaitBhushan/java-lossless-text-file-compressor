import java.io.*;
import java.util.HashMap;
import java.util.PriorityQueue;
public class CompressionLogic {
    String compressLocation;
    String oldLocation;
    StringBuilder actualContent;
    StringBuilder binaryCode;
    HashMap<Character, Integer> frequency;
    HashMap<Character, String> chart;
    PriorityQueue<TreeNode> que;

    // set default path
    public CompressionLogic() {
        compressLocation = "compress.huff";
        que = new PriorityQueue<>(new CustomLogicPriorityQue());
        actualContent = new StringBuilder();
        binaryCode = new StringBuilder();
        frequency = new HashMap<>();
        chart = new HashMap<>();

    }

    // custom path
    public CompressionLogic(String compressLocation) {
        this();     // call default constructor
        this.oldLocation = compressLocation;
    }

    public void readFileCharacters() {
        try (FileReader reader = new FileReader(oldLocation);) {
            int data = reader.read();
            while (data != -1) {
                char ch = (char) data;
                actualContent.append(ch);
                Integer f = frequency.get(ch);
                if (f == null)
                    frequency.put(ch, 1);
                else frequency.put(ch, frequency.get(ch) + 1);

                data = reader.read();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        createTree();
    }

    public void createTree() {
        for (char ch : frequency.keySet()) {
            que.add(new TreeNode(ch, frequency.get(ch)));
        }
        while (que.size() > 1) {
            TreeNode left = que.poll();
            TreeNode right = que.poll();
            que.add(new TreeNode('\0', left.fre + right.fre, left, right));
        }
        generateNewCode();
    }

    public void generateNewCode() {
        // new binary code will get generated
        helper("", que.peek());
        // make compress file
        generateCompressFile();
    }

    private void helper(String code, TreeNode root) {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            chart.put(root.character, code);
            return;
        }
        helper(code + "0", root.left);
        helper(code + '1', root.right);
    }

    public void generateCompressFile() {
        for (char ch : actualContent.toString().toCharArray()) {
            binaryCode.append(chart.get(ch));
        }
        // Convert bits to byte
        int len = binaryCode.length();

        try (FileOutputStream writer = new FileOutputStream(compressLocation);
             ObjectOutputStream obj = new ObjectOutputStream(writer);) {
            obj.writeObject(que.peek());
            obj.writeInt(binaryCode.length());
            // Only pad if necessary
            int padding = (8 - (len % 8)) % 8;
            for (int i = 0; i < padding; i++) {
                binaryCode.append('0');
            }
            int newLen = binaryCode.length(); // update after padding
            byte binary = 0;
            for (int i = 0; i < newLen; i += 8) {
                int end = Math.min(i + 8, newLen);  // see note on line no. 109 -116
                binary = (byte) Integer.parseInt(binaryCode.substring(i, end), 2);
                obj.writeByte(binary);
            }
            obj.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            getCompressionRation();
        }
    }
    // byte have range 0 - 156 for sign value
    // for unsigned it has 0-255
    // when it comes to 255 it will be store as -1 in file
    // but read as -1& 0xFF with string.format so
    // it will automatically come ot its original form
    // as -1 will have byte representation as 11111111
    // same as actual data
    public void getCompressionRation() {
        File old = new File(oldLocation);
        File comLocation = new File(compressLocation);
        double oldSize = old.length();
        double comSize = comLocation.length();
        if (oldSize == 0 || comSize == 0) {
            System.out.println("Original file size is zero. Cannot compute compression.");
        } else {
            // safe to divide
            System.out.println("Important data : ");
            System.out.println("Actual file size : " + oldSize + "kb");
            System.out.println("Compressed file size : " + comSize + "kb");
            System.out.println("Successful Compression upto : " + (100-(comSize / oldSize * 100)) + "%");
        }
    }
}
