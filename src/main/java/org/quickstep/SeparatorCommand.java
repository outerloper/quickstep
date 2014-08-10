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
      CellSpec spec = spec().withAnchor(A.BOTH);

      if (Orientation.HORIZONTAL.equals(builder.getGridSpec().getOrientation()))
      {
         separator.setOrientation(JSeparator.VERTICAL);
         spec.withWeightX(0.0);
      }
      else
      {
         separator.setOrientation(JSeparator.HORIZONTAL);
         spec.withWeightY(0.0);
      }

      component(separator).withSpec(spec).apply(builder); // TODO test adding separators
   }
}
