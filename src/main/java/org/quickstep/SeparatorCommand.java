package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SeparatorCommand extends AbstractComponentCommand<SeparatorCommand>
{
   @Override
   public JComponent getComponent(Direction parentDirection, ComponentFactory parentFactory)
   {
      return parentFactory.createSeparator(parentDirection.getOther());
   }

   @Override
   public CellSpec getDefaultSpec(Direction parentDirection)
   {
      if (parentDirection.isHorizontal())
      {
         return spec().withAnchor(AX.CENTER, AY.BOTH);
      }
      else
      {
         return spec().withAnchor(AX.BOTH, AY.CENTER);
      }
   }
}
