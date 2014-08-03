package org.quickstep.demo;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import org.quickstep.PanelCommand;

import static javax.swing.BorderFactory.*;
import static org.quickstep.GridBagToolKit.*;

public class CustomPanelCommandDemo extends JFrame
{
   public CustomPanelCommandDemo() throws HeadlessException
   {
      buildContent(this, new MyPanel().
         withOrientation(Orientation.VERTICAL).
         add(new MyPanel().
            add(line().add("User").add(new JTextField(), spec().withPreferredWidth(100))).
            add(line().add("Password").add(new JTextField(), spec().withPreferredWidth(100))).
            withBorder("Custom Panel")
         ).
         add(new MyPanel().
            add(new JButton("Proceed"), spec().withGridWidthRemaining())
         )
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
      new CustomPanelCommandDemo();
   }
}

class MyPanel extends PanelCommand
{
   MyPanel()
   {
      super(new JPanel(), spec().withAnchor(AnchorX.BOTH, AnchorY.BOTH).withWeight(1.0).withGap(12));
   }

   @Override
   protected Border createDefaultBorder(String title)
   {
      return createCompoundBorder(createTitledBorder(createLineBorder(Color.ORANGE, 10), title), createEmptyBorder(9, 9, 9, 9));
   }
}
