package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SeparatorCommand implements GridBagCommand
{
   private final boolean lineSeparator;
   private final JSeparator customSeparator;

   public SeparatorCommand(boolean lineSeparator)
   {
      this(null, lineSeparator);
   }

   protected SeparatorCommand(JSeparator separator, boolean lineSeparator)
   {
      this.lineSeparator = lineSeparator;
      this.customSeparator = separator;
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      JSeparator separator = customSeparator == null ? builder.createDefaultSeparator() : customSeparator;
      GridBagSpec spec = spec();
      GridBagCommand command;

      if (builder.isHorizontal() == lineSeparator)
      {
         separator.setOrientation(JSeparator.HORIZONTAL);
         spec.withWeightY(0.0).withFillX().withAnchorY(AnchorY.MIDDLE);
      }
      else
      {
         separator.setOrientation(JSeparator.VERTICAL);
         spec.withWeightX(0.0).withFillY().withAnchorX(AnchorX.CENTER);
      }

      if (lineSeparator)
      {
         if (builder.isHorizontal())
         {
            spec.withGridWidthRemaining();
         }
         else
         {
            spec.withGridHeightRemaining();
         }
         command = line().add(separator, spec);
      }
      else
      {
         command = new ComponentCommand(separator, spec);
      }

      command.apply(builder);
   }
}
