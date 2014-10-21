package org.quickstep.demo;

import javax.swing.*;

import org.quickstep.command.GridBagCommand;

import static org.quickstep.GridBagToolKit.*;

public class ContentAnchorDemo
{
   public static void main(String[] args) throws Exception
   {
      DemoUtils.setSystemLookAndFeel();
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      buildContent(
         frame, panel()
            .withContentAnchor(A.BOTH)
            .add(panel().add(components()).withBorder().withContentAnchor(AX.RIGHT, AY.BOTTOM))
            .add(panel().add(components()).withBorder().withContentAnchor(AX.BOTH, AY.BOTTOM))
            .add(panel().add(components()).withBorder().withContentAnchor(AX.LEFT, AY.BOTTOM))
            .addLineBreak()
            .add(panel().add(components()).withBorder().withContentAnchor(AX.RIGHT, AY.BOTH))
            .add(panel().add(components()).withBorder().withContentAnchor(AX.BOTH, AY.BOTH))
            .add(panel().add(components()).withBorder().withContentAnchor(AX.LEFT, AY.BOTH))
            .addLineBreak()
            .add(panel().add(components()).withBorder().withContentAnchor(AX.RIGHT, AY.TOP))
            .add(panel().add(components()).withBorder().withContentAnchor(AX.BOTH, AY.TOP))
            .add(panel().add(components()).withBorder().withContentAnchor(AX.LEFT, AY.TOP))
      );

      frame.setMinimumSize(frame.getPreferredSize());
      frame.setLocationRelativeTo(null);
      frame.setAlwaysOnTop(true);
      frame.setVisible(true);
   }

   public static GridBagCommand components()
   {
      return seq()
         .add("Label")
         .add(new JTextField(), spec().withPreferredWidth(50))
         .addLineBreak()
         .add(new JButton("Button"), spec().withGridWidthRemainder().withAnchorX(AX.BOTH));
   }
}
