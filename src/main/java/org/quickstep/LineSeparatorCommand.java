package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class LineSeparatorCommand implements GridBagCommand
{
   @Override
   public void apply(GridBagBuilder builder)
   {
      JSeparator separator = new JSeparator();
      CellSpec spec = spec();

      if (Orientation.HORIZONTAL.equals(builder.getGridSpec().getOrientation()))
      {
         separator.setOrientation(JSeparator.HORIZONTAL);
         spec.withAnchor(AX.BOTH, AY.CENTER).withGridWidthRemaining();
      }
      else
      {
         separator.setOrientation(JSeparator.VERTICAL);
         spec.withAnchor(AX.CENTER, AY.BOTH).withGridHeightRemaining();
      }

      line().add(separator, spec).apply(builder);
   }
}
