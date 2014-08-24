package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class LineSeparatorCommand implements GridBagCommand
{
   private final CellSpec cellSpec = spec();

   public LineSeparatorCommand()
   {
   }

   public LineSeparatorCommand withSpec(CellSpec spec)
   {
      this.cellSpec.overrideWith(spec);
      return this;
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      boolean horizontal = builder.isHorizontal();
      JComponent separator = builder.getComponentFactory().createSeparator(horizontal ? Orientation.HORIZONTAL : Orientation.VERTICAL);
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
