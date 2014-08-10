package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SeparatorCommand extends CellCommand<SeparatorCommand>
{
   @Override
   public JComponent getComponent() // TODO improve design
   {
      return new JSeparator(JSeparator.VERTICAL);
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      JSeparator separator = new JSeparator();
      CellSpec spec = spec();

      if (Orientation.HORIZONTAL.equals(builder.getGridSpec().getOrientation()))
      {
         separator.setOrientation(JSeparator.VERTICAL);
         spec.withAnchor(AX.CENTER, AY.BOTH);
      }
      else
      {
         separator.setOrientation(JSeparator.HORIZONTAL);
         spec.withAnchor(AX.BOTH, AY.CENTER);
      }

      component(separator).withSpec(spec).apply(builder);
   }
}
