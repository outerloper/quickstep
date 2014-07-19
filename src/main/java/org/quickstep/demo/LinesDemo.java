package org.quickstep.demo;

import java.util.logging.Level;
import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class LinesDemo extends JFrame
{
   public LinesDemo()
   {
      debug();
      logger.setLevel(Level.WARNING);
      buildContent(this, panel().
         withMaxLineLength(4).
         specifyCellDefaults(grow().withAnchorX(AnchorX.LEFT)).
         add(checkBox(0)).
         add(line().
            add(checkBox(1), spec().withGridHeight(2)).
            add(checkBox(1)).
            add(checkBox(1))
         ).
         add(line().
            add(checkBox(2)).
            add(checkBox(2)).
            add(checkBox(2)).
            add(checkBox(2)) // will produce warning
         ).
         add(line().
            add(checkBox(3), spec().withGridWidthRemaining()).
            add(checkBox(3)).
            add(checkBox(3)) // will produce warning
         ).
         add(line().
            add(checkBox(4), spec().withGridWidth(4))
         ).
         add(line().
            add(checkBox(5))
         )
      );
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setResizable(true);
      setVisible(true);
   }

   private JCheckBox checkBox(Integer number)
   {
      return new JCheckBox("Line " + number);
   }

   public static void main(String[] args) throws Exception
   {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      new LinesDemo();
   }
}
