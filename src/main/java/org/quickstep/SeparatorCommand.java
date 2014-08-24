package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SeparatorCommand extends AbstractComponentCommand<SeparatorCommand>
{
   @Override
   public JComponent getComponent(Orientation parentOrientation, ComponentFactory parentFactory)
   {
      return parentFactory.createSeparator(parentOrientation.getOther());
   }

   @Override
   public CellSpec getDefaultSpec(Orientation parentOrientation)
   {
      if (parentOrientation.isHorizontal())
      {
         return spec().withAnchor(AX.CENTER, AY.BOTH);
      }
      else
      {
         return spec().withAnchor(AX.BOTH, AY.CENTER);
      }
   }
}
