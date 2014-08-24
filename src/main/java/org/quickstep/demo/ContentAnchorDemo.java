package org.quickstep.demo;

import javax.swing.*;

import org.quickstep.GridBagCommand;

import static javax.swing.UIManager.getSystemLookAndFeelClassName;
import static javax.swing.UIManager.setLookAndFeel;
import static org.quickstep.GridBagToolKit.*;

public class ContentAnchorDemo extends JFrame
{
   public ContentAnchorDemo()
   {
      buildContent(this, panel().
         withContentAnchor(A.BOTH).
         add(panel().add(components()).withBorder().withContentAnchor(AX.RIGHT, AY.BOTTOM)).
         add(panel().add(components()).withBorder().withContentAnchor(AX.BOTH, AY.BOTTOM)).
         add(panel().add(components()).withBorder().withContentAnchor(AX.LEFT, AY.BOTTOM)).
         addLineBreak().
         add(panel().add(components()).withBorder().withContentAnchor(AX.RIGHT, AY.BOTH)).
         add(panel().add(components()).withBorder().withContentAnchor(AX.BOTH, AY.BOTH)).
         add(panel().add(components()).withBorder().withContentAnchor(AX.LEFT, AY.BOTH)).
         addLineBreak().
         add(panel().add(components()).withBorder().withContentAnchor(AX.RIGHT, AY.TOP)).
         add(panel().add(components()).withBorder().withContentAnchor(AX.BOTH, AY.TOP)).
         add(panel().add(components()).withBorder().withContentAnchor(AX.LEFT, AY.TOP))
      );

      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setResizable(true);
      setVisible(true);
   }

   public static GridBagCommand components()
   {
      return seq().
         add("Label").
         add(new JTextField(), spec().withPreferredWidth(50)).
         addLineBreak().
         add(new JButton("Button"), spec().withGridWidthRemainder().withAnchorX(AX.BOTH));
   }

   public static void main(String[] args) throws Exception
   {
//      debug();
      setLookAndFeel(getSystemLookAndFeelClassName());
      new ContentAnchorDemo();
   }
}
