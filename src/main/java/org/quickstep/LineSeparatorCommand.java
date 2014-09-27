package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class LineSeparatorCommand implements GridBagCommand
{
   private final CellSpec cellSpec = spec();

   public LineSeparatorCommand withSpec(CellSpec spec)
   {
      this.cellSpec.overrideWith(spec);
      return this;
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      boolean horizontal = builder.isHorizontal();
      JComponent separator = builder.getComponentFactory().createSeparator(horizontal ? Direction.LEFT_TO_RIGHT : Direction.TOP_TO_BOTTOM);
      CellSpec spec = spec();

      if (horizontal)
      {
         spec.withAnchor(AX.BOTH, AY.CENTER).withGridWidthRemainder();
      }
      else
      {
         spec.withAnchor(AX.CENTER, AY.BOTH).withGridHeightRemainder();
      }

      line().add(separator, spec.overrideWith(cellSpec)).apply(builder);
   }
}
