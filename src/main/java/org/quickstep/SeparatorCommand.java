package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SeparatorCommand implements GridBagCommand
{
   private final boolean vertical;
   private final JSeparator customSeparator;

   public SeparatorCommand(boolean vertical)
   {
      this(null, vertical);
   }

   protected SeparatorCommand(JSeparator separator, boolean vertical)
   {
      this.vertical = vertical;
      this.customSeparator = separator;
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      JSeparator separator = customSeparator == null ? builder.createDefaultSeparator() : customSeparator;
      GridBagSpec spec = spec();
      GridBagCommand command;

      if (vertical)
      {
         separator.setOrientation(JSeparator.VERTICAL);
         spec.withWeightX(0.0).withFillY().withAnchorX(AnchorX.CENTER);
      }
      else
      {
         separator.setOrientation(JSeparator.HORIZONTAL);
         spec.withWeightY(0.0).withFillX().withAnchorY(AnchorY.MIDDLE);
      }

      if (builder.isHorizontal() && !vertical)
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