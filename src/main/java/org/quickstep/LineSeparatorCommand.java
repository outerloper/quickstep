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
      JSeparator separator = new JSeparator();
      CellSpec spec = spec();

      if (builder.isHorizontal())
      {
         separator.setOrientation(JSeparator.HORIZONTAL);
         spec.withAnchor(AX.BOTH, AY.CENTER).withGridWidthRemainder();
      }
      else
      {
         separator.setOrientation(JSeparator.VERTICAL);
         spec.withAnchor(AX.CENTER, AY.BOTH).withGridHeightRemainder();
      }

      line().add(separator, spec.overrideWith(cellSpec)).apply(builder);
   }
}
