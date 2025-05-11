import java.io.Serializable;

public class TreeNode implements Serializable {
    char character;
    int fre;
    TreeNode left;
    TreeNode right;

    TreeNode(char character, int fre) {
        this.character = character;
        this.fre = fre;
    }

    TreeNode(char character, int fre, TreeNode left, TreeNode right) {
        this.character = character;
        this.fre = fre;
        this.left = left;
        this.right = right;
    }

    public String toString() {
        return character + " : " + fre + "\n";
    }
}