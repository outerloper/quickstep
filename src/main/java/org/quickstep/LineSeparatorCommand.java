package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class LineSeparatorCommand implements GridBagCommand
{
   @Override
   public void apply(GridBagBuilder builder)
   {
      JSeparator separator = new JSeparator();
      CellSpec spec = spec().withAnchor(A.BOTH);

      if (Orientation.HORIZONTAL.equals(builder.getGridSpec().getOrientation()))
      {
         separator.setOrientation(JSeparator.HORIZONTAL);
         spec.withWeightY(0.0).withGridWidthRemaining();
      }
      else
      {
         separator.setOrientation(JSeparator.VERTICAL);
         spec.withWeightX(0.0).withGridHeightRemaining();
      }

      line().add(separator, spec).apply(builder);
   }
}
