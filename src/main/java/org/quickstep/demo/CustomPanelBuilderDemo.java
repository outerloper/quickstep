package org.quickstep.demo;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import org.quickstep.GridBagBuilder;

import static javax.swing.BorderFactory.createLineBorder;
import static javax.swing.BorderFactory.createTitledBorder;
import static org.quickstep.GridBagToolKit.buildContent;
import static org.quickstep.GridBagToolKit.spec;

public class CustomPanelBuilderDemo extends JFrame
{
   public CustomPanelBuilderDemo() throws HeadlessException
   {
      buildContent(this, MyBuilder.myPanel().
         add(new JLabel("User")).
         add(new JTextField(), spec().withPreferredWidth(100)).
         nextLine().
         add(new JLabel("Password")).
         add(new JTextField(), spec().withPreferredWidth(100)).
         nextLine().
         add(new JButton("Proceed"), spec().withGridWidth(2)).
         withBorder("Custom Panel")
      );
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setResizable(true);
      setVisible(true);
   }

   public static void main(String[] args) throws Exception
   {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      new CustomPanelBuilderDemo();
   }
}

class MyBuilder extends GridBagBuilder
{
   static MyBuilder myPanel()
   {
      return new MyBuilder(new JPanel());
   }

   private MyBuilder(JPanel panel)
   {
      super(panel, spec().withFill().withWeight(1.0).withGap(20));
   }

   @Override
   protected Border createDefaultBorder(String title)
   {
      return createTitledBorder(createLineBorder(Color.ORANGE, 10), title);
   }
}
