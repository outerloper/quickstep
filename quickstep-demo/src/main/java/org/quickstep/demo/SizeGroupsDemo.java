package org.quickstep.demo;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SizeGroupsDemo
{
   public static void main(String[] args)
   {
      DemoUtils.setSystemLookAndFeel();
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

      buildContent(
         frame, panel()
            .withContentAnchor(A.BOTH)
            .add(new JButton("0"), spec().withSizeGroup(0))
            .add(new JButton("00"), spec().withSizeGroup(0))
            .add(new JButton("000"), spec().withSizeGroup(0))
            .addSeparator()
            .add(new JButton("111"), spec().withSizeGroup(1))
            .add(new JButton("11111"), spec().withSizeGroup(1))
            .add(new JButton("1111111"), spec().withSizeGroup(1))
            .addSeparator()
            .add(new JButton("22222"), spec().withSizeGroup(2))
            .add(new JButton("222222222"), spec().withSizeGroup(2))
            .add(new JButton("2222222222222"), spec().withSizeGroup(2))
      );

      frame.setMinimumSize(frame.getPreferredSize());
      frame.setLocationRelativeTo(null);
      frame.setAlwaysOnTop(true);
      frame.setVisible(true);
   }
}
