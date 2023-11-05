import javax.swing.*;

public class StringFilterRunner
{
    public static void main(String[] args)
    {
        StringFilterGUI stringFilter = new StringFilterGUI();

        stringFilter.setTitle("Tag Extractor");
        stringFilter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stringFilter.setSize(1080, 660);
        stringFilter.setLocation(85, 10);
        stringFilter.setVisible(true);
    }
}