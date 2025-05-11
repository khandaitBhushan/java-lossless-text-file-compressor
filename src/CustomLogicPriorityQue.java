import java.util.Comparator;
public class CustomLogicPriorityQue implements Comparator<TreeNode> {
    @Override
    public int compare(TreeNode t1, TreeNode t2) {
        if (t1.fre == t2.fre) return -1;
        return t1.fre - t2.fre;
    }
}
