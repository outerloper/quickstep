package org.quickstep.demo;

import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SpannedCellsDemo extends JFrame
{
   List<JButton> buttons = new LinkedList<JButton>();

   JButton okButton = new JButton("OK");
   JButton cancelButton = new JButton("Cancel");
   JButton helpButton = new JButton("Help");

   public SpannedCellsDemo()
   {
      initComponents();
      arrangeComponents();

      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setVisible(true);
   }

   private void initComponents()
   {
      for (int i = 0; i < 30; i++)
      {
         buttons.add(new JButton("JButton " + i));
      }
   }

   private void arrangeComponents()
   {
      buildContent(this, panel().
         withMaxLineLength(1).
         add(panel().
            withSpec(grow()).
            withMaxLineLength(4).
            withDefaultSpec(grow()).
            withCellSpec(1, 2, spec().withGridSize(2, 3)).
            withCellSpec(0, 5, spec().withGridSize(2, 2)).
            withCellSpec(2, 7, spec().withGridSize(1, 2)).
            withCellSpec(3, 3, spec().withGridHeightRemaining()).
            addAll(buttons)
         ).
         add(panel().
            withSpec(spec().withGridWidthRemaining()).
            withDefaultSpec(spec().withPreferredWidth(66)).
            add(okButton).
            add(cancelButton).
            add(helpButton)
         )
      );
   }

   public static void main(String[] args)
   {
//      GridBagBuilder.debug();
      try
      {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      new SpannedCellsDemo();
   }
}
