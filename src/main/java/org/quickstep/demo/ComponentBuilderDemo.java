package org.quickstep.demo;

import javax.swing.*;

import org.quickstep.ComponentBuilder;
import org.quickstep.GridBagSpec;

import static org.quickstep.GridBagToolKit.*;

public class ComponentBuilderDemo extends JFrame
{
   public ComponentBuilderDemo()
   {
      buildContent(this, panel().
         withMaxLineLength(1).
         withSpec(spec().withInset(0, 0, 5, 0)).
         specifyColumn(0, growX().withInset(5, 5, 0, 5)).
         add(new JButton("Button")).
         add(SeparatorBuilder.horizontalSeparator()).
         add(new JButton("Button")).
         add(panel().withSpec(growY()).specifyCellDefaults(growX()).
            add(new JButton("Button")).add(SeparatorBuilder.verticalSeparator()).add(new JButton("Button"))
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
      new ComponentBuilderDemo();
   }
}

class SeparatorBuilder implements ComponentBuilder
{
   private JSeparator separator;
   private GridBagSpec spec;

   private SeparatorBuilder(JSeparator separator, GridBagSpec spec)
   {
      this.separator = separator;
      this.spec = spec;
   }

   public static SeparatorBuilder horizontalSeparator()
   {
      return new SeparatorBuilder(new JSeparator(JSeparator.HORIZONTAL), growX().withWeightY(0.0).withInsetX(0));
   }

   public static SeparatorBuilder verticalSeparator()
   {
      return new SeparatorBuilder(new JSeparator(JSeparator.VERTICAL), growY().withWeightX(0.0).withInsetY(0));
   }

   @Override
   public GridBagSpec getSpec()
   {
      return spec;
   }

   @Override
   public JComponent build()
   {
      return separator;
   }
}
